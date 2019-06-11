package com.bdxh.school.enums;

/**
 * @description:
 * @author: binzh
 * @create: 2019-05-31 16:12
 **/
public enum  SchoolOrgEnum {
    COLLEGE(new Byte("1"),"学院"),
    FACULTY(new Byte("2"),"系"),
    PROFESSION(new Byte("3"),"专业"),
    GRADE (new Byte("4"),"年级"),
    CLASS(new Byte("5"),"班级"),
    ADMINISTRATION(new Byte("6"),"行政"),
    CAUCUS(new Byte("7"),"党团"),
    TEACHING(new Byte("8"),"教学"),
    LOGISTICS(new Byte("9"),"后勤"),
    OTHER(new Byte("10"),"其他");
    SchoolOrgEnum(Byte key,String value){
        this.key = key;
        this.value = value;
    }

    private final Byte key;

    private final String value;
}