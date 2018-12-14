package com.srx.pms.util.security;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 对称加密之AES加密工具类
 * 
 * @author admin
 * 
 */
public class AESUtil {
	public static final String DEFULT_CHARSET = "UTF-8";

	private static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr == null || hexStr.length() < 1) {
			return null;
		}
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
					16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	private static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	public static String encrypt(String content, String salt) throws Exception {
		if (content == null || content.length() < 1 || salt == null || salt.length() < 1) {
			return null;
		}
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128, new SecureRandom(salt.getBytes()));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			byte[] byteContent = content.getBytes(DEFULT_CHARSET);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] result = cipher.doFinal(byteContent);
			return parseByte2HexStr(result);
		} catch (Exception e) {
			throw e;
		}
	}

	public static String decrypt(String content, String salt) throws Exception {
		if (content == null || content.length() < 1 || salt == null || salt.length() < 1) {
			return null;
		}
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128, new SecureRandom(salt.getBytes()));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] bytes = parseHexStr2Byte(content);
			if (bytes == null || bytes.length < 1) {
				return null;
			}
			return new String(cipher.doFinal(bytes), DEFULT_CHARSET);
		} catch (Exception e) {
			throw e;
		}
	}

	public static String en(String content, String salt) throws Exception {
		return encrypt(encrypt(content, salt), salt);
	}

	public static String de(String content, String salt) throws Exception {
		return decrypt(decrypt(content, salt), salt);
	}
}
