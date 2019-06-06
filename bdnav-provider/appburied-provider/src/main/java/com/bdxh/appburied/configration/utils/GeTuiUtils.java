package com.bdxh.appburied.configration.utils;

import com.bdxh.common.helper.getui.constant.GeTuiConstant;
import com.bdxh.common.helper.getui.entity.AppNotificationTemplate;
import com.bdxh.common.helper.getui.request.AppPushRequest;
import com.bdxh.common.helper.getui.utils.GeTuiUtil;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: binzh
 * @create: 2019-05-21 15:53
 **/
public class GeTuiUtils {
    /**
     *
     * @param clientIds 客户端ID
     * @param title  消息标题
     * @param text   消息文本
     * @return
     */
    public static Boolean  pushMove(List<String> clientIds, String title, String text){
        //个推请求参数类
        AppPushRequest appPushRequest = new AppPushRequest();
        appPushRequest.setAppId(GeTuiConstant.GeTuiParams.appId);
        appPushRequest.setAppKey(GeTuiConstant.GeTuiParams.appKey);
        appPushRequest.setMasterSecret(GeTuiConstant.GeTuiParams.MasterSecret);
        //测试阶段先写一个死的clientId 之后会动态获取clientId
        appPushRequest.setClientId(clientIds);
        //穿透模版:发送后不会在系统通知栏展现，SDK将消息传给第三方应用后需要开发者写展现代码才能看到。
        AppNotificationTemplate appNotificationTemplate = new AppNotificationTemplate();
        appNotificationTemplate.setTitle(title);
        System.out.println(text);
        appNotificationTemplate.setTransmissionContent(text);
        appPushRequest.setAppNotificationTemplate(appNotificationTemplate);
        Map<String, Object> resultMap = GeTuiUtil.appBatchPush(appPushRequest);
        Boolean result = false;
        //如果推送成功就个推会返回 {result=ok, contentId=OSL-0520_2vlMMg1urX5H7l3cxFuwS3}
        if (resultMap.get("result").equals("ok")) {
            result = true;
        }
        return result;
    }
}