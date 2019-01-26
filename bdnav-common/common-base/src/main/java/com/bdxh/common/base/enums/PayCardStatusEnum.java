package com.bdxh.common.base.enums;

/**
 * @description: 西安一卡通钱包充值支付状态
 * @author: xuyuan
 * @create: 2019-01-04 11:02
 **/
public enum PayCardStatusEnum {

    NO_PAY(new Byte("1"),"未支付"),
    PAYING(new Byte("2"),"支付中"),
    PAY_FAIL(new Byte("3"),"支付失败"),
    PAY_SUCCESS(new Byte("4"),"支付成功"),
    NO_RECHARGE(new Byte("5"),"未充值"),
    RECHARGEING(new Byte("6"),"充值中"),
    RECHARGE_FAIL(new Byte("7"),"充值失败"),
    RECHARGE_SUCCESS(new Byte("8"),"充值成功");

    private Byte code;

    private String desc;

    PayCardStatusEnum(Byte code, String desc){
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
