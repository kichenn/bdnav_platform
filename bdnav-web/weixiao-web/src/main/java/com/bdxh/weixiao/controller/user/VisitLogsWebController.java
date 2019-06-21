package com.bdxh.weixiao.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.bdxh.account.entity.UserDevice;
import com.bdxh.account.feign.UserDeviceControllerClient;
import com.bdxh.common.helper.getui.constant.GeTuiConstant;
import com.bdxh.common.helper.getui.entity.AppTransmissionTemplate;
import com.bdxh.common.helper.getui.request.AppPushRequest;
import com.bdxh.common.helper.getui.utils.GeTuiUtil;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.AddBlackUrlDto;
import com.bdxh.school.entity.BlackUrl;
import com.bdxh.school.feign.BlackUrlControllerClient;
import com.bdxh.user.feign.VisitLogsControllerClient;
import com.bdxh.user.vo.VisitLogsVo;
import com.bdxh.weixiao.configration.security.entity.UserInfo;
import com.bdxh.weixiao.configration.security.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: binzh
 * @create: 2019-05-21 19:37
 **/
@Slf4j
@RequestMapping(value = "/visitLogWeb")
@RestController
@Api(value = "上网行为管理----上网行为管理控制器", tags = "上网行为管理----上网行为管理控制器")
@Validated
public class VisitLogsWebController {
    @Autowired
    private VisitLogsControllerClient visitLogsControllerClient;

    @Autowired
    private BlackUrlControllerClient blackUrlControllerClient;

    @Autowired
    private UserDeviceControllerClient userDeviceControllerClient;

    /**
     * 收费服务
     * @param cardNumber
     * @return
     */
    @ApiOperation(value = "家长查询单个孩子浏览网站日志接口",response = VisitLogsVo.class)
    @RequestMapping(value="/queryVisitLogByCardNumber",method = RequestMethod.POST)
    public Object queryVisitLogByCardNumber(@RequestParam(name="cardNumber")@NotNull(message = "cardNumber不能为空")  String cardNumber){
        try {
            UserInfo userInfo = SecurityUtils.getCurrentUser();
            return visitLogsControllerClient.queryVisitLogByCardNumber(userInfo.getSchoolCode(),cardNumber);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error();
        }
    }


    @RequestMapping(value = "/addBlacklist", method = RequestMethod.POST)
    @ApiOperation(value = "家长添加url黑名单", response = Boolean.class)
    public Object addBlacklist(@Validated @RequestBody AddBlackUrlDto addBlackUrlDto) {
        addBlackUrlDto.setUrlType(Long.valueOf(2));//标识为家长添加的黑名单
        Wrapper wrapMapper = blackUrlControllerClient.addBlack(addBlackUrlDto);
        String aap=String.valueOf(wrapMapper.getResult());
        BlackUrl bu=blackUrlControllerClient.findBlackUrlById(Long.valueOf(aap)).getResult();
        if (bu!=null) {
           UserDevice userDevices = userDeviceControllerClient.findUserDeviceByCodeOrCard(bu.getSchoolCode(),bu.getCardNumber()).getResult();
            if (userDevices!=null) {
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
                obj.put("key", "blackUrlToPush");
                obj.put("data",bu);
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