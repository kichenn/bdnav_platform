package com.bdxh.product.enums;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 商品图片类型
 * @Author: Kang
 * @Date: 2019/7/2 15:32
 */
public enum ProductImgTypeEnum {
    IMAGE(new Byte("1"), "商品图标"),
    DETAILS_IMAGES(new Byte("2"), "商品详情图片");

    private final Byte key;
    private final String value;


    private ProductImgTypeEnum(Byte key, String value) {
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
        for (ProductImgTypeEnum c : ProductImgTypeEnum.values()) {
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
        for (ProductImgTypeEnum c : ProductImgTypeEnum.values()) {
            if (c.getKey().equals(key)) {
                return c.value;
            }
        }
        return StringUtils.EMPTY;
    }

    public static List<Map<String, Object>> getEnums() {
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        ProductImgTypeEnum[] bs = ProductImgTypeEnum.values();
        for (ProductImgTypeEnum projectType : bs) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("key", projectType.getKey());
            map.put("value", projectType.getValue());
            data.add(map);
        }
        return data;
    }
}
