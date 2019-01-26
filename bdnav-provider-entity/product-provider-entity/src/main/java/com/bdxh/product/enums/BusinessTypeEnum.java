package com.bdxh.product.enums;

/**
 * @description: 商品类型
 * @author: xuyuan
 * @create: 2019-01-21 19:24
 **/
public enum BusinessTypeEnum {

    WEIXIAO_ADD_SERVICE(Byte.valueOf("1"),"微校增值服务");

    BusinessTypeEnum(Byte code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getCodeDesc(Byte code) {
        for (BusinessTypeEnum obj : BusinessTypeEnum.values()) {
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
