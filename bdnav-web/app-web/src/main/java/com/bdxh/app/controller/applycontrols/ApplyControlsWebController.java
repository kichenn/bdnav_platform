package com.bdxh.app.controller.applycontrols;


import com.bdxh.account.entity.Account;
import com.bdxh.app.configration.security.utils.SecurityUtils;
import com.bdxh.appburied.dto.AddInstallAppsDto;
import com.bdxh.appburied.dto.AppStatusQueryDto;
import com.bdxh.appburied.dto.DelOrFindAppBuriedDto;
import com.bdxh.appburied.feign.AppStatusControllerClient;
import com.bdxh.appburied.feign.InstallAppsControllerClient;
import com.bdxh.appmarket.entity.AppVersion;
import com.bdxh.appmarket.feign.AppControllerClient;
import com.bdxh.appmarket.feign.AppVersionControllerClient;
import com.bdxh.common.helper.qcloud.files.FileOperationUtils;
import com.bdxh.common.helper.qcloud.files.constant.QcloudConstants;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.BlackUrlQueryDto;
import com.bdxh.school.feign.BlackUrlControllerClient;
import com.bdxh.system.feign.ControlConfigControllerClient;
import com.bdxh.user.dto.UpdateStudentDto;
import com.bdxh.user.feign.StudentControllerClient;
import com.bdxh.user.vo.StudentVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


/**
 * 应用管控API
 */
@RestController
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
    private AppStatusControllerClient appStatusControllerClient;

    @Autowired
    private AppVersionControllerClient appVersionControllerClient;

    @Autowired
    private AppControllerClient appControllerClient;


    @Autowired
    private ControlConfigControllerClient controlConfigControllerClient;


    @ApiOperation(value = "修改学生个人信息", response = Boolean.class)
    @RequestMapping(value = "/applyControlsWeb/modifyInfo", method = RequestMethod.POST)
    public Object modifyInfo(@Validated @RequestBody UpdateStudentDto updateStudentDto) {
        return studentControllerClient.updateStudent(updateStudentDto);
    }

    @ApiOperation(value = "显示学生信息详情", response = Boolean.class)
    @RequestMapping(value = "/applyControlsWeb/infoDetails", method = RequestMethod.GET)
    public Object infoDetails(@Validated @RequestParam(name = "schoolCode") String schoolCode, @RequestParam(name = "cardNumber") String cardNumber) {
        return studentControllerClient.queryStudentInfo(schoolCode, cardNumber);
    }

    @ApiOperation(value = "本地应用上报接口", response = Boolean.class)
    @RequestMapping(value = "/applyControlsWeb/ReportApp", method = RequestMethod.POST)
    public Object ReportApp(@Validated @RequestBody AddInstallAppsDto addInstallAppsDto) {
        return installAppsControllerClient.addInstallApp(addInstallAppsDto);
    }

    @ApiOperation(value = "学校黑名单", response = Boolean.class)
    @RequestMapping(value = "/applyControlsWeb/blackList", method = RequestMethod.POST)
    public Object blackList(@Validated @RequestBody BlackUrlQueryDto blackUrlQueryDto) {
        return blackUrlControllerClient.findBlackInConditionPaging(blackUrlQueryDto);
    }

    @ApiOperation(value = "查询用户被禁名单列表", response = Boolean.class)
    @RequestMapping(value = "/applyControlsWeb/disableAppList", method = RequestMethod.POST)
    public Object disableAppList(@Validated @RequestBody AppStatusQueryDto appStatusQueryDto) {
        return appStatusControllerClient.findAppStatusInContionPaging(appStatusQueryDto);
    }

    @ApiOperation(value = "最新应用版本查询", response = Boolean.class)
    @RequestMapping(value = "/applyControlsWeb/versionUpdating", method = RequestMethod.GET)
    public Object versionUpdating(@RequestParam("appId") Long appId) {
        Wrapper<AppVersion> wrapper = appVersionControllerClient.findNewAppVersion(appId);
        return WrapMapper.ok(wrapper.getResult());

    }


    @ApiOperation(value = "查询预置应用列表", response = Boolean.class)
    @RequestMapping(value = "/authenticationApp/thePresetList", method = RequestMethod.GET)
    public Object thePresetList(@RequestParam(value = "preset",defaultValue = "1") Byte preset) {
        Wrapper wrapper = appControllerClient.thePresetList(preset);
        return WrapMapper.ok(wrapper.getResult());

    }


    @ApiOperation(value = "修改个人头像", response = Boolean.class)
    @RequestMapping(value = "/applyControlsWeb/modifyInfoPhoto", method = RequestMethod.POST)
    public Object modifyInfoPhoto(MultipartFile multipartFile) {
        //获取账户信息
        Account account = SecurityUtils.getCurrentUser();
        if (account!=null){
        //查询此账户学生信息
       StudentVo studentVo = studentControllerClient.queryStudentInfo(account.getSchoolCode(), account.getCardNumber()).getResult();
        //删除腾讯云的以前图片
        FileOperationUtils.deleteFile(studentVo.getImageName(), QcloudConstants.APP_BUCKET_NAME);
        Map<String, String> result = null;
        try {
            result = FileOperationUtils.saveBatchFile(multipartFile, QcloudConstants.APP_BUCKET_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
        UpdateStudentDto updateStudentDto=new UpdateStudentDto();
        updateStudentDto.setSchoolCode(account.getSchoolCode());
        updateStudentDto.setCardNumber(account.getCardNumber());
        updateStudentDto.setImage(result.get("url"));
        updateStudentDto.setImageName(result.get("name"));
        studentControllerClient.updateStudent(updateStudentDto);

        return WrapMapper.ok(updateStudentDto.getImage());

    }else{
            return WrapMapper.error("获取token信息失败");
        }

    }

    @ApiOperation(value = "批量上报应用信息", response = Boolean.class)
    @RequestMapping(value = "/applyControlsWeb/batchSaveInstallAppsInfo", method = RequestMethod.POST)
    public Object batchSaveInstallAppsInfo(@RequestBody List<AddInstallAppsDto> appInstallList) {
        Wrapper wrapper = installAppsControllerClient.batchSaveInstallAppsInfo(appInstallList);
        return WrapMapper.ok(wrapper.getResult());
    }

    @ApiOperation(value = "删除上报应用信息", response = Boolean.class)
    @RequestMapping(value = "/applyControlsWeb/delByAppPackage", method = RequestMethod.GET)
    public Object delByAppPackage(@RequestParam("schoolCode") String schoolCode,
                                    @RequestParam("cardNumber") String cardNumber,
                                    @RequestParam("appPackage") String appPackage) {
        Wrapper wrapper = installAppsControllerClient.delByAppPackage(schoolCode,cardNumber,appPackage);
        return WrapMapper.ok(wrapper.getResult());
    }


    @ApiOperation(value = "查询应用黑白名单", response = Boolean.class)
    @RequestMapping(value = "/applyControlsWeb/findAppType", method = RequestMethod.GET)
    public Object findAppType(@RequestParam(name = "appType") Byte appType) {
        Wrapper wrapper = controlConfigControllerClient.findAppType(appType);
        return WrapMapper.ok(wrapper.getResult());
    }


}
