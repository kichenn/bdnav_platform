package com.bdxh.appburied.enums;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 上报App应用日志模式
 * @Author: Kang
 * @Date: 2019/4/12 15:49
 */
public enum ApplyLogModelEnum {
    SINGLE(new Byte("1"), "单个解锁"),
    ALL(new Byte("2"), "全部解锁");

    private final Byte key;
    private final String value;

    public final static Byte SINGLE_KEY = 1;
    public final static Byte ALL_KEY = 2;
    public final static String SINGLE_VALUE = "单个解锁";
    public final static String ALL_VALUE = "全部解锁";

    private ApplyLogModelEnum(Byte key, String value) {
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
        for (ApplyLogModelEnum c : ApplyLogModelEnum.values()) {
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
        for (ApplyLogModelEnum c : ApplyLogModelEnum.values()) {
            if (c.getKey().equals(key)) {
                return c.value;
            }
        }
        return StringUtils.EMPTY;
    }

    public static List<Map<String, Object>> getEnums() {
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        ApplyLogModelEnum[] bs = ApplyLogModelEnum.values();
        for (ApplyLogModelEnum projectType : bs) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("key", projectType.getKey());
            map.put("value", projectType.getValue());
            data.add(map);
        }
        return data;
    }

}



