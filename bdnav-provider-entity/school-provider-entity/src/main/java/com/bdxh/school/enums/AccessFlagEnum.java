package com.bdxh.school.enums;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @Description: 是否递归权限 1 是 2 否
 * @Author: Kang
 * @Date: 2019/3/27 17:31
 */
public enum AccessFlagEnum {
    ALLOW(new Byte("1"), "允许"),
    NO_ALLOW(new Byte("2"), "不允许");

    private final Byte key;
    private final String value;

    public final static Byte ALLOW_KEY = 1;
    public final static Byte NO_KEY = 2;
    public final static String ALLOW_VALUE = "允许";
    public final static String NO_ALLOW_VALUE = "不允许";

    private AccessFlagEnum(Byte key, String value) {
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
        for (AccessFlagEnum c : AccessFlagEnum.values()) {
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
        for (AccessFlagEnum c : AccessFlagEnum.values()) {
            if (c.getKey().equals(key)) {
                return c.value;
            }
        }
        return StringUtils.EMPTY;
    }

    public static List<Map<String, Object>> getEnums() {
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        AccessFlagEnum[] bs = AccessFlagEnum.values();
        for (AccessFlagEnum projectType : bs) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("key", projectType.getKey());
            map.put("value", projectType.getValue());
            data.add(map);
        }
        return data;
    }

}



