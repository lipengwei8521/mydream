package com.mydream.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	/**
	 * 获取当前时间，格式（yyyy-MM-dd HH:mm:ss）
	 * @return
	 */
	public static String getTimeNow() {
		// 设置日期格式
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(new Date());
	}
	
	/**
	 * 获取当前时间，自定义事件格式
	 * @param formatString 时间格式字符串例如（yyyy-MM-dd HH:mm:ss）
	 * @return
	 */
	public static String getTimeNow(String formatString) {
		// 设置日期格式
		SimpleDateFormat df = new SimpleDateFormat(formatString);
		return df.format(new Date());
	}

}
