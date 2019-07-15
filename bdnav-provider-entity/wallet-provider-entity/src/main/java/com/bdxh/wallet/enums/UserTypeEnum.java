package com.bdxh.wallet.enums;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public enum UserTypeEnum {


    STUDENT(new Byte("1"), "学生"),
    TEACHER(new Byte("2"), "教师"),
    PARENTS(new Byte("3"), "家长");



    private final Byte key;
    private final String value;

    public final static Byte STUDENT_KEY = 1;
    public final static Byte TEACHER_KEY = 2;
    public final static Byte PARENTS_KEY = 3;

    public final static String STUDENT_VALUE= "老师";
    public final static String RTEACHER_VALUE = "教师";
    public final static String PARENTS_VALUE= "家长";


    private UserTypeEnum(Byte key, String value) {
        this.key = key;
        this.value = value;
    }

    public Byte getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public static Byte getKey(String value) {
        if (null == value) {
            return null;
        }
        for (UserTypeEnum u : UserTypeEnum.values()) {
            if (u.getValue().equals(value)) {
                return u.key;
            }
        }
        return null;
    }

    public static String getValue(Byte key) {
        if (null == key) {
            return StringUtils.EMPTY;
        }
        for (UserTypeEnum u : UserTypeEnum.values()) {
            if (u.getKey().equals(key)) {
                return u.value;
            }
        }
        return StringUtils.EMPTY;
    }

    public static List<Map<String, Object>> getEnums() {
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        UserTypeEnum[] bs = UserTypeEnum.values();
        for (UserTypeEnum projectType : bs) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("key", projectType.getKey());
            map.put("value", projectType.getValue());
            data.add(map);
        }
        return data;
    }
}
