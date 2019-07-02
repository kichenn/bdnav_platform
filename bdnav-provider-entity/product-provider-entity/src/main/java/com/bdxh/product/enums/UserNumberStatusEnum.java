package com.bdxh.product.enums;

public enum UserNumberStatusEnum {
    ADD(new Byte("1"), "增加"),
    REMOVE(new Byte("2"), "减少");

    private Byte code;

    private String desc;

    UserNumberStatusEnum(Byte code, String desc) {
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
