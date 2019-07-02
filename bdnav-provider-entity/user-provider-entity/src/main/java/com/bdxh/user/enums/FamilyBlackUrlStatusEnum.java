package com.bdxh.user.enums;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author WanMing
 * @date 2019/6/25 10:39
 */

public enum FamilyBlackUrlStatusEnum {

    START(new Byte("1"), "启用"),
    FORBIDDEN(new Byte("2"), "禁用");

    private final Byte key;
    private final String value;

    public final static Byte START_KEY = 1;
    public final static Byte FORBIDDEN_KEY = 2;
    public final static String START_VALUE = "启用";
    public final static String FORBIDDEN_VALUE = "禁用";

    private FamilyBlackUrlStatusEnum(Byte key, String value) {
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
        for (FamilyBlackUrlStatusEnum c : FamilyBlackUrlStatusEnum.values()) {
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
        for (FamilyBlackUrlStatusEnum c : FamilyBlackUrlStatusEnum.values()) {
            if (c.getKey().equals(key)) {
                return c.value;
            }
        }
        return StringUtils.EMPTY;
    }

    public static List<Map<String, Object>> getEnums() {
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        FamilyBlackUrlStatusEnum[] bs = FamilyBlackUrlStatusEnum.values();
        for (FamilyBlackUrlStatusEnum projectType : bs) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("key", projectType.getKey());
            map.put("value", projectType.getValue());
            data.add(map);
        }
        return data;
    }

}
