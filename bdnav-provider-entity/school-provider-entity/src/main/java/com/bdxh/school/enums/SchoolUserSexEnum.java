package com.bdxh.school.enums;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 学校用户类型枚举
 * @Author: Kang
 * @Date: 2019/3/28 10:20
 */
public enum SchoolUserSexEnum {
    MALE(new Byte("1"), "男"),
    FEMALE(new Byte("2"), "女");

    private final Byte key;
    private final String value;

    public final static Byte MALE_KEY = 1;
    public final static Byte FEMALE_KEY = 2;
    public final static String MALE_VALUE = "男";
    public final static String FEMALE_VALUE = "女";

    private SchoolUserSexEnum(Byte key, String value) {
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
        for (SchoolUserSexEnum c : SchoolUserSexEnum.values()) {
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
        for (SchoolUserSexEnum c : SchoolUserSexEnum.values()) {
            if (c.getKey().equals(key)) {
                return c.value;
            }
        }
        return StringUtils.EMPTY;
    }

    public static List<Map<String, Object>> getEnums() {
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        SchoolUserSexEnum[] bs = SchoolUserSexEnum.values();
        for (SchoolUserSexEnum projectType : bs) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("key", projectType.getKey());
            map.put("value", projectType.getValue());
            data.add(map);
        }
        return data;
    }

}



