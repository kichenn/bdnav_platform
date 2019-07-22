package com.bdxh.wallet.enums;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**状态 1 未支付 2 支付中 3 支付成功 4 支付失败
 * @author WanMing
 * @date 2019/7/22 15:46
 */

public enum  RechargeStatusEnum {



    NO_PAY(new Byte("1"), "未支付"),
    PAYING(new Byte("2"), "支付中"),
    PAY_SUCCESS(new Byte("3"), "支付成功"),
    PAY_FAIL(new Byte("4"), "支付失败");

    private final Byte key;
    private final String value;

    public final static Byte NO_PAY_KEY = 1;
    public final static Byte PAYING_KEY = 2;
    public final static Byte PAY_SUCCESS_KEY = 3;
    public final static Byte PAY_FAIL_KEY = 4;
    public final static String NO_PAY_VALUE = "未支付";
    public final static String PAYING_VALUE = "支付中";
    public final static String PAY_SUCCESS_VALUE = "支付成功";
    public final static String PAY_FAILVALUE = "支付失败";

    private RechargeStatusEnum(Byte key, String value) {
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
        for (RechargeStatusEnum c : RechargeStatusEnum.values()) {
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
        for (RechargeStatusEnum c : RechargeStatusEnum.values()) {
            if (c.getKey().equals(key)) {
                return c.value;
            }
        }
        return StringUtils.EMPTY;
    }

    public static List<Map<String, Object>> getEnums() {
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        RechargeStatusEnum[] bs = RechargeStatusEnum.values();
        for (RechargeStatusEnum projectType : bs) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("key", projectType.getKey());
            map.put("value", projectType.getValue());
            data.add(map);
        }
        return data;
    }
}
