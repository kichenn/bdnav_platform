package com.bdxh.wallet.enums;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public enum AccountStatusEnum {


    NORMAL(new Byte("1"), "正常"),
    ABNORMAL(new Byte("2"), "异常");


    private final Byte key;
    private final String value;

    public final static Byte NORMAL_KEY = 1;
    public final static Byte ABNORMAL_KEY = 2;


    public final static String NORMAL_VALUE= "正常";
    public final static String ABNORMAL_VALUE = "异常";



    private AccountStatusEnum(Byte key, String value) {
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
        for (AccountStatusEnum a : AccountStatusEnum.values()) {
            if (a.getValue().equals(value)) {
                return a.key;
            }
        }
        return null;
    }

    public static String getValue(Byte key) {
        if (null == key) {
            return StringUtils.EMPTY;
        }
        for (AccountStatusEnum a : AccountStatusEnum.values()) {
            if (a.getKey().equals(key)) {
                return a.value;
            }
        }
        return StringUtils.EMPTY;
    }

    public static List<Map<String, Object>> getEnums() {
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        AccountStatusEnum[] bs = AccountStatusEnum.values();
        for (AccountStatusEnum projectType : bs) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("key", projectType.getKey());
            map.put("value", projectType.getValue());
            data.add(map);
        }
        return data;
    }
}
