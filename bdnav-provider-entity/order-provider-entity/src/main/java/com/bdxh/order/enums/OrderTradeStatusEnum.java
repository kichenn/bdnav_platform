package com.bdxh.order.enums;

/**
 * @Description: 订单交易状态枚举类
 * @Author: Kang
 * @Date: 2019/6/18 14:25
 */
public enum OrderTradeStatusEnum {

    TRADING(new Byte("1"), "交易中"),
    CANCLE(new Byte("2"), "已取消"),
    DELETE(new Byte("3"), "已删除"),
    SUCCESS(new Byte("4"), "交易成功") ;


    private Byte code;

    private String desc;

    OrderTradeStatusEnum(Byte code, String desc) {
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
