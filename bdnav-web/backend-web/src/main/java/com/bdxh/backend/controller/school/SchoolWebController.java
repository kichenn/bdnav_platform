package com.bdxh.backend.controller.school;

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

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/schoolWebController")
@Validated
@Slf4j
@Api(value = "学校信息", tags = "学校信息交互API")
public class SchoolWebController {

    @Autowired
    private SchoolControllerClient schoolControllerClient;

    /**
     * @Description: 导出报表名称
     */
    private static final String title = "学校列表报表信息.xlsx";

    @RequestMapping(value = "/findSchoolsInConditionPaging", method = RequestMethod.POST)
    @ApiOperation(value = "学校信息列表数据[分页筛选]", response = SchoolShowVo.class)
    public Object findSchoolsInConditionPaging(@Validated @RequestBody SchoolQueryDto schoolQueryDto) {
        Wrapper wrapper = schoolControllerClient.findSchoolsInConditionPaging(schoolQueryDto);
        return WrapMapper.ok(wrapper.getResult());
    }

    @RequestMapping(value = "/addSchool", method = RequestMethod.POST)
    @ApiOperation(value = "增加学校信息", response = Boolean.class)
    public Object findSchoolsInConditionPaging(@Validated @RequestBody SchoolDto schoolDto) {
        Wrapper wrapper = schoolControllerClient.addSchool(schoolDto);
        return wrapper;
    }


    @RequestMapping(value = "/modifySchoolInfo", method = RequestMethod.POST)
    @ApiOperation(value = "修改学校信息", response = Boolean.class)
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


    @RequestMapping(value = "/delSchool", method = RequestMethod.GET)
    @ApiOperation(value = "删除学校信息", response = Boolean.class)
    public Object delSchool(@RequestParam("id") Long id) {
        SchoolInfoVo schoolInfo = schoolControllerClient.findSchoolById(id).getResult();
        //删除腾讯云的信息
        FileOperationUtils.deleteFile(schoolInfo.getSchoolLogoName(), null);
        //删除学校
        Wrapper wrapper = schoolControllerClient.delSchool(id);
        return wrapper;
    }

    @RequestMapping(value = "/batchDelSchool", method = RequestMethod.POST)
    @ApiOperation(value = "批量删除学校信息", response = Boolean.class)
    public Object batchDelSchool(@RequestBody List<Long> ids) {
        Wrapper wrapper = schoolControllerClient.batchDelSchool(ids);
        return wrapper;
    }

    @RequestMapping(value = "/findSchoolById", method = RequestMethod.GET)
    @ApiOperation(value = "查询学校详情", response = SchoolInfoVo.class)
    public Object findSchoolById(@RequestParam("schoolId") Long id) {
        Wrapper wrapper = schoolControllerClient.findSchoolById(id);
        return WrapMapper.ok(wrapper.getResult());
    }

    /**
     * @Description: 学校信息导出
     * @Author: Kang
     * @Date: 2019/2/27 18:31
     */
    @RequestMapping(value = "/downloadReportSchoolExcel", method = RequestMethod.GET)
    @ApiOperation(value = "学校信息导出")
    @ResponseBody
    public Object downloadReportSchoolExcel(@RequestParam("isBy") Byte isBy, @RequestParam(value = "ids", required = false) List<Long> ids,
                                            @RequestParam(value = "schoolCode", required = false) String schoolCode,
                                            @RequestParam(value = "schoolName", required = false) String schoolName,
                                            @RequestParam(value = "schoolType", required = false) Byte schoolType,
                                            @RequestParam(value = "schoolNature", required = false) String schoolNature,
                                            @RequestParam(value = "text", required = false) String text,
                                            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                            @RequestParam(value = "pageSize", required = false, defaultValue = "15") Integer pageSize) {
        SchoolExcelDto schoolExcelDto = new SchoolExcelDto();
        schoolExcelDto.setIsBy(isBy);
        schoolExcelDto.setIds(ids);
        schoolExcelDto.setSchoolCode(schoolCode);
        schoolExcelDto.setSchoolName(schoolName);
        schoolExcelDto.setSchoolType(schoolType);
        schoolExcelDto.setSchoolNature(schoolNature);
        schoolExcelDto.setText(text);
        schoolExcelDto.setPageNum(pageNum);
        schoolExcelDto.setPageSize(pageSize);
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        List<School> schools = new ArrayList<>();
        switch (schoolExcelDto.getIsBy()) {
            case 1:
                //分页学校信息导出
                List<SchoolShowVo> tempShowVo = schoolControllerClient.findSchoolsInConditionPaging(schoolExcelDto).getResult().getList();
                if (CollectionUtils.isNotEmpty(tempShowVo)) {
                    tempShowVo.stream().forEach(e -> {
                        School school = new School();
                        BeanUtils.copyProperties(e, school);
                        school.setCreateDate(DateUtil.format(e.getCreateDate(), "yyyy/MM/dd HH:mm:ss"));
                        schools.add(school);
                    });
                }
                break;
            case 2:
                //id选择信息导出
                schools.addAll(schoolControllerClient.findSchoolInIds(schoolExcelDto.getIds()).getResult());
                break;
            case 3:
                //查询所有学校信息
                List<SchoolShowVo> tempShowVo1 = schoolControllerClient.findSchools().getResult();
                if (CollectionUtils.isNotEmpty(tempShowVo1)) {
                    tempShowVo1.stream().forEach(e -> {
                        School school = new School();
                        BeanUtils.copyProperties(e, school);
                        school.setCreateDate(DateUtil.format(e.getCreateDate(), "yyyy/MM/dd HH:mm:ss"));
                        schools.add(school);
                    });
                }

                break;
            default:
                return WrapMapper.error("不存在的选项，请检查");
        }
        if (CollectionUtils.isEmpty(schools)) {
            return WrapMapper.error("抱歉，相关学校不存在。。");
        }

        List<SchoolExcelReportBean> schoolExcelReportBeans = schools.stream().map(e -> {
            SchoolExcelReportBean tempBean = new SchoolExcelReportBean();
            BeanUtils.copyProperties(e, tempBean);
            tempBean.setSchoolTypeValue(SchoolTypeEnum.getValue(e.getSchoolType()));
            tempBean.setCreateDate(DateUtils.date2Str(e.getCreateDate(), "yyyy/MM/dd HH:mm:ss"));
            //分割省市县
            if (StringUtils.isNotEmpty(e.getSchoolArea()) && e.getSchoolArea().contains("/")) {
                tempBean.setProvince(e.getSchoolArea().substring(0, e.getSchoolArea().indexOf("/")));
                tempBean.setCity(e.getSchoolArea().substring(e.getSchoolArea().indexOf("/") + 1, e.getSchoolArea().lastIndexOf("/")));
                tempBean.setAreaOrcounty(e.getSchoolArea().substring(e.getSchoolArea().lastIndexOf("/") + 1));
            }
            return tempBean;
        }).collect(Collectors.toList());

        try (OutputStream out = response.getOutputStream()) {
            String fileName = URLEncoder.encode(title, StandardCharsets.UTF_8.toString());
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"; filename*=utf-8''" + fileName);
            ExcelExportUtils.getInstance().exportObjects2Excel(schoolExcelReportBeans, SchoolExcelReportBean.class, true, "北斗星航", true, out);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("导出失败：" + e.getMessage());
        }
        return WrapMapper.ok(true);
    }
}
