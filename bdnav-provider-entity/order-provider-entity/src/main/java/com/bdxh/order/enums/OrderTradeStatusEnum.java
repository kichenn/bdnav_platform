package com.bdxh.order.enums;

/**
 * @description: 订单交易状态枚举类
 * @author: xuyuan
 * @create: 2019-01-09 16:25
 **/
public enum OrderTradeStatusEnum {

    TRADING(new Byte("1"),"交易中"),
    SUCCESS(new Byte("2"),"交易成功"),
    CANCLE(new Byte("3"),"已取消"),
    DELETE(new Byte("4"),"已删除");


    private Byte code;

    private String desc;

    OrderTradeStatusEnum(Byte code, String desc){
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
