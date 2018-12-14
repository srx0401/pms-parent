package com.srx.pms.util.security;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
/**
 * 	UUID工具类
 * @author admin
 *
 */
public class UUIDUtil {
	/**
	 * 	获取一个UUID
	 * @return
	 */
	public static String getUUID(){
		return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
	}
	/**
	 * 	获取指定数量的UUID
	 * @param number	数量.小于1时返回null
	 * @return
	 */
	public static List<String> getUUID(long number){
		if (number < 1) {
			return null;
		}
		List<String> res = new ArrayList<>();
		do{
			res.add(getUUID());
		}while(res.size() < number);
		return res;
	}
	public static void main(String[] args) {
		List<String> ids = getUUID(80);
		for(String id : ids){
			System.out.println(id);
		}
	}
}
