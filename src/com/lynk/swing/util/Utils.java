package com.lynk.swing.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.lynk.swing.common.Constants;

public class Utils implements Constants {
	private static DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
	
	/**
	 * 现在时间
	 * @return yyyyMMddHHmmss
	 */
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
	
	/**
	 * 可用状态转换为数据库表示
	 * @param enableCn
	 * @return
	 */
	public static String convertEnableToTiny(String enableCn) {
		switch (enableCn) {
		case ENABLE_CN:
			return ENABLE;
		case DISABLE_CN:
			return DISABLE;
		case ENABLE_AND_DISABLE_CN:
			return ENABLE_AND_DISABLE;
		default:
			return null;
		}
	}
	/**
	 * 可用状态转换为中文表示
	 * @param enableEn
	 * @return
	 */
	public static String convertEnableToCn(String enableEn) {
		switch (enableEn) {
		case ENABLE:
			return ENABLE_CN;
		case DISABLE:
			return DISABLE_CN;
		case ENABLE_AND_DISABLE:
			return ENABLE_AND_DISABLE_CN;
		default:
			return null;
		}
	}
}