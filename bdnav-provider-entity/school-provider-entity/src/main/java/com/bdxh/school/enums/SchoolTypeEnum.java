package com.bdxh.school.enums;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 学校类型
 * @Author: Kang
 * @Date: 2019/2/27 11:02
 */
public enum SchoolTypeEnum {
    PRIMARYSCHOOL(new Byte("1"), "小学"),
    JUNIORMIDDLESCHOOL(new Byte("2"), "初中"),
    HIGHSCHOOL(new Byte("3"), "高中"),
    SECONDARYSPECIALIZEDSCHOOL(new Byte("4"), "中专"),
    JUNIORCOLLEGE(new Byte("5"), "大专"),
    COLLEAGESANDUNIVERSITIES(new Byte("6"), "高校");

    private final Byte key;
    private final String value;

    public final static Byte PRIMARYSCHOOL_KEY = 1;
    public final static Byte JUNIORMIDDLESCHOOL_KEY = 2;
    public final static Byte HIGHSCHOOL_KEY = 3;
    public final static Byte SECONDARYSPECIALIZEDSCHOOL_KEY = 4;
    public final static Byte JUNIORCOLLEGE_KEY = 5;
    public final static Byte COLLEAGESANDUNIVERSITIES_KEY = 6;
    public final static String PRIMARYSCHOOL_VALUE = "小学";
    public final static String JUNIORMIDDLESCHOOL_VALUE = "初中";
    public final static String HIGHSCHOOL_VALUE = "高中";
    public final static String SECONDARYSPECIALIZEDSCHOOL_VALUE = "中专";
    public final static String JUNIORCOLLEGE_VALUE = "大专";
    public final static String COLLEAGESANDUNIVERSITIES_VALUE = "高校";

    private SchoolTypeEnum(Byte key, String value) {
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
        for (SchoolTypeEnum c : SchoolTypeEnum.values()) {
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
        for (SchoolTypeEnum c : SchoolTypeEnum.values()) {
            if (c.getKey().equals(key)) {
                return c.value;
            }
        }
        return StringUtils.EMPTY;
    }

    public static List<Map<String, Object>> getEnums() {
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        SchoolTypeEnum[] bs = SchoolTypeEnum.values();
        for (SchoolTypeEnum projectType : bs) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("key", projectType.getKey());
            map.put("value", projectType.getValue());
            data.add(map);
        }
        return data;
    }

}



