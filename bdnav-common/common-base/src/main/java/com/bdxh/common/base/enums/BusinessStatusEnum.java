package com.bdxh.common.base.enums;

/**
 * @Description: 微信支付状态枚举类
 * @Author: Kang
 * @Date: 2019/6/18 14:21
 */
public enum BusinessStatusEnum {

    NO_PROCESS(new Byte("1"), "未处理"),
    HASE_PROCESS(new Byte("２"), "已处理");

    private Byte code;

    private String desc;

    BusinessStatusEnum(Byte code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Byte getCode() {
        return code;
    }

    public void setCode(Byte code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
