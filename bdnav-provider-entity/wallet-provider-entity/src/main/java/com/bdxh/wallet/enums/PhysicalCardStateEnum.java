package com.bdxh.wallet.enums;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public enum PhysicalCardStateEnum {

    NORMAL(new Byte("1"), "正常"),
    REPORT_THE_LOSS_OF(new Byte("2"), "挂失"),
    WRITE_OFF(new Byte("3"), "注销");



    private final Byte key;
    private final String value;

    public final static Byte NORMAL_KEY = 1;
    public final static Byte REPORT_THE_LOSS_OF_KEY = 2;
    public final static Byte WRITE_OFF_KEY = 3;

    public final static String NORMAL_VALUE= "正常";
    public final static String REPORT_THE_LOSS_OF_VALUE = "注销";
    public final static String WRITE_OFF_VALUE= "挂失";


    private PhysicalCardStateEnum(Byte key, String value) {
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
        for (PhysicalCardStateEnum p : PhysicalCardStateEnum.values()) {
            if (p.getValue().equals(value)) {
                return p.key;
            }
        }
        return null;
    }

    public static String getValue(Byte key) {
        if (null == key) {
            return StringUtils.EMPTY;
        }
        for (PhysicalCardStateEnum p : PhysicalCardStateEnum.values()) {
            if (p.getKey().equals(key)) {
                return p.value;
            }
        }
        return StringUtils.EMPTY;
    }

    public static List<Map<String, Object>> getEnums() {
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        PhysicalCardStateEnum[] bs = PhysicalCardStateEnum.values();
        for (PhysicalCardStateEnum projectType : bs) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("key", projectType.getKey());
            map.put("value", projectType.getValue());
            data.add(map);
        }
        return data;
    }
}
