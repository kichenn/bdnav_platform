package com.bdxh.app.controller.applycontrols;


import com.alibaba.fastjson.JSONObject;
import com.bdxh.account.entity.Account;
import com.bdxh.app.configration.security.utils.SecurityUtils;
import com.bdxh.appburied.dto.AddApplyLogDto;
import com.bdxh.appburied.dto.AddInstallAppsDto;
import com.bdxh.appburied.feign.AppStatusControllerClient;
import com.bdxh.appburied.feign.ApplyLogControllerClient;
import com.bdxh.appburied.feign.InstallAppsControllerClient;
import com.bdxh.appburied.vo.informationVo;
import com.bdxh.appmarket.entity.App;
import com.bdxh.appmarket.feign.AppControllerClient;
import com.bdxh.appmarket.feign.SystemAppControllerClient;
import com.bdxh.appmarket.vo.appDownloadlinkVo;
import com.bdxh.appmarket.vo.appVersionVo;
import com.bdxh.common.helper.excel.ExcelImportUtil;
import com.bdxh.common.helper.qcloud.files.FileOperationUtils;
import com.bdxh.common.helper.qcloud.files.constant.QcloudConstants;
import com.bdxh.common.utils.ValidatorUtil;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.QuerySchoolStrategy;
import com.bdxh.school.entity.School;
import com.bdxh.school.feign.BlackUrlControllerClient;
import com.bdxh.school.feign.SchoolControllerClient;
import com.bdxh.school.feign.SchoolStrategyControllerClient;
import com.bdxh.school.vo.MobileStrategyVo;
import com.bdxh.system.dto.AddFeedbackAttachDto;
import com.bdxh.system.dto.AddFeedbackDto;
import com.bdxh.system.feign.ControlConfigControllerClient;
import com.bdxh.system.feign.FeedbackControllerClient;
import com.bdxh.system.feign.SysBlackUrlControllerClient;
import com.bdxh.system.vo.SysBlackUrlVo;
import com.bdxh.user.dto.UpdateStudentDto;
import com.bdxh.user.feign.FamilyStudentControllerClient;
import com.bdxh.user.feign.StudentControllerClient;
import com.bdxh.user.vo.FamilyStudentVo;
import com.bdxh.user.vo.StudentVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;


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
    private AppControllerClient appControllerClient;


    @Autowired
    private ControlConfigControllerClient controlConfigControllerClient;

    @Autowired
    private ApplyLogControllerClient applyLogControllerClient;

    @Autowired
    private SystemAppControllerClient systemAppControllerClient;

    @Autowired
    private SchoolStrategyControllerClient schoolStrategyControllerClient;

    @Autowired
    private FeedbackControllerClient feedbackControllerClient;

    @Autowired
    private FamilyStudentControllerClient familyStudentControllerClient;

    @Autowired
    private SchoolControllerClient schoolControllerClient;

    @Autowired
    private SysBlackUrlControllerClient sysBlackUrlControllerClient;

    @ApiOperation(value = "修改学生个人信息", response = Boolean.class)
    @RequestMapping(value = "/applyControlsWeb/modifyInfo", method = RequestMethod.POST)
    public Object modifyInfo(@Validated @RequestBody UpdateStudentDto updateStudentDto) {
        return studentControllerClient.updateStudent(updateStudentDto);
    }

    @ApiOperation(value = "显示学生信息详情", response = StudentVo.class)
    @RequestMapping(value = "/applyControlsWeb/infoDetails", method = RequestMethod.GET)
    public Object infoDetails(@Validated @RequestParam(name = "schoolCode") String schoolCode, @RequestParam(name = "cardNumber") String cardNumber) {
        return studentControllerClient.queryStudentInfo(schoolCode, cardNumber);
    }

    @ApiOperation(value = "本地应用上报接口", response = Boolean.class)
    @RequestMapping(value = "/applyControlsWeb/ReportApp", method = RequestMethod.POST)
    public Object ReportApp(@Validated @RequestBody AddInstallAppsDto addInstallAppsDto) {
        return installAppsControllerClient.addInstallApp(addInstallAppsDto);
    }

    @ApiOperation(value = "学校黑名单", response = String.class)
    @RequestMapping(value = "/applyControlsWeb/blackList", method = RequestMethod.GET)
    public Object blackList(@RequestParam(name = "schoolCode") String schoolCode) {
        return blackUrlControllerClient.findBlackInList(schoolCode);
    }

    @ApiOperation(value = "查询用户被禁名单列表", response = String.class)
    @RequestMapping(value = "/applyControlsWeb/disableAppList", method = RequestMethod.GET)
    public Object disableAppList(@RequestParam(name = "schoolCode") String schoolCode, @RequestParam(name = "cardNumber") String cardNumber) {
        return appStatusControllerClient.findAppStatusInByAccount(schoolCode, cardNumber);
    }

    @ApiOperation(value = "版本更新", response = appVersionVo.class)
    @RequestMapping(value = "/applyControlsWeb/versionUpdating", method = RequestMethod.GET)
    public Object versionUpdating() {
        Wrapper<appVersionVo> wrapper = systemAppControllerClient.versionUpdating();
        return WrapMapper.ok(wrapper.getResult());

    }


    @ApiOperation(value = "查询预置应用列表", response = appDownloadlinkVo.class)
    @RequestMapping(value = "/authenticationApp/thePresetList", method = RequestMethod.GET)
    public Object thePresetList(@RequestParam(value = "preset", defaultValue = "1") Byte preset) {
        Wrapper wrapper = appControllerClient.thePresetList(preset);
        return WrapMapper.ok(wrapper.getResult());
    }


    @ApiOperation(value = "修改个人头像", response = Boolean.class)
    @RequestMapping(value = "/applyControlsWeb/modifyInfoPhoto", method = RequestMethod.POST)
    public Object modifyInfoPhoto(MultipartFile multipartFile) {
        //获取账户信息
        Account account = SecurityUtils.getCurrentUser();
        if (account != null) {
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
            UpdateStudentDto updateStudentDto = new UpdateStudentDto();
            updateStudentDto.setSchoolCode(account.getSchoolCode());
            updateStudentDto.setCardNumber(account.getCardNumber());
            updateStudentDto.setImage(result.get("url"));
            updateStudentDto.setImageName(result.get("name"));
            studentControllerClient.updateStudent(updateStudentDto);

            return WrapMapper.ok(updateStudentDto.getImage());

        } else {
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
        Wrapper wrapper = installAppsControllerClient.delByAppPackage(schoolCode, cardNumber, appPackage);
        return WrapMapper.ok(wrapper.getResult());
    }


    @ApiOperation(value = "查询应用黑盒|隐藏", response = String.class)
    @RequestMapping(value = "/authenticationApp/findAppType", method = RequestMethod.GET)
    public Object findAppType(@RequestParam(name = "appType") Byte appType) {
        Wrapper wrapper = controlConfigControllerClient.findAppType(appType);
        return WrapMapper.ok(wrapper.getResult());
    }

    @ApiOperation(value = "申请应用畅玩(解锁)", response = Boolean.class)
    @RequestMapping(value = "/applyControlsWeb/applyUnlockApplication", method = RequestMethod.POST)
    public Object applyUnlockApplication(@RequestBody AddApplyLogDto addApplyLogDto) {
        FamilyStudentVo familyStudent = familyStudentControllerClient.studentQueryInfo(addApplyLogDto.getSchoolCode(), addApplyLogDto.getCardNumber()).getResult();
        School school = schoolControllerClient.findSchoolBySchoolCode(addApplyLogDto.getSchoolCode()).getResult();
        if (null != familyStudent) {
            addApplyLogDto.setOperatorCode(familyStudent.getFCardNumber());
            addApplyLogDto.setAppKey(school.getSchoolKey());
            addApplyLogDto.setAppSecret(school.getSchoolSecret());
            Wrapper wrapper = applyLogControllerClient.addApplyLog(addApplyLogDto);
            return WrapMapper.ok(wrapper.getResult());
        }
        return WrapMapper.ok("您还没有绑定家长，请先绑定一个家长");
    }

    @ApiOperation(value = "提供学校应用下载APP链接", response = appDownloadlinkVo.class)
    @RequestMapping(value = "/applyControlsWeb/applicationDownloadLink", method = RequestMethod.GET)
    public Object applicationDownloadLink(@RequestParam(value = "schoolCode", required = false) String schoolCode) {
        Wrapper wrapper = appControllerClient.findTheApplicationList(schoolCode);
        return WrapMapper.ok(wrapper.getResult());
    }


    /**
     * @Description: 根据schoolcode查询学校策略模式
     * @Date 2019-04-18 09:52:43
     */
    @RequestMapping(value = "/findSchoolStrategyList", method = RequestMethod.GET)
    @ApiOperation(value = "查询当前学校策略", response = MobileStrategyVo.class)
    public Object findSchoolStrategyList(@RequestParam("schoolCode") String schoolCode, @RequestParam("groupId") String groupId) {
        List<MobileStrategyVo> schoolMsv = new ArrayList<>();
        List<QuerySchoolStrategy> sList = schoolStrategyControllerClient.findSchoolStrategyList(schoolCode, groupId).getResult();
        for (int i = 0; i < sList.size(); i++) {
            MobileStrategyVo msv = new MobileStrategyVo();
            msv.setPolicyName(sList.get(i).getPolicyName());
            msv.setDayMark(sList.get(i).getDayMark());
            msv.setEndDate(sList.get(i).getEndDate());
            msv.setExclusionDays(sList.get(i).getExclusionDays());
            msv.setPriority(sList.get(i).getPriority());
            msv.setStartDate(sList.get(i).getStartDate());
            msv.setTimeMark(sList.get(i).getTimeMark());
            msv.setUsableDevice(sList.get(i).getUsableDevice());
            if (StringUtils.isNotEmpty(sList.get(i).getUsableApp()) && StringUtils.isNotBlank(sList.get(i).getUsableApp())) {
                List<App> apks = appControllerClient.getAppListByids(sList.get(i).getUsableApp()).getResult();
                List<String> apkPackages = new ArrayList<>();
                for (int j = 0; j < apks.size(); j++) {
                    apkPackages.add(apks.get(j).getAppPackage());
                }
                msv.setAppPackage(apkPackages);
            }
            schoolMsv.add(msv);
        }
        return WrapMapper.ok(schoolMsv);
    }

    /**
     * 添加用户反馈信息
     * @author WanMing
     * @param addFeedbackDto
     * @return
     *
     */
    @RequestMapping(value = "/addFeedback", method = RequestMethod.POST)
    @ApiOperation(value = "添加用户反馈信息", response = Boolean.class)
    public Object addFeedback(@Validated @RequestBody AddFeedbackDto addFeedbackDto, @RequestParam(name = "multipartFiles", required = false) List<MultipartFile> multipartFiles) {
        //添加操作人的信息
        Account account = SecurityUtils.getCurrentUser();
        addFeedbackDto.setOperator(account.getId());
        addFeedbackDto.setOperatorName(account.getUserName());
        //后台上传图片
        List<AddFeedbackAttachDto> feedbackAttachVos = null;
        try {
            if (CollectionUtils.isNotEmpty(multipartFiles)) {
                feedbackAttachVos = new ArrayList<>();
                for (MultipartFile multipartFile : multipartFiles) {
                    AddFeedbackAttachDto addFeedbackAttachDto = new AddFeedbackAttachDto();
                    Map<String, String> result = FileOperationUtils.saveFile(multipartFile, QcloudConstants.APP_BUCKET_NAME);
                    addFeedbackAttachDto.setImg(result.get("url"));
                    addFeedbackAttachDto.setImgName(result.get("name"));
                    feedbackAttachVos.add(addFeedbackAttachDto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        addFeedbackDto.setImage(feedbackAttachVos);
        return feedbackControllerClient.addFeedback(addFeedbackDto);
    }

    /**
     * 查询当前用户下的申请消息
     *
     * @return
     */
    @RequestMapping(value = "/checkMymessages", method = RequestMethod.GET)
    @ApiOperation(value = "查询当前用户下的申请消息", response = informationVo.class)
    public Object checkMymessages(@RequestParam("schoolCode") String schoolCode, @RequestParam("cardNumber") String cardNumber) {
        JSONObject obj = new JSONObject();
        List<informationVo> list = applyLogControllerClient.checkMymessages(schoolCode, cardNumber).getResult();
        obj.put("data", list);
        return WrapMapper.ok(obj);
    }


    /**
     *  批量导入病毒库数据
     *
     * @Author: WanMing
     * @Date: 2019/6/24 12:54
     */
    @RequestMapping(value = "/importSysBlackUrl", method = RequestMethod.POST)
    @ApiOperation(value = "导入url列表检测安全性", response = String.class)
    public Object importSysBlackUrl(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return WrapMapper.error("文件为空,请检查文件内容");
            }
            //计时
            long start = System.currentTimeMillis();
            List<String[]> urlStr = ExcelImportUtil.readExcelNums(file, 0);
            if (CollectionUtils.isNotEmpty(urlStr)) {
                //获取所有的url集合
                List<String> urls = urlStr.stream().map(item -> item[0]).collect(Collectors.toList());
                //去重
                List<String> distinctUrls = urls.stream().distinct().collect(Collectors.toList());
                sysBlackUrlControllerClient.batchCheckSysBlackUrl(distinctUrls);
            }
            long end = System.currentTimeMillis();
            log.info("总计用时：" + (end - start) + "毫秒");
            return WrapMapper.ok("导入完成");
        } catch (IOException e) {
            log.error(e.getMessage());
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 查询本地病毒库所有数据
     *
     * @Author: WanMing
     * @Date: 2019/6/24 15:02
     */
    @RequestMapping(value = "/queryAllSysBlackUrl", method = RequestMethod.GET)
    @ApiOperation(value = "查询本地病毒库所有数据", response = SysBlackUrlVo.class)
    public Object queryAllSysBlackUrl() {
        return sysBlackUrlControllerClient.queryAllSysBlackUrl();
    }


}
