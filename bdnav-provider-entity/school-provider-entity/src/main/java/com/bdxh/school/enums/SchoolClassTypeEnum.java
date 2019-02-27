package com.bdxh.school.enums;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 学校院系的类型
 * @Author: Kang
 * @Date: 2019/2/27 11:24
 */
public enum SchoolClassTypeEnum {
    COLLEGE("1", "学院"),
    SERIES("2", "系"),
    MAJOR("3", "专业"),
    GRADE("4", "年级"),
    CLASS("5", "班级");

    private final String key;
    private final String value;

    public final static String THIS_PUBLIC_KEY = "1";
    public final static String THIS_PRIVATE_KEY = "2";
    public final static String THIS_PUBLIC_VALUE = "公立";
    public final static String THIS_PRIVATE_VALUE = "私立";

    private SchoolClassTypeEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public static String getKey(String value) {
        if (null == value) {
            return StringUtils.EMPTY;
        }
        for (SchoolClassTypeEnum c : SchoolClassTypeEnum.values()) {
            if (c.getValue().equals(value)) {
                return c.key;
            }
        }
        return StringUtils.EMPTY;
    }

    public static String getValue(String key) {
        if (null == key) {
            return StringUtils.EMPTY;
        }
        for (SchoolClassTypeEnum c : SchoolClassTypeEnum.values()) {
            if (c.getKey().equals(key)) {
                return c.value;
            }
        }
        return StringUtils.EMPTY;
    }

    public static List<Map<String, Object>> getEnums() {
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        SchoolClassTypeEnum[] bs = SchoolClassTypeEnum.values();
        for (SchoolClassTypeEnum projectType : bs) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("key", projectType.getKey());
            map.put("value", projectType.getValue());
            data.add(map);
        }
        return data;
    }

}



