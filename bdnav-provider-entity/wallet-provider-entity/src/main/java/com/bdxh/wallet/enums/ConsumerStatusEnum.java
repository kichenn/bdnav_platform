package com.bdxh.wallet.enums;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author WanMing
 * @date 2019/7/12 14:53
 */

public enum ConsumerStatusEnum {


    NO_PAY(new Byte("1"), "未扣款"),
    PAY_SUCCESS (new Byte("2"), "扣款成功"),
    PAYING (new Byte("3"), "扣款中"),
    PAY_FAILURE(new Byte("4"), "扣款失败");

    private final Byte key;
    private final String value;

    public final static Byte NO_PAY_KEY = 1;
    public final static Byte PAY_SUCCESS_KEY = 2;
    public final static Byte PAYING_KEY = 3;
    public final static Byte PAY_FAILURE_KEY = 4;
    public final static String NO_PAY_VALUE = "未扣款";
    public final static String PAY_SUCCESS_VALUE = "扣款成功";
    public final static String PAYING_VALUE = "扣款中";
    public final static String PAY_FAILURE_VALUE = "扣款失败";

    private ConsumerStatusEnum(Byte key, String value) {
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
        for (ConsumerStatusEnum c : ConsumerStatusEnum.values()) {
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
        for (ConsumerStatusEnum c : ConsumerStatusEnum.values()) {
            if (c.getKey().equals(key)) {
                return c.value;
            }
        }
        return StringUtils.EMPTY;
    }

    public static List<Map<String, Object>> getEnums() {
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        ConsumerStatusEnum[] bs = ConsumerStatusEnum.values();
        for (ConsumerStatusEnum projectType : bs) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("key", projectType.getKey());
            map.put("value", projectType.getValue());
            data.add(map);
        }
        return data;
    }
}
