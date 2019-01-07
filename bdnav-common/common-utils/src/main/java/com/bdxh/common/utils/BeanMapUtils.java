package com.bdxh.common.utils;

import com.google.common.collect.Lists;
import org.dozer.DozerBeanMapper;

import java.util.Collection;
import java.util.List;

/**
 * @description: 对象copy工具类
 * @author: xuyuan
 * @create: 2018-12-17 16:57
 **/
public final class BeanMapUtils {

    private static final DozerBeanMapper dozer =new DozerBeanMapper();

    private BeanMapUtils(){

    }

    //转换对象类型
    public static <T> T map(Object source, Class<T> clazz) {
        return dozer.map(source, clazz);
    }

    //转换集合类型
    public static <T> List<T> mapList(Collection<?> sourceList, Class<T> clazz) {
        List<T> destinationList = Lists.newArrayList();
        if (sourceList!=null&&!sourceList.isEmpty()){
            for (Object sourceObject : sourceList) {
                T destinationObject = dozer.map(sourceObject, clazz);
                destinationList.add(destinationObject);
            }
        }
        return destinationList;
    }

    //对象之间copy
    public static void copy(Object source, Object destinationObject) {
        dozer.map(source, destinationObject);
    }

}
