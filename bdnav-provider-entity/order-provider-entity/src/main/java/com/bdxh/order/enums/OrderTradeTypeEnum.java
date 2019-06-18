package com.bdxh.order.enums;

/**
 * @Description: 微信支付状态枚举类
 * @Author: Kang
 * @Date: 2019/6/18 14:25
 */
public enum OrderTradeTypeEnum {

    WECHAT_JSAPI(new Byte("1"), "JSAPI", "微信JSAPI支付");

    private Byte code;

    private String value;

    private String desc;

    OrderTradeTypeEnum(Byte code, String value, String desc) {
        this.code = code;
        this.value = value;
        this.desc = desc;
    }

    public Byte getCode() {
        return code;
    }

    public void setCode(Byte code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
