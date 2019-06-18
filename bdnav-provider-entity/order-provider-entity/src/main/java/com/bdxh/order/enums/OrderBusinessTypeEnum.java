package com.bdxh.order.enums;

/**
 * @Description: 微信支付状态枚举类
 * @Author: Kang
 * @Date: 2019/6/18 14:24
 */
public enum OrderBusinessTypeEnum {

    WEIXIAO_PAID_SERVICE(new Byte("1"), "校园钱包充值"),
    CONTROL_SERVICE(new Byte("2"), "管控服务");

    private Byte code;

    private String desc;

    OrderBusinessTypeEnum(Byte code, String desc) {
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
