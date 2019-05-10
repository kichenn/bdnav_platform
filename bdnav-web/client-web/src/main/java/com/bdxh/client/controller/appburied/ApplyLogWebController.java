package com.bdxh.client.controller.appburied;

import com.bdxh.appburied.dto.AddApplyLogDto;
import com.bdxh.appburied.dto.ApplyLogQueryDto;
import com.bdxh.appburied.dto.DelOrFindAppBuriedDto;
import com.bdxh.appburied.dto.ModifyApplyLogDto;
import com.bdxh.appburied.entity.AppStatus;
import com.bdxh.appburied.feign.ApplyLogControllerClient;
import com.bdxh.client.configration.security.utils.SecurityUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.entity.SchoolUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Description: 控制器
 * @Author Kang
 * @Date 2019-04-11 16:39:55
 */
@RestController
@RequestMapping("/applyLogWeb")
@Slf4j
@Validated
@Api(value = "应用审核日志信息", tags = "应用审核日志信息交互API")
public class ApplyLogWebController {

    @Autowired
    private ApplyLogControllerClient applyLogControllerClient;


    @RequestMapping(value = "/findApplyLogById", method = RequestMethod.POST)
    @ApiOperation(value = "根据id查询APP审核日志信息", response = AppStatus.class)
    public Object findApplyLogById(@Validated @RequestBody DelOrFindAppBuriedDto findApplyLogDto) {
        SchoolUser schoolUser= SecurityUtils.getCurrentUser();
        findApplyLogDto.setSchoolCode(schoolUser.getSchoolCode());
        findApplyLogDto.setSchoolId(schoolUser.getSchoolId());
        Wrapper wrapper= applyLogControllerClient.findApplyLogById(findApplyLogDto);
        return wrapper;
    }

    @RequestMapping(value = "/findApplyLogInContionPaging", method = RequestMethod.POST)
    @ApiOperation(value = "分页上报App审核日志查询", response = AppStatus.class)
    public Object findAppStatusInContionPaging(@Validated @RequestBody ApplyLogQueryDto applyLogQueryDto) {
        SchoolUser schoolUser= SecurityUtils.getCurrentUser();
        applyLogQueryDto.setSchoolCode(schoolUser.getSchoolCode());
        applyLogQueryDto.setSchoolId(schoolUser.getSchoolId());
        return applyLogControllerClient.findApplyLogInContionPaging(applyLogQueryDto);
    }
}