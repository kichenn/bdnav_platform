package com.bdxh.appburied.enums;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: APP应用状态
 * @Author: Kang
 * @Date: 2019/4/12 14:21
 */
public enum AppStatusEnum {
    NORMAL(new Byte("1"), "正常"),
    LOCKING(new Byte("2"), "锁定");

    private final Byte key;
    private final String value;

    public final static Byte NORMAL_KEY = 1;
    public final static Byte LOCKING_KEY = 2;
    public final static String NORMAL_VALUE = "正常";
    public final static String LOCKING_VALUE = "锁定";

    private AppStatusEnum(Byte key, String value) {
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
        for (AppStatusEnum c : AppStatusEnum.values()) {
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
        for (AppStatusEnum c : AppStatusEnum.values()) {
            if (c.getKey().equals(key)) {
                return c.value;
            }
        }
        return StringUtils.EMPTY;
    }

    public static List<Map<String, Object>> getEnums() {
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        AppStatusEnum[] bs = AppStatusEnum.values();
        for (AppStatusEnum projectType : bs) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("key", projectType.getKey());
            map.put("value", projectType.getValue());
            data.add(map);
        }
        return data;
    }

}



