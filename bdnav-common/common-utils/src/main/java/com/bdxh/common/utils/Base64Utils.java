package com.bdxh.common.utils;

import org.apache.commons.codec.binary.Base64;

import java.nio.charset.Charset;
import java.util.Objects;

/**
 * @description: base64加密类
 * @author: xuyuan
 * @create: 2019-01-28 12:18
 **/
public class Base64Utils {

    /**
     * base64加密
     * @param value 待加密字符串 字符集默认utf-8
     * @return
     */
    public static String encode(String value) {
        return encode(value,"utf-8");
    }

    /**
     * base64加密
     * @param value 待加密字符串
     * @param charSet 字符集
     * @return
     */
    public static String encode(String value,String charSet) {
        return encode(value.getBytes(Charset.forName(charSet)));
    }

    /**
     * base64加密
     * @param bytes 字节数组
     * @return
     */
    public static String encode(byte[] bytes) {
        if (Objects.isNull(bytes) || bytes.length == 0) {
            return "";
        } else {
            //此处编码默认utf-8，可随便写
            return new String(Base64.encodeBase64(bytes),Charset.forName("utf-8"));
        }
    }

    /**
     * base64解密
     * @param value 待解密字符串 字符集默认utf-8
     * @return
     */
    public static String decode(String value) {
        return Base64Utils.decode(value,"utf-8");
    }

    /**
     * base64解密
     * @param value 待解密字符串
     * @return
     */
    public static byte[] decodeByte(String value) {
        if (Objects.isNull(value) || value.equals("")) {
            return new byte[0];
        }
        return Base64.decodeBase64(value);
    }

    /**
     * base64解密
     * @param value 待解密字符串
     * @param charSet 字符集 与加密对应
     * @return
     */
    public static String decode(String value,String charSet) {
        if (Objects.isNull(value) || value.equals("")) {
            return "";
        }
        return new String(Base64.decodeBase64(value), Charset.forName(charSet));
    }

}
