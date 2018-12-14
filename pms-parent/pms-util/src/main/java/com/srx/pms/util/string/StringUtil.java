package com.srx.pms.util.string;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public final class StringUtil {
	public static final boolean empty(final String str){
		return str == null || str.length() < 1;
	}
	public static final String changePrefixAndSuffix(final String str,int prefixLength,int suffixLength){
		if (empty(str)) {
			return "";
		}
		if(str.length() < prefixLength + suffixLength){
			return str;
		}
		String prefix = str.substring(0, prefixLength);
		String middle = str.substring(prefix.length(),str.length() - suffixLength);
		String suffix = str.substring(prefix.length()+middle.length(),str.length());
		return suffix + middle + prefix;
	}
	public static final int findPositiveInteger(final String str,int index){
		if (empty(str)) {
			return -1;
		}
		List<Integer> integers = new ArrayList<>();
		Pattern p = Pattern.compile("[0-9\\.]{1}");
		Matcher m = p.matcher(str);
		while(m.find()){
		   integers.add(Integer.parseInt(m.group()));
		   
		}
		if (integers.size() < 1) {
			return -1;
		}
		if (index < 0) {
			return integers.get(0);
		}else if(index >= integers.size()){
			return integers.get(integers.size() - 1);
		}
		
		return integers.get(index);
	}
}
