package com.bdxh.order.enums;

/**
 * @Description: 微信支付状态枚举类
 * @Author: Kang
 * @Date: 2019/6/18 14:24
 */
public enum OrderChanelTypeEnum {

    BDXH_SELF(new Byte("1"), "自有渠道");

    private Byte code;

    private String desc;

    OrderChanelTypeEnum(Byte code, String desc) {
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
