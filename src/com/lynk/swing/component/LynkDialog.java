package com.lynk.swing.component;

import java.awt.AWTKeyStroke;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.FocusManager;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import com.lynk.swing.common.Constants;
import com.lynk.swing.component.common.WaitingPanel;

public class LynkDialog extends JDialog implements Constants {
	private static final long serialVersionUID = 1L;
	
	private WaitingPanel waitPanel;

	public LynkDialog() {
		this(false);
	}
	
	public LynkDialog(boolean enterKeyForward) {
		setModalityType(ModalityType.APPLICATION_MODAL);
		waitPanel = new WaitingPanel(this);
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

	@Override
	public void setSize(Dimension d) {
		setSize(d.width, d.height);
	}

	@Override
	public void setSize(int width, int height) {
		super.setSize(width, height);
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
