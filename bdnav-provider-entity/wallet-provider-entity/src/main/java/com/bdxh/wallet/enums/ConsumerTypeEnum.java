package com.bdxh.wallet.enums;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author WanMing
 * @date 2019/7/12 14:45
 */

public enum ConsumerTypeEnum {


    SLOT_CARD(new Byte("1"), "刷卡"),
    BRUSH_YARDS(new Byte("2"), "刷码"),
    SCAN_QR(new Byte("3"), "扫码"),
    FACE_RECOGNITION(new Byte("4"), "人脸识别");

    private final Byte key;
    private final String value;

    public final static Byte SLOT_CARD_KEY = 1;
    public final static Byte BRUSH_YARDS_KEY = 2;
    public final static Byte SCAN_QR_KEY = 3;
    public final static Byte FACE_RECOGNITION_KEY = 4;
    public final static String SLOT_CARD_VALUE = "刷卡";
    public final static String BRUSH_YARDS_VALUE = "刷码";
    public final static String SCAN_QR_VALUE = "扫码";
    public final static String FACE_RECOGNITION_VALUE = "人脸识别";

    private ConsumerTypeEnum(Byte key, String value) {
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
        for (ConsumerTypeEnum c : ConsumerTypeEnum.values()) {
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
        for (ConsumerTypeEnum c : ConsumerTypeEnum.values()) {
            if (c.getKey().equals(key)) {
                return c.value;
            }
        }
        return StringUtils.EMPTY;
    }

    public static List<Map<String, Object>> getEnums() {
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        ConsumerTypeEnum[] bs = ConsumerTypeEnum.values();
        for (ConsumerTypeEnum projectType : bs) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("key", projectType.getKey());
            map.put("value", projectType.getValue());
            data.add(map);
        }
        return data;
    }
}
