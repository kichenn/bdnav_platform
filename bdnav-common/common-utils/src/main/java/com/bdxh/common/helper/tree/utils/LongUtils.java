package com.bdxh.common.helper.tree.utils;

/**
 * @Description: Long 工具类
 * @Author: Kang
 * @Date: 2019/2/27 9:49
 */
public class LongUtils {

    public static Boolean isEmpty(Long record) {
        return record == null || record == 0;
    }

    public static Boolean isNotEmpty(Long record) {
        return record != null && record != 0;
    }
}
