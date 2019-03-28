package com.bdxh.school.enums;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @Description: 权限组用户群组类型
 * @Author: Kang
 * @Date: 2019/3/27 14:38
 */
public enum GroupTypeEnum {
    STUDENT(new Byte("1"), "学生"),
    TEACHER(new Byte("2"), "老师");

    private final Byte key;
    private final String value;

    public final static Byte STUDENT_KEY = 1;
    public final static Byte TEACHER_KEY = 2;
    public final static String STUDENT_VALUE = "学生";
    public final static String TEACHER_VALUE = "老师";

    private GroupTypeEnum(Byte key, String value) {
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
        for (GroupTypeEnum c : GroupTypeEnum.values()) {
            if (c.getValue().equals(value)) {
                return c.key;
            }
        }
        return null;
    }

    public static String getValue(Byte key) {
        if (null == key) {
            return StringUtils.EMPTY;
        }
        for (GroupTypeEnum c : GroupTypeEnum.values()) {
            if (c.getKey().equals(key)) {
                return c.value;
            }
        }
        return StringUtils.EMPTY;
    }

    public static List<Map<String, Object>> getEnums() {
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        GroupTypeEnum[] bs = GroupTypeEnum.values();
        for (GroupTypeEnum projectType : bs) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("key", projectType.getKey());
            map.put("value", projectType.getValue());
            data.add(map);
        }
        return data;
    }

}



