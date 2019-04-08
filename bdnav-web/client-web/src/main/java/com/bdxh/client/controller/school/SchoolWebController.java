package com.bdxh.client.controller.school;

import com.bdxh.client.configration.security.utils.SecurityUtils;
import com.bdxh.common.helper.excel.ExcelExportUtils;
import com.bdxh.common.helper.excel.bean.SchoolExcelReportBean;
import com.bdxh.common.helper.excel.utils.DateUtils;
import com.bdxh.common.helper.qcloud.files.FileOperationUtils;
import com.bdxh.common.utils.DateUtil;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.ModifySchoolDto;
import com.bdxh.school.dto.SchoolDto;
import com.bdxh.school.dto.SchoolExcelDto;
import com.bdxh.school.dto.SchoolQueryDto;
import com.bdxh.school.entity.School;
import com.bdxh.school.entity.SchoolUser;
import com.bdxh.school.enums.SchoolTypeEnum;
import com.bdxh.school.feign.SchoolControllerClient;
import com.bdxh.school.vo.SchoolInfoVo;
import com.bdxh.school.vo.SchoolShowVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clientSchoolWeb")
@Validated
@Slf4j
@Api(value = "学校管理员--学校信息", tags = "学校管理员--学校信息交互API")
public class SchoolWebController {

    @Autowired
    private SchoolControllerClient schoolControllerClient;

    @RolesAllowed({"ADMIN"})
    @RequestMapping(value = "/modifySchoolInfo", method = RequestMethod.POST)
    @ApiOperation(value = "修改学校信息（需学校admin权限）", response = Boolean.class)
    public Object modifySchoolInfo(@Validated @RequestBody ModifySchoolDto modifySchoolDto) {
        SchoolInfoVo schoolInfo = schoolControllerClient.findSchoolById(modifySchoolDto.getId()).getResult();
        //匹配图片是否已经修改，如果修改删除前一次的图片
        if (!schoolInfo.getSchoolLogoName().equals(modifySchoolDto.getSchoolLogoName())) {
            //删除腾讯云的以前图片
            FileOperationUtils.deleteFile(schoolInfo.getSchoolLogoName(), null);
        }
        Wrapper wrapper = schoolControllerClient.modifySchoolInfo(modifySchoolDto);
        return wrapper;
    }

    @RequestMapping(value = "/findSchoolById", method = RequestMethod.GET)
    @ApiOperation(value = "查询当前学校", response = SchoolInfoVo.class)
    public Object findSchoolById() {
        //获取当前用户
        SchoolUser user = SecurityUtils.getCurrentUser();
        Wrapper wrapper = schoolControllerClient.findSchoolById(user.getSchoolId());
        return WrapMapper.ok(wrapper.getResult());
    }
}
