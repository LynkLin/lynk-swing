package com.lynk.swing.component;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.lynk.swing.common.Constants;

public class LynkFrame extends JFrame implements Constants {
	private static final long serialVersionUID = 1L;
	
	public static final Font WAIT_PANEL_FONT = new Font("微软雅黑", Font.PLAIN, 20);
	
	private InfiniteProgressPanel waitPanel;
	
	public LynkFrame() {
		waitPanel = new InfiniteProgressPanel();
		waitPanel.setFont(WAIT_PANEL_FONT);
		setGlassPane(waitPanel);
	}
	
	/**
	 * 显示等待界面
	 */
	public void showWaitPanel(final String msg) {
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				waitPanel.setText(msg == null? "":msg);
				waitPanel.start();
			}
		});
	}
	
	/**
	 * 设置文字
	 * @param msg
	 */
	public void setWaitText(final String msg) {
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				waitPanel.setText(msg);
			}
		});
	}
	
	/**
	 * 隐藏等待界面
	 */
	public void hideWaitPanel() {
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				waitPanel.stop();
			}
		});
	}

	public void showErrorMsg(String msg) {
		JOptionPane.showMessageDialog(this, msg, APP_NAME, JOptionPane.ERROR_MESSAGE);
	}
	
	public void showErrorMsg(Exception e) {
		JOptionPane.showMessageDialog(this, e.getMessage(), APP_NAME, JOptionPane.ERROR_MESSAGE);
	}
	
	public void showInfoMsg(String msg) {
		JOptionPane.showMessageDialog(this, msg, APP_NAME, JOptionPane.INFORMATION_MESSAGE);
	}
}
