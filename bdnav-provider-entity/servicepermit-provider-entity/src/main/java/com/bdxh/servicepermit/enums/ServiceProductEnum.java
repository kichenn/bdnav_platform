package com.bdxh.servicepermit.enums;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 服务商品权限枚举
 * @Author: Kang
 * @Date: 2019/6/24 10:42
 */
public enum ServiceProductEnum {
    //围栏
    FENCE(new Byte("1"), "location"),
    //上网行为
    NET(new Byte("2"), "network"),
    //应用管控
    CONTROL(new Byte("3"), "appControl");

    private final Byte key;
    private final String value;

    private ServiceProductEnum(Byte key, String value) {
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
        for (ServiceProductEnum c : ServiceProductEnum.values()) {
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
        for (ServiceProductEnum c : ServiceProductEnum.values()) {
            if (c.getKey().equals(key)) {
                return c.value;
            }
        }
        return StringUtils.EMPTY;
    }

    public static List<Map<String, Object>> getEnums() {
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        ServiceProductEnum[] bs = ServiceProductEnum.values();
        for (ServiceProductEnum projectType : bs) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("key", projectType.getKey());
            map.put("value", projectType.getValue());
            data.add(map);
        }
        return data;
    }

}



