package com.bdxh.order.enums;

/**
 * @Description: 订单支付状态枚举类
 * @Author: Kang
 * @Date: 2019/6/18 14:24
 */
public enum OrderPayStatusEnum {

    NO_PAY(new Byte("1"), "未支付"),
    PAYING(new Byte("2"), "支付中"),
    PAY_FAIL(new Byte("3"), "支付失败"),
    PAY_SUCCESS(new Byte("4"), "支付成功");

    private Byte code;

    private String desc;

    OrderPayStatusEnum(Byte code, String desc) {
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
