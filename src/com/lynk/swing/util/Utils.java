package com.lynk.swing.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
	private static DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
	public static String getNowStr() {
		return format.format(new Date());
	}
	
	/**
	 * 格式化秒数,最多24H
	 * @param second
	 * @return
	 */
	public static String formatSceond(long second) {
		long time = second / 1000;
		if(time < 86400 && time >= 3600 ) {
			long h = time / 3600;
			long m = (time - (h * 3600)) / 60;
			long s = time - h * 3600 - m * 60;
			return h + "时" + m + "分" + s + "秒";
		}
		if(time < 3600 && time >= 60) {
			long m = time / 60;
			long s = time - m * 60;
			return m + "分" + s + "秒";
		}
		return (double) second / 1000 + "秒";
	}
}
