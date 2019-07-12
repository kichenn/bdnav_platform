package com.bdxh.school.enums;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author WanMing
 * @date 2019/7/11 16:06
 */

public enum DeviceTypeEnum {

    POS_DEVICE(new Byte("1"), "POS机"),
    GUARD_DEVICE(new Byte("2"), "门禁机"),
    SIGN_IN_DEVICE(new Byte("3"), "打卡机");

    private final Byte key;
    private final String value;

    public final static Byte POS_DEVICE_KEY = 1;
    public final static Byte GUARD_DEVICE_KEY = 2;
    public final static Byte SIGN_IN_DEVICE_KEY = 3;
    public final static String POS_DEVICE_VALUE = "POS机";
    public final static String GUARD_DEVICE_VALUE = "门禁机";
    public final static String SIGN_IN_DEVICE_VALUE = "打卡机";

    private DeviceTypeEnum(Byte key, String value) {
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
        for (DeviceTypeEnum c : DeviceTypeEnum.values()) {
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
        for (DeviceTypeEnum c : DeviceTypeEnum.values()) {
            if (c.getKey().equals(key)) {
                return c.value;
            }
        }
        return StringUtils.EMPTY;
    }

    public static List<Map<String, Object>> getEnums() {
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        DeviceTypeEnum[] bs = DeviceTypeEnum.values();
        for (DeviceTypeEnum projectType : bs) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("key", projectType.getKey());
            map.put("value", projectType.getValue());
            data.add(map);
        }
        return data;
    }
}
