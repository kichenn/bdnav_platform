package com.bdxh.product.enums;

/**
 * @description: 商品类型
 * @author: xuyuan
 * @create: 2019-01-21 19:24
 **/
public enum  ProductTypeEnum {

    SINGLE(Byte.valueOf("1"),"单品"),
    GROUP(Byte.valueOf("2"),"套餐");

    ProductTypeEnum(Byte code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getCodeDesc(Byte code) {
        for (ProductTypeEnum obj : ProductTypeEnum.values()) {
            if (obj.code.intValue()==code.intValue()) {
                return obj.desc;
            }
        }
        return null;
    }

    private Byte code;
    private String desc;

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
