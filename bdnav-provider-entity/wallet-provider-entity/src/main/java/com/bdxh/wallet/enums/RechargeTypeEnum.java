package com.bdxh.wallet.enums;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**充值类型枚举
 * @author WanMing
 * @date 2019/7/18 18:36
 */

public enum RechargeTypeEnum {



    ON_LINE_RECHARGE(new Byte("1"), "线上充值"),
    GENERATION_OF_CHARGE(new Byte("2"), "线上代充"),
    ALL_IN_ONE_RECHARGE(new Byte("3"), "一体机自充"),
    WINDOW_RECHARGE(new Byte("4"), "窗口充值");

    private final Byte key;
    private final String value;

    public final static Byte ON_LINE_RECHARGEKEY = 1;
    public final static Byte GENERATION_OF_CHARGE_KEY = 2;
    public final static Byte ALL_IN_ONE_RECHARGEKEY = 3;
    public final static Byte WINDOW_RECHARGE_KEY = 4;
    public final static String ON_LINE_RECHARGE_VALUE = "线上充值";
    public final static String GENERATION_OF_CHARGE_VALUE = "线上代充";
    public final static String ALL_IN_ONE_RECHARGE_VALUE = "一体机自充";
    public final static String WINDOW_RECHARGE_VALUE = "窗口充值";

    private RechargeTypeEnum(Byte key, String value) {
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
        for (RechargeTypeEnum c : RechargeTypeEnum.values()) {
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
        for (RechargeTypeEnum c : RechargeTypeEnum.values()) {
            if (c.getKey().equals(key)) {
                return c.value;
            }
        }
        return StringUtils.EMPTY;
    }

    public static List<Map<String, Object>> getEnums() {
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        RechargeTypeEnum[] bs = RechargeTypeEnum.values();
        for (RechargeTypeEnum projectType : bs) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("key", projectType.getKey());
            map.put("value", projectType.getValue());
            data.add(map);
        }
        return data;
    }
}
