package com.srx.pms.util.security;

import java.security.MessageDigest;

public class MD5Util {
	public static final String DEFAULT_CHARSET = "UTF-8";
	private static final char[] HEX_DIGITS = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
    public static final String encrypt(final String content) {
        try {  
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(content.getBytes(DEFAULT_CHARSET));
            byte[] md = mdInst.digest();
            char str[] = new char[md.length * 2];  
            int k = 0;  
            for (int i = 0; i < md.length; i++) {  
                byte byte0 = md[i];  
                str[k++] = HEX_DIGITS[byte0 >>> 4 & 0xf];  
                str[k++] = HEX_DIGITS[byte0 & 0xf];  
            }  
            return new String(str);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
    }
    public static final String MD5(final String content) {
    	return encrypt(encrypt(content));
    }

    public static void main(String[] args) {  
        System.out.println(MD5Util.MD5("admin_admin"));  
        System.out.println(MD5Util.MD5("加密"));  
        System.out.println(encrypt("admin"));
        System.out.println(encrypt("18584881929"));
        System.out.println(encrypt("admin_"+encrypt("admin")));
        System.out.println(MD5Util.MD5("admin_"+encrypt("admin")));
        System.out.println(MD5Util.MD5("srx0401_"+encrypt("srx0401"))); 
        System.out.println(MD5Util.MD5("wujuan_"+encrypt("wujuan"))); 
        System.out.println(MD5Util.MD5("srx_"+encrypt("srx"))); 
        System.out.println(MD5Util.MD5("test_"+encrypt("test"))); 
        System.out.println(MD5Util.MD5("may_"+encrypt("may"))); 
    }  
}
