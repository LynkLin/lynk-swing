package com.lynk.swing.util;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import net.sf.jasperreports.view.JRSaveContributor;
import net.sf.jasperreports.view.save.JRDocxSaveContributor;
import net.sf.jasperreports.view.save.JRMultipleSheetsXlsSaveContributor;
import net.sf.jasperreports.view.save.JRPdfSaveContributor;
import net.sf.jasperreports.view.save.JRSingleSheetXlsSaveContributor;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class LynkSwingUtil {
	/**
	 * 获取静态配置参数
	 * @param propName
	 * @return
	 */
	public static String getStaticProp(String propName) {
		Properties prop = new Properties();
		InputStream is = null;
		try {
			is = LynkSwingUtil.class.getClassLoader().getResourceAsStream("resource/config/config.properties");
			prop.load(is);
			return prop.getProperty(propName, "");
		} catch (Exception e) {
			return "";
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
			}
		}
	}
	
	/**
	 * 获取静态配置参数
	 * @param propName
	 * @return
	 */
	public static String getStaticProp(String propName, String defaultValue) {
		Properties prop = new Properties();
		InputStream is = null;
		try {
			is = LynkSwingUtil.class.getClassLoader().getResourceAsStream("resource/config/config.properties");
			prop.load(is);
			return prop.getProperty(propName, defaultValue);
		} catch (Exception e) {
			return defaultValue;
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
			}
		}
	}
	
	/**
	 * 生产ToolTip
	 * @param name
	 * @param values
	 * @return
	 */
	public static String createStyledToolTip(String name, Object... values) {
		StringBuilder sb = new StringBuilder();
		sb.append("<html><table>");
		String[] names = name.split(",");
		for (int i = 0; i < names.length; i++) {
			sb.append("<tr>");
			sb.append("<td>");
			sb.append("<b>").append(names[i]).append(": </b>");
			sb.append("</td><td>");
			sb.append(values[i].toString());
			sb.append("</td>");
			sb.append("</tr>");
		}
		sb.append("</table></html>");
		return sb.toString();
	}
	
	/**
	 * 生产ToolTip
	 * @param names
	 * @param values
	 * @return
	 */
	public static String createStyledToolTip(String[] names, Object... values) {
		StringBuilder sb = new StringBuilder();
		sb.append("<html><table>");
		for (int i = 0; i < names.length; i++) {
			sb.append("<tr>");
			sb.append("<td>");
			sb.append("<b>").append(names[i]).append(": </b>");
			sb.append("</td><td>");
			sb.append(values[i].toString());
			sb.append("</td>");
			sb.append("</tr>");
		}
		sb.append("</table></html>");
		return sb.toString();
	}
	
	/**
	 * 生产ToolTip, 第一行加粗显示, 显示表格线
	 * @param names
	 * @param values
	 * @return
	 */
	public static String createStyledToolTip(String[][] data) {
		StringBuilder sb = new StringBuilder();
		sb.append("<html><table border='1' cellpadding='3' cellspacing='0'>");
		for (int r = 0; r < data.length; r++) {
			sb.append("<tr>");
			if(r == 0) {
				for(int c = 0; c < data[r].length; c++) {
					sb.append("<td align='center'><b>").append(data[r][c]).append("</b></td>");
				}
			} else {
				for(int c = 0; c < data[r].length; c++) {
					sb.append("<td>").append(data[r][c]).append("</td>");
				}
			}
			sb.append("</tr>");
		}
		sb.append("</table></html>");
		return sb.toString();
	}
	
	/**
	 * 报表保存的格式
	 * @return
	 */
	public static List<JRSaveContributor> createReportSaveFilters() {
		List<JRSaveContributor> saveContributors = new ArrayList<JRSaveContributor>();
		
		JRSingleSheetXlsSaveContributor filter1 = new JRSingleSheetXlsSaveContributor(null, null) {

			@Override
			public String getDescription() {
				return "导出为一个Sheet(.xls)";
			}
			
		};
		JRMultipleSheetsXlsSaveContributor filter2 = new JRMultipleSheetsXlsSaveContributor(null, null) {

			@Override
			public String getDescription() {
				return "导出为多个Sheet(.xls)";
			}
			
		};
		JRDocxSaveContributor filter3 = new JRDocxSaveContributor(null, null) {

			@Override
			public String getDescription() {
				return "导出为Word(.docx)";
			}
			
		};
		JRPdfSaveContributor filter4 = new JRPdfSaveContributor(null, null) {

			@Override
			public String getDescription() {
				return "导出为PDF(.pdf)";
			}
			
		};
		saveContributors.add(filter1);
		saveContributors.add(filter2);
		saveContributors.add(filter4);
		saveContributors.add(filter3);
		return saveContributors;
	}
	
	/**
	 * 获取颜色的16位表示
	 * @param color
	 * @return
	 */
	public static String getRgbHex(Color color) {
		String hexString = Integer.toHexString(color.getRGB());
		return "#" + hexString.substring(2);
	}
	
	/**
	 * 防止精度丢失
	 * @return
	 */
	public static JsonConfig createBigDecimalConfig() {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(BigDecimal.class, new JsonValueProcessor() {
			
			@Override
			public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
				if(value == null) {
					return "0";
				}
				if(value instanceof BigDecimal) {
					return ((BigDecimal) value).toString();
				}
				return value;
			}
			
			@Override
			public Object processArrayValue(Object value, JsonConfig jsonConfig) {
				return null;
			}
		});
		return jsonConfig;
	}
}
