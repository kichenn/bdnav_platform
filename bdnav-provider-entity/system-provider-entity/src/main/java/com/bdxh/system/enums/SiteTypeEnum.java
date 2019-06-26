package com.bdxh.system.enums;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *  网站类型枚举
 * @author WanMing
 * @date 2019/6/21 17:01
 */

public enum  SiteTypeEnum {

    UNKNOWN(new Byte("-1"), "未知网站"),
    NON_PHISHING(new Byte("0"), "非钓鱼网站"),
    PHISHING(new Byte("1"), "钓鱼网站"),
    WEBSITE_HIGH_RISK(new Byte("2"), "高风险网站");

    private final Byte key;
    private final String value;

    private SiteTypeEnum(Byte key, String value) {
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
        for (SiteTypeEnum c : SiteTypeEnum.values()) {
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
        for (SiteTypeEnum c : SiteTypeEnum.values()) {
            if (c.getKey().equals(key)) {
                return c.value;
            }
        }
        return StringUtils.EMPTY;
    }

    public static List<Map<String, Object>> getEnums() {
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        SiteTypeEnum[] bs = SiteTypeEnum.values();
        for (SiteTypeEnum projectType : bs) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("key", projectType.getKey());
            map.put("value", projectType.getValue());
            data.add(map);
        }
        return data;
    }
}
