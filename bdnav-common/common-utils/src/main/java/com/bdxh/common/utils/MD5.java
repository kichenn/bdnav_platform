package com.bdxh.common.utils;

import java.security.MessageDigest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description: MD5加密工具类
 * @author: xuyuan
 * @create: 2018-12-17 16:57
 **/
public class MD5 {
	
	private static final Logger logger = LoggerFactory.getLogger(MD5.class);

	public static String md5(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0){
					i += 256;
				}
				if (i < 16){
					buf.append("0");
				}
				buf.append(Integer.toHexString(i));
			}
			str = buf.toString();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("MD5加密失败",e);
		}
		return str;
	}
}
