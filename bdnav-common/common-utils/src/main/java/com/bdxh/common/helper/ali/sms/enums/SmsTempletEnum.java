package com.bdxh.common.helper.ali.sms.enums;

import com.bdxh.common.base.enums.ErrorCodeEnum;
import com.bdxh.common.base.exception.BusinessException;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @Description: 短信模版枚举
 * @Author: Kang
 * @Date: 2019/3/4 11:25
 */
public enum SmsTempletEnum {

    VERIFICATION_CODE("A_01", "IDENTIFYING_CODE_", "SMS_152461236", "博学派", "code", "验证码");

    //短信Id
    private String smsId;

    //缓存前缀 (前缀+手机 = 缓存key)
    private String prefixCache;

    //阿里短信模版code
    private String templetCode;

    //阿里短信签名
    private String signName;

    //阿里短信入参
    private String smsParamName;

    //说明
    private String remark;

    /**
     * @Description: 根据模板code获取枚举值
     * @Author: Kang
     * @Date: 2019/3/4 11:31
     */
    public static SmsTempletEnum getEnumByTemplateCode(String templateCode) {
        SmsTempletEnum smsTempletEnum = null;
        for (SmsTempletEnum ele : SmsTempletEnum.values()) {
            if (ele.getTempletCode().equals(templateCode)) {
                smsTempletEnum = ele;
                break;
            }
        }
        return smsTempletEnum;
    }

    /**
     * @Description: 短信id获取枚举值
     * @Author: Kang
     * @Date: 2019/3/4 11:32
     */
    public static SmsTempletEnum getEnumBySmsId(String smsId) {
        SmsTempletEnum smsTempletEnum = null;
        for (SmsTempletEnum ele : SmsTempletEnum.values()) {
            if (ele.getSmsId().equals(smsId)) {
                smsTempletEnum = ele;
                break;
            }
        }
        return smsTempletEnum;
    }

    /**
     * 根据模板code判断短信模板是否存在
     *
     * @param smsTemplateCode
     * @return
     */
    public static boolean isSmsTemplate(String smsTemplateCode) {

        if (StringUtils.isEmpty(smsTemplateCode)) {
            throw new BusinessException(ErrorCodeEnum.UAC10011020);
        }
        List<String> templetCodeList = getTemplateCodeList();

        return templetCodeList.contains(smsTemplateCode);
    }

    public static List<SmsTempletEnum> getList() {
        return Arrays.asList(SmsTempletEnum.values());
    }

    public static List<String> getTemplateCodeList() {
        List<String> templetCodeList = Lists.newArrayList();
        List<SmsTempletEnum> list = getList();
        for (SmsTempletEnum templetEnum : list) {
            if (StringUtils.isEmpty(templetEnum.getTempletCode())) {
                continue;
            }
            templetCodeList.add(templetEnum.getTempletCode());
        }
        return templetCodeList;
    }

    SmsTempletEnum(String smsId, String prefixCache, String templetCode, String signName, String smsParamName, String remark) {
        this.smsId = smsId;
        this.prefixCache = prefixCache;
        this.templetCode = templetCode;
        this.signName = signName;
        this.smsParamName = smsParamName;
        this.remark = remark;
    }


    public String getSmsId() {
        return smsId;
    }

    public void setSmsId(String smsId) {
        this.smsId = smsId;
    }

    public String getPrefixCache() {
        return prefixCache;
    }

    public void setPrefixCache(String prefixCache) {
        this.prefixCache = prefixCache;
    }

    public String getTempletCode() {
        return templetCode;
    }

    public void setTempletCode(String templetCode) {
        this.templetCode = templetCode;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public String getSmsParamName() {
        return smsParamName;
    }

    public void setSmsParamName(String smsParamName) {
        this.smsParamName = smsParamName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
