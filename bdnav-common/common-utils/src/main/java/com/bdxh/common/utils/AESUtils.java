package com.bdxh.common.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.SecureRandom;

/**
 * @description: AES加密
 * @author: xuyuan
 * @create: 2019-01-28 19:06
 **/
public class AESUtils {

    /**
     * 初始化key
     * @param key
     * @param charSet
     * @return
     * @throws Exception
     */
    public static byte[] initKey(String key, String charSet) throws Exception{
        //密钥生成器
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        //初始化密钥生成器
        keyGen.init(128, new SecureRandom(key.getBytes(Charset.forName(charSet))));  //默认128，获得无政策权限后可用192或256
        //生成密钥
        SecretKey secretKey = keyGen.generateKey();
        return secretKey.getEncoded();
    }

    /**
     * 加密
     * @param value
     * @param key
     * @return
     */
    public static String encryptAES(String value, String key) {
        return encryptAES(value,key,"utf-8");
    }

    /**
     * 加密
     * @param value
     * @param key
     * @param charSet
     * @return
     */
    public static String encryptAES(String value, String key, String charSet) {
        try {
            //初始化key
            byte[] keys = initKey(key,charSet);
            //密钥
            SecretKey secretKey = new SecretKeySpec(keys, "AES");
            //Cipher完成加密
            Cipher cipher = Cipher.getInstance("AES");
            //根据密钥对cipher进行初始化
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] data = value.getBytes(Charset.forName(charSet));
            //加密
            byte[] encrypt = cipher.doFinal(data);
            return Base64Utils.encode(encrypt);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("AES加密失败",e.getCause());
        }
    }

    /**
     * 解密 默认编码utf-8
     * @param value 待解密字符串
     * @param key 密码
     * @return
     */
    public static String decryptAES(String value, String key) {
        return decryptAES(value,key,"utf-8");
    }

    /**
     * 解密
     * @param value 待解密字符串
     * @param key 密码
     * @param charSet 字符集
     * @return
     */
    public static String decryptAES(String value, String key, String charSet) {
        try {
            //初始化key
            byte[] keys = initKey(key,charSet);
            //秘钥
            SecretKey secretKey = new SecretKeySpec(keys, "AES");
            //Cipher完成解密
            Cipher cipher = Cipher.getInstance("AES");
            //根据密钥对cipher进行初始化
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] data = Base64Utils.decodeByte(value);
            //解密
            byte[] plain = cipher.doFinal(data);
            return new String(plain,Charset.forName(charSet));
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("AES解密失败",e.getCause());
        }
    }

}
