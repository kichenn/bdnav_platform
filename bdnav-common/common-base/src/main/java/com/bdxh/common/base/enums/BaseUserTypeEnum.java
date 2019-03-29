package com.bdxh.common.base.enums;

/**
 * @description: 基本用户类型枚举
 * @author: xuyuan
 * @create: 2019-03-28 10:51
 **/
public enum BaseUserTypeEnum {

    STUDENT(new Byte("1"),"学生"),
    TEACHER(new Byte("2"),"老师"),
    FAMILY(new Byte("3"),"家长");

    private Byte code;

    private String desc;

    BaseUserTypeEnum(Byte code, String desc){
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
