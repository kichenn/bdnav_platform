package com.bdxh.common.helper.getui.constant;

/**
 * @Description: 个推相关常量
 * @Author: Kang
 * @Date: 2019/5/7 14:10
 */
public class GeTuiConstant {

    /**
     * app推送接口来发送消息
     */
    public static final String HOST = "http://sdk.open.api.igexin.com/apiex.htm";

    /**
     * 个推相关请参
     */
    public interface GeTuiParams {
        String appId = "";

        String appKey = "";

        String masterSecret = "";
    }
}
