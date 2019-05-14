package com.bdxh.common.helper.ali.sms.constant;


import java.util.concurrent.TimeUnit;

/**
 * @Description: 阿里短信入常量
 * @Author: Kang
 * @Date: 2019/3/4 15:19
 */
public class AliyunSmsConstants {

    public static String accessKeyId = "LTAINBSpEmQBcxyH";

    public static String accessKeySecret = "y76IuwVqnrID8NtUXrN8HNdA7v93Pe";

    //产品名称:云通信短信API产品,无需替换
    public static String product = "Dysmsapi";
    //产品域名,无需替换
    public static String domain = "dysmsapi.aliyuncs.com";

    /**
     * @Description: 短信相关常量头
     * @Author: Kang
     * @Date: 2019/5/14 9:51
     */
    public interface CodeConstants {
        /**
         * 验证码头
         */
        String CAPTCHA_PREFIX = "captcha:";

        /**
         * 验证码过期时间
         */
        int CAPTCHA_TIME = (int) TimeUnit.MINUTES.toSeconds(3);

    }
}
