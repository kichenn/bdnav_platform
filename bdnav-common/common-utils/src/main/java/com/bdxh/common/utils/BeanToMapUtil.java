package com.bdxh.common.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeanToMapUtil {

	private static Logger logger = LoggerFactory.getLogger(BeanToMapUtil.class);

	/**
	 * 
	 * mapToObject: 将map转换成对象. <br/>
	 * 
	 * @author TangFangguo
	 * @param map
	 * @param beanClass
	 * @return
	 * @throws Exception
	 */
	public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) {
		try {
			if (map == null) {
				return null;
			}
			Object obj = beanClass.newInstance();
			BeanUtils.populate(obj, map);
			return obj;
		} catch (Exception e) {
			logger.error("将map转换成对象出现异常: " + e.getLocalizedMessage());
		}
		return null;
	}

	/**
	 * 
	 * objectToMap: 将对象转换成map. <br/>
	 * 
	 * @author TangFangguo
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> objectToMap(Object obj) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Class<?> clazz = obj.getClass();
			for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
				Field[] declaredFields = clazz.getDeclaredFields();
				for (Field field : declaredFields) {
					field.setAccessible(true);
					map.put(field.getName(), field.get(obj));
				}
			}

		} catch (Exception e) {
			logger.error("将对象转换成map时出现异常: " + e.getLocalizedMessage());
		}
		if (obj == null) {
			return null;
		}
		return map;
	}
	
	/**
	 * 
	 * objectToMap: 将对象转换成map. <br/>
	 * 
	 * @author xuyuan
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static SortedMap<String, String> objectToTreeMap(Object obj) {
		SortedMap<String, String> map = new TreeMap<String, String>();
		try {
			Class<?> clazz = obj.getClass();
			for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
				Field[] declaredFields = clazz.getDeclaredFields();
				for (Field field : declaredFields) {
					field.setAccessible(true);
					String name = field.getName();
					if("serialVersionUID".equals(name)) {
						continue;
					}
					Object value = field.get(obj);
					if (value!=null) {
						map.put(name,String.valueOf(value));
					}
					
				}
			}

		} catch (Exception e) {
			logger.error("将对象转换成map时出现异常: " + e.getLocalizedMessage());
		}
		return map;
	}

	/**
	 * 将map转换成排序参数
	 * @param map
	 * @return
	 */
	public static String mapToString(SortedMap<String, String> map){
		if (map==null||map.isEmpty()){
			return null;
		}
		StringBuilder sb=new StringBuilder();
		Set<String> keys = map.keySet();
		for (String key:keys){
			String value=map.get(key);
			sb.append(key).append("=").append(value).append("&");
		}
		sb.setLength(sb.length()-1);
		String result=sb.toString();
		return result;
	}

	 public static Object mapToObjectIns(Map<String, Object> map, Class<?> beanClass) throws Exception {
        if (map == null)   
            return null;
        Object obj = beanClass.newInstance();
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());    
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();    
        for (PropertyDescriptor property : propertyDescriptors) {  
            Method setter = property.getWriteMethod();    
            if (setter != null) {  
                setter.invoke(obj, map.get(property.getName()));   
            }  
        }
        return obj;  
    }

}
