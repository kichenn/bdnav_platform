package com.bdxh.common.helper.ali.sms.enums;

/**
 * @Description: 短信模版枚举
 * @Author: Kang
 * @Date: 2019/3/4 11:25
 */
public enum SmsTempletEnum {

    TEMPLATE_VERIFICATION("SMS_6390432", "身份验证", "code,product"),
    VERIFICATION_CODE("SMS_152461236", "博学派", "code");

    //阿里短信模版code
    private String templetCode;

    //阿里短信签名
    private String signName;

    //阿里短信入参
    private String smsParamName;

    SmsTempletEnum(String templetCode, String signName, String smsParamName) {
        this.templetCode = templetCode;
        this.signName = signName;
        this.smsParamName = smsParamName;
    }

    public String getTempletCode() {
        return templetCode;
    }

    public String getSignName() {
        return signName;
    }

    public String getSmsParamName() {
        return smsParamName;
    }

}
