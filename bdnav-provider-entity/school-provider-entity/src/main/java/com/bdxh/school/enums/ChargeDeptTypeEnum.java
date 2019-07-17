package com.bdxh.school.enums;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**部门类型
 * @author WanMing
 * @date 2019/7/17 10:26
 */

public enum ChargeDeptTypeEnum {


    RECHARGE_DEPT(new Byte("1"), "充值部门"),
    CONSUMER_DEPT(new Byte("2"), "消费部门");

    private final Byte key;
    private final String value;

    public final static Byte RECHARGE_DEPT_KEY = 1;
    public final static Byte CONSUMER_DEPT_KEY = 2;
    public final static String RECHARGE_DEPT_VALUE = "充值部门";
    public final static String CONSUMER_DEPT_VALUE = "消费部门";

    private ChargeDeptTypeEnum(Byte key, String value) {
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
        for (ChargeDeptTypeEnum c : ChargeDeptTypeEnum.values()) {
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
        for (ChargeDeptTypeEnum c : ChargeDeptTypeEnum.values()) {
            if (c.getKey().equals(key)) {
                return c.value;
            }
        }
        return StringUtils.EMPTY;
    }

    public static List<Map<String, Object>> getEnums() {
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        ChargeDeptTypeEnum[] bs = ChargeDeptTypeEnum.values();
        for (ChargeDeptTypeEnum projectType : bs) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("key", projectType.getKey());
            map.put("value", projectType.getValue());
            data.add(map);
        }
        return data;
    }
}
