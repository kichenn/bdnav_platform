package com.bdxh.school.enums;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 使用类型 安卓 IOS
 */
public enum PlatformTypeEnum {
    ANDROID(new Byte("1"), "安卓"),
    IOS(new Byte("2"), "苹果");


    private final Byte key;
    private final String value;

    public final static Byte ANDROID_KEY = 1;
    public final static Byte IOS_KEY = 2;
    public final static String ANDROID_VALUE = "安卓";
    public final static String IOS_VALUE = "苹果";

    private PlatformTypeEnum(Byte key, String value) {
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
        for (PlatformTypeEnum c : PlatformTypeEnum.values()) {
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
        for (PlatformTypeEnum c : PlatformTypeEnum.values()) {
            if (c.getKey().equals(key)) {
                return c.value;
            }
        }
        return StringUtils.EMPTY;
    }

    public static List<Map<String, Object>> getEnums() {
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        PlatformTypeEnum[] bs = PlatformTypeEnum.values();
        for (PlatformTypeEnum projectType : bs) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("key", projectType.getKey());
            map.put("value", projectType.getValue());
            data.add(map);
        }
        return data;
    }
}
