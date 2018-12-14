package com.srx.pms.util.date;

import java.text.SimpleDateFormat;

public class DateUtil {
	public static final String PATTERN_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
	public static final SimpleDateFormat DATE_TIME_FORMATER = new SimpleDateFormat(PATTERN_DATE_TIME);
	public static final String PATTERN_DATE = "yyyy-MM-dd";
	public static final SimpleDateFormat DATE_FORMATER = new SimpleDateFormat(PATTERN_DATE);
}
