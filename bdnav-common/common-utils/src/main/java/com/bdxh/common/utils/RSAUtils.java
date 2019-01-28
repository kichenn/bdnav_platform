package com.bdxh.common.utils;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @description: RSA加密类 秘钥长度1024
 * @author: xuyuan
 * @create: 2019-01-28 12:09
 **/
public class RSAUtils {

    /**
     * 私钥
     */
    public static final String privateKey="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJuTvFLNOgpaPfEN 7fxsJbLqtE8Tk4DRVgj7HkCSowpNEv1XZIdAMXMtmDgkPs4qmMIMYvHGb1u0nWsO dVxD/sIQIfVghzEbYrea3lNPKlrd0pk5PwmH1RdRF33d3zNEXV630wTNlVTUdXsH Qoy8TDHvGb7qVb3ZLzIGKrBqQ+mhAgMBAAECgYAp9VcmEblkj2ADQhl8KYKlVU1Z wlLWvB5y/57yFVopbg4AC3DOVU7A3HwgMA5MNgrK0uJgiH8CAZ0vAVA/mQr8yg1M h6gItwNQdA6kE7LZ2g0x+gwWD9VHjNWqNrJ8H+7wUNzEanJxmK3bbWWbXDURGmfs 8j/lX93NoMHOlf06EQJBAMgdVZhHiIrTOZL3bpU0/hNiQhFefJ69/zwXZEaqj5H9 n5Wqps1QCJXHkAfbCcmP2jynfCrh4wRNQ8rPTrvX6zUCQQDHBk9xDGUoHi3mxrkg HP8Yz0Q1IPyrDSrLGex7qXQNwD+FJx4YgQcy+E4MWjCjyw21EUXFvaPxELhqq0TV KyY9AkB8ggPno1pJRfLkhh6/pyEOXmxM8kUHX5+We9dgQG7jnrHUJUFzLQCqcGgk +xuz1VUrt+mBpNniq1Aqt6F9LnKhAkEAhFhR417WtYK8C0YCPLSLfbf2J040Npqe 5kfq1aCtJ6e7lZOZk7nZS6+KVvRye7i++LTvrLqalecEewpAz2/OEQJAA+q+4PG1 gvhWa69yBROsAMLk3wDKTLL+hl5Omieb1hkooT1FOZOc56I7Tw9CBnUgSpImXwpe gm8ZqynNHHBPXA==";

    /**
     * 公钥
     */
    public static final String publicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCbk7xSzToKWj3xDe38bCWy6rRP E5OA0VYI+x5AkqMKTRL9V2SHQDFzLZg4JD7OKpjCDGLxxm9btJ1rDnVcQ/7CECH1 YIcxG2K3mt5TTypa3dKZOT8Jh9UXURd93d8zRF1et9MEzZVU1HV7B0KMvEwx7xm+ 6lW92S8yBiqwakPpoQIDAQAB";

    /**
     * 加密最大长度 117
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * 解密最大长度 128
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    /**
     * 获取私钥
     * @param key 私钥
     * @return
     */
    public static PrivateKey getPrivateKey(String key) {
        try {
            //实例化密钥工厂
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            //密钥材料转换
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec=new PKCS8EncodedKeySpec(Base64Utils.decodeByte(key));
            //产生私钥
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            return privateKey;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取公钥
     * @param key 公钥
     * @return
     */
    public static PublicKey getPublicKey(String key) {
        try {
            //实例化密钥工厂
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            //密钥材料转换
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64Utils.decodeByte(key));
            //产生公钥
            PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
            return pubKey;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 公钥加密
     * @param data 待加密数据
     * @param key 公钥
     * @return
     */
    public static String encrypt(byte[] data, String key) {
        try {
            //获取公钥
            PublicKey publicKey = getPublicKey(key);
            //数据加密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            int inputLen = data.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            byte[] bytes = out.toByteArray();
            out.close();
            return Base64Utils.encode(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 公钥加密
     * @param value 待加密字符串
     * @param key 公钥
     * @param charset 字符集
     * @return
     */
    public static String encrypt(String value, String key, String charset) {
        try {
            //获取公钥
            PublicKey publicKey = getPublicKey(key);
            //数据加密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] data = value.getBytes(Charset.forName(charset));
            int inputLen = data.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            byte[] bytes = out.toByteArray();
            out.close();
            return Base64Utils.encode(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 公钥加密
     * @param value 待加密字符串 默认字符集utf-8
     * @param key 公钥
     * @return
     */
    public static String encrypt(String value, String key) {
        try {
            //获取公钥
            PublicKey publicKey = getPublicKey(key);
            //数据加密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] data = value.getBytes(Charset.forName("utf-8"));
            int inputLen = data.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            byte[] bytes = out.toByteArray();
            out.close();
            return Base64Utils.encode(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 私钥解密
     * @param value 待解密字符串
     * @param key 私钥
     * @param charset 字符集
     * @return
     */
    public static String decrypt(String value, String key, String charset) {
        try {
            PrivateKey privateKey = getPrivateKey(key);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] data = Base64Utils.decodeByte(value);
            int inputLen = data.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_DECRYPT_BLOCK;
            }
            byte[] bytes = out.toByteArray();
            out.close();
            return new String(bytes, charset);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 私钥解密
     * @param value 待解密字符串 默认字符集utf-8
     * @param key 私钥
     * @return
     */
    public static String decrypt(String value, String key) {
        try {
            PrivateKey privateKey = getPrivateKey(key);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] data = Base64Utils.decodeByte(value);
            int inputLen = data.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_DECRYPT_BLOCK;
            }
            byte[] bytes = out.toByteArray();
            out.close();
            return new String(bytes,"utf-8");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
