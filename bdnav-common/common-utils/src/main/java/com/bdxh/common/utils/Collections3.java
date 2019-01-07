package com.bdxh.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @description: 集合工具类
 * @author: xuyuan
 * @create: 2018-12-17 14:57
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Collections3 {

	/**
	 * 返回a+b的新List.
	 */
	public static <T> List<T> union(final Collection<T> a, final Collection<T> b) {
		List<T> result = new ArrayList<>(a);
		result.addAll(b);
		return result;
	}

	/**
	 * 返回a-b的新List.
	 */
	public static <T> List<T> subtract(final Collection<T> a, final Collection<T> b) {
		List<T> list = new ArrayList<>(a);
		for (T element : b) {
			list.remove(element);
		}
		return list;
	}

	/**
	 * 返回a与b的交集的新List.
	 */
	public static <T> List<T> intersection(Collection<T> a, Collection<T> b) {
		List<T> list = new ArrayList<>();
		for (T element : a) {
			if (b.contains(element)) {
				list.add(element);
			}
		}
		return list;
	}
}
