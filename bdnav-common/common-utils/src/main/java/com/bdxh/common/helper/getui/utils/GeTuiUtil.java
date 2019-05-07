//package com.bdxh.common.helper.getui.utils;
//
//import com.gexin.rp.sdk.base.IPushResult;
//import com.gexin.rp.sdk.base.impl.AppMessage;
//import com.gexin.rp.sdk.http.IGtPush;
//import com.gexin.rp.sdk.template.LinkTemplate;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @Description: 个推相关
// * @Author: Kang
// * @Date: 2019/5/7 14:08
// */
//public class GeTuiUtil {
//
//
//    //定义常量, appId、appKey、masterSecret 采用本文档 "第二步 获取访问凭证 "中获得的应用配置
//    private static String appId = "7vARA2BsxE8xt5iV0aTAa9";
//    private static String appKey = "RYd8R3YUcg82gm4JJVuyZA";
//    private static String masterSecret = "D70ROS9CFI98zBvaxT8bf7";
//    private static String url = "http://sdk.open.api.igexin.com/apiex.htm";
//
//    public static void appPush() {
//        IGtPush push = new IGtPush(url, appKey, masterSecret);
//
//        // 定义"点击链接打开通知模板"，并设置标题、内容、链接
//        LinkTemplate template = new LinkTemplate();
//        template.setAppId(appId);
//        template.setAppkey(appKey);
//        template.setTitle("请填写通知标题");
//        template.setText("请填写通知内容");
//        template.setUrl("http://getui.com");
//
//        List<String> appIds = new ArrayList<String>();
//        appIds.add(appId);
//
//        // 定义"AppMessage"类型消息对象，设置消息内容模板、发送的目标App列表、是否支持离线发送、以及离线消息有效期(单位毫秒)
//        AppMessage message = new AppMessage();
//        message.setData(template);
//        message.setAppIdList(appIds);
//        message.setOffline(true);
//        message.setOfflineExpireTime(1000 * 600);
//
//        IPushResult ret = push.pushMessageToApp(message);
//        System.out.println(ret.getResponse().toString());
//    }
//
//    public static void main(String[] args) {
//        appPush();
//    }
//}
