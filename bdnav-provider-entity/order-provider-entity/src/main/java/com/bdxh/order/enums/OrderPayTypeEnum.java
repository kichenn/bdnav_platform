package com.bdxh.order.enums;

/**
 * @description: 微信支付状态枚举类
 * @author: xuyuan
 * @create: 2019-01-04 11:02
 **/
public enum OrderPayTypeEnum {

    WECHAT_PAY(new Byte("1"),"微信支付");

    private Byte code;

    private String desc;

    OrderPayTypeEnum(Byte code, String desc){
        this.code=code;
        this.desc=desc;
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
