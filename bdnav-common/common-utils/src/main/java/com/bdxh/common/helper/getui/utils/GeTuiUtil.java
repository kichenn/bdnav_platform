package com.bdxh.common.helper.getui.utils;

import com.bdxh.common.helper.getui.constant.GeTuiConstant;
import com.bdxh.common.helper.getui.entity.AppLinkTemplate;
import com.bdxh.common.helper.getui.entity.AppNotificationTemplate;
import com.bdxh.common.helper.getui.entity.AppTransmissionTemplate;
import com.bdxh.common.helper.getui.request.AppPushRequest;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.gexin.rp.sdk.template.style.Style0;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 个推相关
 * @Author: Kang
 * @Date: 2019/5/7 14:08
 */
public class GeTuiUtil {


    /**
     * 对单个用户推送消息(此接口支持少量，速度比较快)
     */
    public static Map<String, Object> appPush(AppPushRequest request) {
        IGtPush push = new IGtPush(GeTuiConstant.HOST, request.getAppKey(), request.getMasterSecret());
        // 定义"点击链接打开通知模板"，
        LinkTemplate template = setLinkTemplate(request.getAppId(), request.getAppKey(), request.getAppTemplate());
        // 定义"AppMessage"类型消息对象
        SingleMessage message = new SingleMessage();
        // 设置消息是否离线
        message.setOffline(request.isOffline());
        if (message.isOffline()) {
            //设置离线时间
            message.setOfflineExpireTime(request.getOfflineExpireTime());
        }
        message.setData(template);
        message.setPushNetWorkType(request.getPushNetWorkType());

        //定义发送对象
        Target target = new Target();
        target.setAppId(request.getAppId());
        target.setClientId(request.getClientId().get(0));
        IPushResult ret = null;
        try {
            ret = push.pushMessageToSingle(message, target);
        } catch (RequestException e) {
            e.printStackTrace();
            //异常后重发
            ret = push.pushMessageToSingle(message, target, e.getRequestId());
        }
        return ret.getResponse();
    }


    /**
     * 对指定列表用户推送消息(批量，速度慢)
     * 此接口有频次控制，申请修改请联系第三方人员的邮箱：kegf@getui.com
     */
    public static Map<String, Object> appBatchPush(AppPushRequest request) {
        // 配置返回每个用户返回用户状态，可选
//        System.setProperty("gexin_pushList_needDetails", "true");
        // 配置返回每个别名及其对应cid的⽤户状态，可选
        // System.setProperty("gexin_pushList_needAliasDetails", "true");
        IGtPush push = new IGtPush(GeTuiConstant.HOST, request.getAppKey(), request.getMasterSecret());
        // 通知透传模板
        NotificationTemplate template = setNotificationTemplate(request.getAppId(), request.getAppKey(), request.getAppNotificationTemplate());
        ListMessage message = new ListMessage();
        message.setData(template);
        // 设置消息是否离线
        message.setOffline(request.isOffline());
        if (message.isOffline()) {
            //设置离线时间
            message.setOfflineExpireTime(request.getOfflineExpireTime());
        }
        // 配置推送目标
        List<Target> targets = new ArrayList<>();

        for (int i = 0; i < request.getClientId().size(); i++) {
            Target target = new Target();
            target.setAppId(request.getAppId());
            target.setClientId(request.getClientId().get(i));
            //别名
            // target.setAlias(Alias1);
            targets.add(target);
        }
        // taskId用于在推送时去查找对应的message
        String taskId = push.getContentId(message);
        IPushResult ret = push.pushMessageToList(taskId, targets);
        return ret.getResponse();
    }


    /**
     * 对单个用户推送自定义透传消息(此接口支持少量，速度比较快)
     */
    public static Map<String, Object> appCustomPush(AppPushRequest request) {
        IGtPush push = new IGtPush(GeTuiConstant.HOST, request.getAppKey(), request.getMasterSecret());
        TransmissionTemplate template = setTransmissionTemplate(request.getAppId(), request.getAppKey(),request.getAppTransmissionTemplate());
        // 定义"AppMessage"类型消息对象
        SingleMessage message = new SingleMessage();
        // 设置消息是否离线
        message.setOffline(request.isOffline());
        if (message.isOffline()) {
            //设置离线时间
            message.setOfflineExpireTime(request.getOfflineExpireTime());
        }
        message.setData(template);
        message.setPushNetWorkType(request.getPushNetWorkType());

        //定义发送对象
        Target target = new Target();
        target.setAppId(request.getAppId());
        target.setClientId(request.getClientId().get(0));
        IPushResult ret = null;
        try {
            ret = push.pushMessageToSingle(message, target);
        } catch (RequestException e) {
            e.printStackTrace();
            //异常后重发
            ret = push.pushMessageToSingle(message, target, e.getRequestId());
        }
        return ret.getResponse();
    }

    /**
     * 对指定列表用户推送消息(批量，速度慢)
     * 此接口有频次控制，申请修改请联系第三方人员的邮箱：kegf@getui.com
     */
    public static Map<String, Object> appCustomBatchPush(AppPushRequest request) {
        // 配置返回每个用户返回用户状态，可选
//        System.setProperty("gexin_pushList_needDetails", "true");
        // 配置返回每个别名及其对应cid的⽤户状态，可选
        // System.setProperty("gexin_pushList_needAliasDetails", "true");
        IGtPush push = new IGtPush(GeTuiConstant.HOST, request.getAppKey(), request.getMasterSecret());
        // 自定义透传模板
        TransmissionTemplate template = setTransmissionTemplate(request.getAppId(), request.getAppKey(),request.getAppTransmissionTemplate());
        ListMessage message = new ListMessage();
        message.setData(template);
        // 设置消息是否离线
        message.setOffline(request.isOffline());
        if (message.isOffline()) {
            //设置离线时间
            message.setOfflineExpireTime(request.getOfflineExpireTime());
        }
        // 配置推送目标
        List<Target> targets = new ArrayList<>();

        for (int i = 0; i < request.getClientId().size(); i++) {
            Target target = new Target();
            target.setAppId(request.getAppId());
            target.setClientId(request.getClientId().get(i));
            //别名
            // target.setAlias(Alias1);
            targets.add(target);
        }
        // taskId用于在推送时去查找对应的message
        String taskId = push.getContentId(message);
        IPushResult ret = push.pushMessageToList(taskId, targets);
        return ret.getResponse();
    }


    /**
     * 普通模版配置
     *
     * @param appId
     * @param appKey
     * @param appTemplate
     * @return
     */
    public static LinkTemplate setLinkTemplate(String appId, String appKey, AppLinkTemplate appTemplate) {
        LinkTemplate template = new LinkTemplate();
        // 设置APPID与APPKEY
        template.setAppId(appId);
        template.setAppkey(appKey);
        Style0 style = new Style0();
        // 设置通知栏标题与内容
        style.setTitle(appTemplate.getTitle());
        style.setText(appTemplate.getText());
        // 配置通知栏图标("icon.png")
        style.setLogo(appTemplate.getLogo());
        // 配置通知栏网络图标
        style.setLogoUrl(appTemplate.getLogoUrl());
        // 设置通知是否响铃，
        style.setRing(appTemplate.isRing());
        //设置通知是否震动，
        style.setVibrate(appTemplate.isVibrate());
        //设置通知是否允许清除
        style.setClearable(appTemplate.isClearable());
        template.setStyle(style);
        // 设置打开的网地址
        template.setUrl(appTemplate.getUrl());
        return template;
    }

    /**
     * 穿透模版配置
     */
    public static NotificationTemplate setNotificationTemplate(String appId, String appKey, AppNotificationTemplate appNotificationTemplate) {
        NotificationTemplate template = new NotificationTemplate();
        // 设置APPID与APPKEY
        template.setAppId(appId);
        template.setAppkey(appKey);
        Style0 style = new Style0();
        // 设置通知栏标题与内容
        style.setTitle(appNotificationTemplate.getTitle());
        style.setText(appNotificationTemplate.getText());
        // 配置通知栏图标 "icon.png"
        style.setLogo(appNotificationTemplate.getLogo());
        // 配置通知栏⽹络图标
        style.setLogoUrl(appNotificationTemplate.getLogoUrl());
        // 设置通知是否响铃，
        style.setRing(appNotificationTemplate.isRing());
        //设置通知是否震动，
        style.setVibrate(appNotificationTemplate.isVibrate());
        //设置通知是否允许清除
        style.setClearable(appNotificationTemplate.isClearable());
        template.setStyle(style);
        // 透传消息设置，1为强制启动应用，客户端接收到消息后就会⽴即启动应用；2为等待应用启动
        template.setTransmissionType(appNotificationTemplate.getTransmissionType());
        //穿透消息内容
        template.setTransmissionContent(appNotificationTemplate.getTransmissionContent());
        return template;
    }


    //自定义出穿透模板
    //仅传递信息，不做任何展示推送一串代码给应用，该代码仅此app可以解析。收到透传消息时，界面不作任何展示，用户无感知，应用收到命令后按代码执行操作。
    public static TransmissionTemplate setTransmissionTemplate(String appId, String appKey, AppTransmissionTemplate appTransmissionTemplate) {
        TransmissionTemplate template = new TransmissionTemplate();
        template.setAppId(appId);
        template.setAppkey(appKey);
        // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
        template.setTransmissionType(appTransmissionTemplate.getTransmissionType());
        template.setTransmissionContent(appTransmissionTemplate.getTransmissionContent());
        // 设置定时展示时间
        // template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
        return template;
    }



    public static void main(String[] args) {

     AppPushRequest appPushRequest = new AppPushRequest();
        appPushRequest.setAppId("RHcyuojO3l8unOKXhpvgk5");
        appPushRequest.setAppKey("hUnVoqBByZ6ptQutCjbD84");
        appPushRequest.setMasterSecret("sFNspJai4W8aaxxB74WTVA");
        List<String> clientIds = new ArrayList<>();
        clientIds.add("a257d94560d928983488b2b9e8bb6e1c");
        appPushRequest.setClientId(clientIds);

        //普通模版
   /*     AppLinkTemplate appLinkTemplate = new AppLinkTemplate();
        appLinkTemplate.setTitle("测试标题1111");
        appLinkTemplate.setText("测试内容。。。。。");
        appLinkTemplate.setUrl("http://www.baidu.com");
        appLinkTemplate.setLogo("icon.png");*/
//        appLinkTemplate.setLogoUrl("");

        //穿透模版
/*        AppNotificationTemplate appNotificationTemplate = new AppNotificationTemplate();
        appNotificationTemplate.setTitle("穿透标题测试");
        appNotificationTemplate.setText("穿透内容测试");
        appNotificationTemplate.setUrl("http://www.baidu.com");
        appNotificationTemplate.setLogo("icon.png");*/

        //自定义透传模板 只发送消息内容
        AppTransmissionTemplate appTransmissionTemplate =new AppTransmissionTemplate();
        appTransmissionTemplate.setTransmissionContent("穿透内容测试1233WPWPDKWPDKWDOEDKOKz");



/*        appPushRequest.setAppTemplate(appLinkTemplate);
        appPushRequest.setAppNotificationTemplate(appNotificationTemplate);*/
        appPushRequest.setAppTransmissionTemplate(appTransmissionTemplate);

        //单发普通模版
       Map<String, Object> resultMap = appCustomPush(appPushRequest);

        //群发穿透模版
    /*    Map<String, Object> resultMap = appBatchPush(appPushRequest);*/

        System.out.println(resultMap.toString());
    }
}
