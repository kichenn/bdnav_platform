package com.bdxh.common.helper.ali.sms.response;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.List;
import java.util.Map;

public class SmsResponseParams {

    public SmsResponseParams() {
    }

    public static SmsResponseParams Istance() {
        return new SmsResponseParams();
    }

    //是否成功
    private Boolean result;

    //状态码
    private String status;

    //生成的验证码,内容值
    private Map<String, String> dataMap;

    //所发送的手机号码
    private List<String> phones;

    //说明
    private String remark;

    public SmsResponseParams setResult(Boolean result) {
        this.result = result;
        return this;
    }

    public SmsResponseParams setStatus(String status) {
        this.status = status;
        return this;
    }

    public SmsResponseParams setCode(Map<String, String> dataMap) {
        this.dataMap = dataMap;
        return this;
    }

    public SmsResponseParams setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public SmsResponseParams setPhones(List<String> phones) {
        this.phones = phones;
        return this;
    }


    public Boolean getResult() {
        return this.result;
    }

    public String getStatus() {
        return this.status;
    }

    public Map<String, String> getDataMap() {
        return this.dataMap;
    }

    public String getRemark() {
        return this.remark;
    }

    public List<String> getPhones() {
        return this.phones;
    }

}
