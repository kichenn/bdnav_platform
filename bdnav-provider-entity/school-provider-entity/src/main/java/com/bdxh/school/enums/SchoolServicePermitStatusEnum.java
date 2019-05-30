package com.bdxh.school.enums;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author WanMing
 * @date 2019/5/28 12:01
 */

public enum SchoolServicePermitStatusEnum {


    VALID(new Byte("1"), "有效"),
    INVALID(new Byte("2"), "无效");

    private final Byte key;
    private final String value;

    public final static Byte VALID_KEY = 1;
    public final static Byte INVALID_KEY = 2;
    public final static String VALID_VALUE = "有效";
    public final static String INVALID_VALUE = "无效";

    SchoolServicePermitStatusEnum(Byte key, String value) {
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
        for (SchoolServicePermitStatusEnum c : SchoolServicePermitStatusEnum.values()) {
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
        for (SchoolServicePermitStatusEnum c : SchoolServicePermitStatusEnum.values()) {
            if (c.getKey().equals(key)) {
                return c.value;
            }
        }
        return StringUtils.EMPTY;
    }

    public static List<Map<String, Object>> getEnums() {
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        SchoolServicePermitStatusEnum[] bs = SchoolServicePermitStatusEnum.values();
        for (SchoolServicePermitStatusEnum projectType : bs) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("key", projectType.getKey());
            map.put("value", projectType.getValue());
            data.add(map);
        }
        return data;
    }




}
