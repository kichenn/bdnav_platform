package com.bdxh.common.base.enums;

public enum BaseProductImgTypeEnum {

    IOCIMAGES(new Byte("1"), "图标图片"),
    IMAGE(new Byte("2"), "商品详情图片");

    private Byte code;

    private String desc;

    BaseProductImgTypeEnum(Byte code, String desc) {
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
