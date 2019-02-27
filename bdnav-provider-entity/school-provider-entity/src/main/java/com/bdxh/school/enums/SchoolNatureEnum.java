package com.bdxh.school.enums;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 学校性质
 * @Author: Kang
 * @Date: 2019/2/27 11:23
 */
public enum SchoolNatureEnum {
    THIS_PUBLIC("1", "公立"),
    THIS_PRIVATE("2", "私立");

    private final String key;
    private final String value;

    public final static String THIS_PUBLIC_KEY = "1";
    public final static String THIS_PRIVATE_KEY = "2";
    public final static String THIS_PUBLIC_VALUE = "公立";
    public final static String THIS_PRIVATE_VALUE = "私立";

    private SchoolNatureEnum(String key, String value) {
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
        for (SchoolNatureEnum c : SchoolNatureEnum.values()) {
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
        for (SchoolNatureEnum c : SchoolNatureEnum.values()) {
            if (c.getKey().equals(key)) {
                return c.value;
            }
        }
        return StringUtils.EMPTY;
    }

    public static List<Map<String, Object>> getEnums() {
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        SchoolNatureEnum[] bs = SchoolNatureEnum.values();
        for (SchoolNatureEnum projectType : bs) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("key", projectType.getKey());
            map.put("value", projectType.getValue());
            data.add(map);
        }
        return data;
    }

}



