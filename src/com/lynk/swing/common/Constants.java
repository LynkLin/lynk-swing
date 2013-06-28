package com.lynk.swing.common;

import java.awt.Font;

import com.lynk.swing.util.LynkSwingUtil;

public interface Constants {
	String APP_NAME = LynkSwingUtil.getStaticProp("APP_NAME");
	
	Font APP_FONT = new Font("微软雅黑", Font.PLAIN, Integer.parseInt(LynkSwingUtil.getStaticProp("APP_FONT_SIZE", "14")));
	Font APP_FONT_BLOD = new Font("微软雅黑", Font.BOLD, Integer.parseInt(LynkSwingUtil.getStaticProp("APP_FONT_SIZE", "14")));
	
	String ENABLE = "0";
	String DISABLE = "1";
	
	String ENABLE_CN = "可用";
	String DISABLE_CN = "已禁用";
}
