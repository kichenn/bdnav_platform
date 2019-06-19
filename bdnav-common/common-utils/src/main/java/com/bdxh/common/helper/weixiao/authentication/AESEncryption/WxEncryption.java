package com.bdxh.common.helper.weixiao.authentication.AESEncryption;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 微校数据加密以及ASCII排序
 * @author 
 *
 */
@Component
@Slf4j
public class WxEncryption {
	
	public static String Encrypt(String sSrc, String sKey, String sIv) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
		int blockSize = cipher.getBlockSize();

		byte[] dataBytes = sSrc.getBytes();
		int plaintextLength = dataBytes.length;
		if (plaintextLength % blockSize != 0) {
			plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
		}

		byte[] plaintext = new byte[plaintextLength];
		System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

		SecretKeySpec keyspec = new SecretKeySpec(sKey.getBytes(), "AES");
		IvParameterSpec ivspec = new IvParameterSpec(sIv.getBytes());

		cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
		byte[] encrypted = cipher.doFinal(plaintext);

		return byte2hex(encrypted).toLowerCase();
	}

	public static String Decrypt(String sSrc, String sKey, String sIv) throws Exception {

		byte[] encrypted1 = hex2byte(sSrc);

		Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
		SecretKeySpec keyspec = new SecretKeySpec(sKey.getBytes(), "AES");
		IvParameterSpec ivspec = new IvParameterSpec(sIv.getBytes());

		cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);

		byte[] original = cipher.doFinal(encrypted1);
		String originalString = new String(original);

		return originalString;
	}

	public static byte[] hex2byte(String strhex) {
		if (strhex == null) {
			return null;
		}
		int l = strhex.length();
		if (l % 2 == 1) {
			return null;
		}
		byte[] b = new byte[l / 2];
		for (int i = 0; i != l / 2; i++) {
			b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2), 16);
		}

		return b;
	}

	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}

		return hs.toUpperCase();
	}


	/**
	 * Map集合按照ASCII码从小到大（字典序）排序生成签名
	 * @param map
	 * @return
	 */
	public static String getSign(Map<String, Object> map,String secret) {
		String result = "";
		try {
			List<Map.Entry<String, Object>> infoIds = new ArrayList<Map.Entry<String, Object>>(map.entrySet());
			// 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
			Collections.sort(infoIds, new Comparator<Map.Entry<String, Object>>() {
				public int compare(Map.Entry<String, Object> o1, Map.Entry<String, Object> o2) {
					return (o1.getKey()).toString().compareTo(o2.getKey());
				}
			});
			// 构造签名键值对的格式
			StringBuilder sb = new StringBuilder();
			for (Map.Entry<String, Object> item : infoIds) {
				if (item.getKey() != null || item.getKey() != "") {
					String key = item.getKey();
					String val = item.getValue().toString();
					if (!(val == "" || val == null)) {
						sb.append(key + "=" + val + "&");
					}
				}

			}
			sb.append("key="+secret);
			log.info("数据签名之前：{}",sb.toString());
			result = sb.toString();
			//进行MD5加密
			result = DigestUtils.md5Hex(result).toUpperCase();
		} catch (Exception e) {
			return null;
		}
		return result;
	}
}
