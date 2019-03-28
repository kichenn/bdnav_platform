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
public enum SchoolPermissionTypeEnum {
    ENUMS(new Byte("1"), "菜单"),
    BUTTON(new Byte("2"), "按钮");

    private final Byte key;
    private final String value;

    public final static Byte ENUMS_KEY = 1;
    public final static Byte BUTTON_KEY = 2;
    public final static String ENUMS_VALUE = "菜单";
    public final static String BUTTON_VALUE = "按钮";

    private SchoolPermissionTypeEnum(Byte key, String value) {
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
        for (SchoolPermissionTypeEnum c : SchoolPermissionTypeEnum.values()) {
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
        for (SchoolPermissionTypeEnum c : SchoolPermissionTypeEnum.values()) {
            if (c.getKey().equals(key)) {
                return c.value;
            }
        }
        return StringUtils.EMPTY;
    }

    public static List<Map<String, Object>> getEnums() {
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        SchoolPermissionTypeEnum[] bs = SchoolPermissionTypeEnum.values();
        for (SchoolPermissionTypeEnum projectType : bs) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("key", projectType.getKey());
            map.put("value", projectType.getValue());
            data.add(map);
        }
        return data;
    }

}



