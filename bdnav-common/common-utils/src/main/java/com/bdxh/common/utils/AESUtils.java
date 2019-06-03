package com.bdxh.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * @Description: AES加密
 * @Author: Kang
 * @Date: 2019/5/31 10:10
 */
@Slf4j
public class AESUtils {

    public interface AesConstant {
        //长度16位
        String WEIXIAO_KEY = "bdnav_weixiao***";
    }


    private static final String ALGORITHM = "AES/ECB/PKCS5Padding";    //"算法/模式/补码方式"

    /**
     * AES加密
     *
     * @param content 加密内容
     * @param key     加密密码，由字母或数字组成
     *                加密解密key必须相同
     * @return
     */
    public static String enCode(String content, String key) {
        if (key == null || "".equals(key)) {
            log.info("key为空！");
            return null;
        }
        if (key.length() != 16) {
            log.info("key长度不是16位！");
            return null;
        }
        try {
            byte[] raw = key.getBytes();  //获得密码的字节数组
            SecretKeySpec skey = new SecretKeySpec(raw, "AES"); //根据密码生成AES密钥
            Cipher cipher = Cipher.getInstance(ALGORITHM);  //根据指定算法ALGORITHM自成密码器
            cipher.init(Cipher.ENCRYPT_MODE, skey); //初始化密码器，第一个参数为加密(ENCRYPT_MODE)或者解密(DECRYPT_MODE)操作，第二个参数为生成的AES密钥
            byte[] contentBytes = content.getBytes("utf-8"); //获取加密内容的字节数组(设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
            byte[] encodeContent = cipher.doFinal(contentBytes); //密码器加密数据
            return Base64.encodeBase64String(encodeContent); //将加密后的数据转换为字符串返回
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * AES解密
     *
     * @param content 加密密文
     * @param key     加密密码,由字母或数字组成此方法使用AES-128-ECB加密模式，key需要为16位加密解密key必须相同
     * @return 解密明文
     **/
    public static String deCode(String content, String key) {
        if (key == null || "".equals(key)) {
            log.info("key为空！");
            return null;
        }
        if (key.length() != 16) {
            log.info("key长度不是16位！");
            return null;
        }
        try {
            //获得密码的字节数组
            byte[] raw = key.getBytes();
            //根据密码生成AES密钥
            SecretKeySpec skey = new SecretKeySpec(raw, "AES");
            // 根据指定算法ALGORITHM自成密码器
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            //初始化密码器，第一个参数为加密(ENCRYPT_MODE)或者解密(DECRYPT_MODE)操作，第二个参数为生成的AES密钥
            cipher.init(Cipher.DECRYPT_MODE, skey);
            //把密文字符串转回密文字节数组
            byte[] encode_content = Base64.decodeBase64(content);
            //密码器解密数据
            byte[] byteContent = cipher.doFinal(encode_content);
            //将解密后的数据转换为字符串返回
            return new String(byteContent, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*****************************************************
     * AES加密解密测试
     * @param args
     * @return
     ****************************************************/

    public static void main(String[] args) {
        String content = "sc160401";
        log.info("加密content：" + content);
        String key = AesConstant.WEIXIAO_KEY;
        log.info("加密key：" + key);
        String enResult = enCode(content, key);
        log.info("加密result：" + enResult);
        String deResult = deCode(enResult, key);
        log.info("解密result：" + deResult);
    }
}
