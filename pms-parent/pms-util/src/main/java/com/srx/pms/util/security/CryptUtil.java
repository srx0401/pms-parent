package com.srx.pms.util.security;

import com.srx.pms.util.string.StringUtil;

public class CryptUtil {

    private static String changePrefixAndSuffix(String str,int prefixLen,int suffixLen){
    	if (StringUtil.empty(str)) {
			return null;
		}
    	while(str.length() <= prefixLen + suffixLen){
    		prefixLen = prefixLen / 2;
    		suffixLen = suffixLen / 2;
    	}
    	return str.length() <= prefixLen + suffixLen ? null : StringUtil.changePrefixAndSuffix(str, prefixLen,suffixLen);
    }

	public static String decryptByAES(String content, String salt) throws Exception{
		if (StringUtil.empty(content) || StringUtil.empty(salt)) {
			return content;
		}
		String temp = MD5Util.MD5(salt);
		return AESUtil.de(changePrefixAndSuffix(content, StringUtil.findPositiveInteger(temp, temp.length()), StringUtil.findPositiveInteger(temp, 0)), temp);
	}
	public static String encryptByAES(String content, String salt) throws Exception{
		if (StringUtil.empty(content) || StringUtil.empty(salt)) {
			return content;
		}
		String temp = MD5Util.MD5(salt);
		return changePrefixAndSuffix(AESUtil.en(content, temp), StringUtil.findPositiveInteger(temp, 0), StringUtil.findPositiveInteger(temp, temp.length()));
	}
	public static String encryptByMD5(String content, String salt) throws Exception{
		if (StringUtil.empty(content) || StringUtil.empty(salt)) {
			return content;
		}
		String temp = MD5Util.MD5(salt);
		return changePrefixAndSuffix(temp, StringUtil.findPositiveInteger(temp, temp.length()), StringUtil.findPositiveInteger(temp, 0));
	}
	public static String encryptByMD5(String content) throws Exception{
		return encryptByMD5(content,content);
	}
	public static void main(String[] args) {
		try {
			System.out.println("ENCRYPT BY MD5:"+encryptByMD5("18588481929"));
			init();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void init()throws Exception{
		String up = "srx77";
		String pp = "0401Lss12";
		String salt = "18584881929";
		for (int i = 1; i <= 25; i++) {
			String u = up + (i < 10 ? "0"+i : i);
			String p = pp + (i < 10 ? "0"+i : i);
			System.out.println(u + "," + p);
			String md5eu = encryptByMD5(u, salt);
			String md5ep = encryptByMD5(p, salt);
			System.out.println("ENCRYPT BY MD5:"+md5eu + "," + md5ep);
			String eu = encryptByAES(u, salt);
			String ep = encryptByAES(p, salt);
			System.out.println("ENCRYPT BY AES:"+eu + "," + ep);
			String du = decryptByAES(eu, salt);
			String dp = decryptByAES(ep, salt);
			System.out.println("DECRYPT BY AES:"+du + "," + dp);
		}
	}
}
