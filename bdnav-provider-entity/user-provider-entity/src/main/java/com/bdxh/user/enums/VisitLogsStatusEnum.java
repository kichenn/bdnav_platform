package com.bdxh.user.enums;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author WanMing
 * @date 2019/6/28 15:54
 */

public enum VisitLogsStatusEnum {


    NORMAL(new Byte("0"), "未拦截"),
    OFFLINE(new Byte("1"), "拦截");

    private final Byte key;
    private final String value;

    public final static Byte NORMAL_KEY = 0;
    public final static Byte OFFLINE_KEY = 1;
    public final static String NORMAL_VALUE = "未拦截";
    public final static String OFFLINE_VALUE = "拦截";

    private VisitLogsStatusEnum(Byte key, String value) {
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
        for (VisitLogsStatusEnum c : VisitLogsStatusEnum.values()) {
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
        for (VisitLogsStatusEnum c : VisitLogsStatusEnum.values()) {
            if (c.getKey().equals(key)) {
                return c.value;
            }
        }
        return StringUtils.EMPTY;
    }

    public static List<Map<String, Object>> getEnums() {
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        VisitLogsStatusEnum[] bs = VisitLogsStatusEnum.values();
        for (VisitLogsStatusEnum projectType : bs) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("key", projectType.getKey());
            map.put("value", projectType.getValue());
            data.add(map);
        }
        return data;
    }
}
