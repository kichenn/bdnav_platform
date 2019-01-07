package com.bdxh.common.base.enums;

/**
 * @description: 微信支付状态枚举类
 * @author: xuyuan
 * @create: 2019-01-04 11:02
 **/
public enum  WxPayStatusEnum {

    NO_PAY(new Byte("1"),"未支付"),
    PAYING(new Byte("2"),"支付中"),
    PAY_SUCCESS(new Byte("3"),"支付成功"),
    PAY_FAIL(new Byte("4"),"支付失败"),
    NO_RECHARGE(new Byte("5"),"未充值"),
    RECHARGEING(new Byte("6"),"充值中"),
    RECHARGE_SUCCESS(new Byte("7"),"充值成功"),
    RECHARGE_FAIL(new Byte("8"),"充值失败");

    private Byte code;

    private String desc;

    WxPayStatusEnum(Byte code,String desc){
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
