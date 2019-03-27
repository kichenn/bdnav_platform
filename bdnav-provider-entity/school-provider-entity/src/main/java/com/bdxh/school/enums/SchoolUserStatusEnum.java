package com.bdxh.school.enums;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
* @Description:  学校用户状态
* @Author: Kang
* @Date: 2019/3/27 11:09
*/
public enum SchoolUserStatusEnum {
    ENABLE(new Byte("1"), "正常"),
    PROHIBIT(new Byte("2"), "锁定");

    private final Byte key;
    private final String value;

    public final static Byte ENABLE_KEY = 1;
    public final static Byte PROHIBIT_KEY = 2;
    public final static String JENABLE_VALUE = "正常";
    public final static String PROHIBIT_VALUE = "锁定";

    private SchoolUserStatusEnum(Byte key, String value) {
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
        for (SchoolUserStatusEnum c : SchoolUserStatusEnum.values()) {
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
        for (SchoolUserStatusEnum c : SchoolUserStatusEnum.values()) {
            if (c.getKey().equals(key)) {
                return c.value;
            }
        }
        return StringUtils.EMPTY;
    }

    public static List<Map<String, Object>> getEnums() {
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        SchoolUserStatusEnum[] bs = SchoolUserStatusEnum.values();
        for (SchoolUserStatusEnum projectType : bs) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("key", projectType.getKey());
            map.put("value", projectType.getValue());
            data.add(map);
        }
        return data;
    }

}



