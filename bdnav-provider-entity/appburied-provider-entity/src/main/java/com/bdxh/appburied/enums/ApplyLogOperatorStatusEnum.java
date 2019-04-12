package com.bdxh.appburied.enums;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 上报App应用日志操作状态
 * @Author: Kang
 * @Date: 2019/4/12 16:49
 */
public enum ApplyLogOperatorStatusEnum {
    WAIT(new Byte("1"), "待审核"),
    REFUSE(new Byte("2"), "审核拒绝"),
    ADOPT(new Byte("3"), "审核通过");

    private final Byte key;
    private final String value;

    public final static Byte WAIT_KEY = 1;
    public final static Byte REFUSE_KEY = 2;
    public final static Byte ADOPT_KEY = 3;
    public final static String WAIT_VALUE = "待审核";
    public final static String REFUSE_VALUE = "审核拒绝";
    public final static String ADOPT_VALUE = "审核通过";

    private ApplyLogOperatorStatusEnum(Byte key, String value) {
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
        for (ApplyLogOperatorStatusEnum c : ApplyLogOperatorStatusEnum.values()) {
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
        for (ApplyLogOperatorStatusEnum c : ApplyLogOperatorStatusEnum.values()) {
            if (c.getKey().equals(key)) {
                return c.value;
            }
        }
        return StringUtils.EMPTY;
    }

    public static List<Map<String, Object>> getEnums() {
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        ApplyLogOperatorStatusEnum[] bs = ApplyLogOperatorStatusEnum.values();
        for (ApplyLogOperatorStatusEnum projectType : bs) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("key", projectType.getKey());
            map.put("value", projectType.getValue());
            data.add(map);
        }
        return data;
    }

}



