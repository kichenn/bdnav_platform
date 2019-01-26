package com.bdxh.common.base.enums;

/**
 * @description: 西安一卡通钱包消费支付状态
 * @author: xuyuan
 * @create: 2019-01-26 11:31
 **/
public enum PayStatusEnum {

    NO_PAY(new Byte("1"),"未支付"),
    PAYING(new Byte("2"),"支付中"),
    PAY_FAIL(new Byte("3"),"支付失败"),
    PAY_SUCCESS(new Byte("4"),"支付成功");

    private Byte code;

    private String desc;

    PayStatusEnum(Byte code, String desc){
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
