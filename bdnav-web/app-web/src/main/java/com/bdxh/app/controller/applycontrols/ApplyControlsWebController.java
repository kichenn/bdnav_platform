package com.bdxh.app.controller.applycontrols;


import com.bdxh.appburied.dto.AddInstallAppsDto;
import com.bdxh.appburied.feign.InstallAppsControllerClient;
import com.bdxh.school.dto.BlackUrlQueryDto;
import com.bdxh.school.feign.BlackUrlControllerClient;
import com.bdxh.school.feign.SchoolStrategyControllerClient;
import com.bdxh.user.dto.UpdateStudentDto;
import com.bdxh.user.feign.StudentControllerClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 应用管控API
 */
@RestController
@RequestMapping("/applyControlsWeb")
@Slf4j
@Validated
@Api(value = "应用管控API信息", tags = "应用管控交互API")
public class ApplyControlsWebController {

    @Autowired
    private StudentControllerClient studentControllerClient;

    @Autowired
    private InstallAppsControllerClient installAppsControllerClient;

    @Autowired
    private BlackUrlControllerClient blackUrlControllerClient;

    @Autowired
    private SchoolStrategyControllerClient schoolStrategyControllerClient;


    @ApiOperation(value = "修改学生个人信息", response = Boolean.class)
    @RequestMapping(value = "/modifyInfo", method = RequestMethod.POST)
    public Object modifyInfo(@Valid @RequestBody UpdateStudentDto updateStudentDto) {
        return studentControllerClient.updateStudent(updateStudentDto);
    }

    @ApiOperation(value = "显示学生信息详情", response = Boolean.class)
    @RequestMapping(value = "/infoDetails", method = RequestMethod.GET)
    public Object infoDetails(@RequestParam(name = "schoolCode")String schoolCode, @RequestParam(name = "cardNumber")String cardNumber) {
        return studentControllerClient.queryStudentInfo(schoolCode,cardNumber);
    }

    @ApiOperation(value = "本地应用上报接口", response = Boolean.class)
    @RequestMapping(value = "/ReportApp", method = RequestMethod.POST)
    public Object ReportApp(@RequestBody AddInstallAppsDto addInstallAppsDto) {
        return installAppsControllerClient.addInstallApp(addInstallAppsDto);
    }

    @ApiOperation(value = "学校黑名单", response = Boolean.class)
    @RequestMapping(value = "/blackList", method = RequestMethod.POST)
    public Object blackList(@RequestBody BlackUrlQueryDto blackUrlQueryDto) {
        return blackUrlControllerClient.findBlackInConditionPaging(blackUrlQueryDto);
    }

    @ApiOperation(value = "应用版本查询", response = Boolean.class)
    @RequestMapping(value = "/versionUpdating", method = RequestMethod.POST)
    public Object versionUpdating (@RequestBody BlackUrlQueryDto blackUrlQueryDto) {
        return blackUrlControllerClient.findBlackInConditionPaging(blackUrlQueryDto);
    }

}
