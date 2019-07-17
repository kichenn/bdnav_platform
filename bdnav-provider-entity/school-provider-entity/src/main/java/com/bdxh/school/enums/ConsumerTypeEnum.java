package com.bdxh.school.enums;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**消费类型
 * @author WanMing
 * @date 2019/7/12 19:01
 */

public enum ConsumerTypeEnum {

    RESTAURANT_FOOD(new Byte("1"), "餐饮美食"),
    DAILY_LIFE(new Byte("2"), "生活日用"),
    READING_AND_STUDYING(new Byte("3"), "读书学习"),
    HEALTH_CARE(new Byte("4"), "医疗保健"),
    OTHER_CLASSIFICATION(new Byte("5"), "其他分类");

    private final Byte key;
    private final String value;

    public final static Byte RESTAURANT_FOOD_KEY = 1;
    public final static Byte DAILY_LIFE_KEY = 2;
    public final static Byte READING_AND_STUDYING_KEY = 3;
    public final static Byte HEALTH_CARE_KEY = 4;
    public final static Byte OTHER_CLASSIFICATION_KEY = 5;
    public final static String RESTAURANT_FOOD_VALUE = "餐饮美食";
    public final static String DAILY_LIFE_VALUE = "生活日用";
    public final static String READING_AND_STUDYING_VALUE = "读书学习";
    public final static String HEALTH_CARE_VALUE = "医疗保健";
    public final static String OTHER_CLASSIFICATION_VALUE = "其他分类";

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
