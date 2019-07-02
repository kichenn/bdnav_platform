package com.bdxh.weixiao.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.bdxh.account.entity.UserDevice;
import com.bdxh.account.feign.UserDeviceControllerClient;
import com.bdxh.common.helper.getui.constant.GeTuiConstant;
import com.bdxh.common.helper.getui.entity.AppTransmissionTemplate;
import com.bdxh.common.helper.getui.request.AppPushRequest;
import com.bdxh.common.helper.getui.utils.GeTuiUtil;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.user.dto.AddFamilyBlackUrlDto;
import com.bdxh.user.dto.FamilyBlackUrlQueryDto;
import com.bdxh.user.entity.FamilyBlackUrl;
import com.bdxh.user.feign.FamilyBlackUrlControllerClient;
import com.bdxh.weixiao.configration.security.entity.UserInfo;
import com.bdxh.weixiao.configration.security.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/familyBlackUrlWeb")
@Api(value = "微校平台----家长黑名单控制器", tags = "微校平台----家长黑名单控制器")
@Validated
public class FamilyBlackUrlWebController {

    @Autowired
    private FamilyBlackUrlControllerClient familyBlackUrlControllerClient;

    @Autowired
    private UserDeviceControllerClient userDeviceControllerClient;


    @ApiOperation(value = "微校平台----家长黑名单列表")
    @RequestMapping(value = "/findFamilyBlackUrlByCondition", method = RequestMethod.POST)
    public Object findFamilyBlackUrlByCondition(@RequestBody FamilyBlackUrlQueryDto familyBlackUrlQueryDto) {
        return familyBlackUrlControllerClient.findFamilyBlackUrlByCondition(familyBlackUrlQueryDto);
    }

    @RequestMapping(value = "/addBlacklist", method = RequestMethod.POST)
    @ApiOperation(value = "微校平台----家长添加url黑名单", response = Boolean.class)
    public Object addBlacklist(@Validated @RequestBody AddFamilyBlackUrlDto addFamilyBlackUrlDto) {
    UserInfo userInfo = SecurityUtils.getCurrentUser();
        addFamilyBlackUrlDto.setSchoolCode(userInfo.getSchoolCode());
        addFamilyBlackUrlDto.setSchoolName(userInfo.getSchoolName());
        addFamilyBlackUrlDto.setFamilyName(userInfo.getName());
    Wrapper wrapMapper = familyBlackUrlControllerClient.addFamilyBlackUrl(addFamilyBlackUrlDto);
        if (wrapMapper.getCode()==500){
            return wrapMapper;
        }else{
      String aap = String.valueOf(wrapMapper.getResult());
/*    FamilyBlackUrl bu = familyBlackUrlControllerClient.findBlackUrlById(userInfo.getSchoolCode(), userInfo.getFamilyCardNumber(), Long.valueOf(aap)).getResult();*/
           log.info("====================="+userInfo.getSchoolCode());
            log.info("====================="+addFamilyBlackUrlDto.getStudentNumber());
            UserDevice userDevices = userDeviceControllerClient.findUserDeviceByCodeOrCard(userInfo.getSchoolCode(),addFamilyBlackUrlDto.getStudentNumber()).getResult();
        if (userDevices != null) {
            AppPushRequest appPushRequest = new AppPushRequest();
            appPushRequest.setAppId(GeTuiConstant.GeTuiParams.appId);
            appPushRequest.setAppKey(GeTuiConstant.GeTuiParams.appKey);
            appPushRequest.setMasterSecret(GeTuiConstant.GeTuiParams.MasterSecret);
            List<String> clientIds = new ArrayList<>();
            clientIds.add(userDevices.getClientId());
            appPushRequest.setClientId(clientIds);
            //穿透模版
            AppTransmissionTemplate appTransmissionTemplate = new AppTransmissionTemplate();
            log.info("黑名单参数====================="+userInfo.getSchoolCode());
            log.info("黑名单参数====================="+userInfo.getFamilyCardNumber());
            FamilyBlackUrl bu = familyBlackUrlControllerClient.findBlackUrlById(userInfo.getSchoolCode(), userInfo.getFamilyCardNumber(), Long.valueOf(aap)).getResult();
            JSONObject obj = new JSONObject();
            obj.put("key", "studentBlackUrlToPush");
            obj.put("data", bu);
            appTransmissionTemplate.setTransmissionContent(obj.toJSONString());
            appPushRequest.setAppTransmissionTemplate(appTransmissionTemplate);
            //群发穿透模版
            Map<String, Object> resultMap = GeTuiUtil.appCustomPush(appPushRequest);
            System.out.println(resultMap.toString());
        }
        }
    return wrapMapper;
  }


    @ApiOperation(value = "微校平台----删除家长黑名单列表")
    @RequestMapping(value = "/delFamilyBlackUrl", method = RequestMethod.POST)
    public Object delFamilyBlackUrl(@RequestParam(name = "schoolCode")  String schoolCode,
                                    @RequestParam(name = "cardNumber") String cardNumber,
                                    @RequestParam(name = "id") String id) {
        return familyBlackUrlControllerClient.delFamilyBlackUrl(schoolCode,cardNumber,id);
    }



}