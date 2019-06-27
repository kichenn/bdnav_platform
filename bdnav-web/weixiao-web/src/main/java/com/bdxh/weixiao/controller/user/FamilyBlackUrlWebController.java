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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    Wrapper wrapMapper = familyBlackUrlControllerClient.addFamilyBlackUrl(addFamilyBlackUrlDto);
    String aap = String.valueOf(wrapMapper.getResult());
    FamilyBlackUrl bu = familyBlackUrlControllerClient.findBlackUrlById(userInfo.getSchoolCode(), userInfo.getFamilyCardNumber(), Long.valueOf(aap)).getResult();
        if(bu !=null) {
        UserDevice userDevices = userDeviceControllerClient.findUserDeviceByCodeOrCard(bu.getSchoolCode(), bu.getCardNumber()).getResult();
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

}