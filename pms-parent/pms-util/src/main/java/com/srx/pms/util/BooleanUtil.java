package com.srx.pms.util;

public class BooleanUtil {
	public static boolean parseBoolean(String flag){
		return flag != null && (flag.trim().equalsIgnoreCase("Y") || flag.trim().equalsIgnoreCase("1"));
	}
}
