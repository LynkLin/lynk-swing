package com.lynk.swing.component;

import java.awt.AWTKeyStroke;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.FocusManager;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import com.lynk.swing.common.Constants;
import com.lynk.swing.component.common.WaitingPanel;

public class LynkInternalFrame extends JInternalFrame implements Constants {
	private static final long serialVersionUID = 1L;
	
	public static final Font WAIT_PANEL_FONT = new Font("微软雅黑", Font.PLAIN, 20);
	
	private WaitingPanel waitPanel;
	
	public LynkInternalFrame() {
		this(false);
	}
	
	public LynkInternalFrame(boolean enterKeyForward) {
		waitPanel = new WaitingPanel();
		setGlassPane(waitPanel);
		if(enterKeyForward) {
			Set<AWTKeyStroke> forwordDefaultKeys = getFocusTraversalKeys(FocusManager.FORWARD_TRAVERSAL_KEYS);
			Set<AWTKeyStroke> forwordNewKeys = new HashSet<AWTKeyStroke>(forwordDefaultKeys);
			forwordNewKeys.add(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0));
			setFocusTraversalKeys(FocusManager.FORWARD_TRAVERSAL_KEYS, forwordNewKeys);
		}
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
	
	public int showYesNoComfirmMsg(String msg) {
		return JOptionPane.showConfirmDialog(this, msg, APP_NAME, JOptionPane.YES_NO_OPTION);
	}
	
	public int showOkCancelComfirmMsg(String msg) {
		return JOptionPane.showConfirmDialog(this, msg, APP_NAME, JOptionPane.OK_CANCEL_OPTION);
	}
}