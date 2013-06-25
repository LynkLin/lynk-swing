package com.lynk.swing.component;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import com.lynk.swing.common.Constants;

public class LynkDialog extends JDialog implements Constants {
	private static final long serialVersionUID = 1L;
	
	private InfiniteProgressPanel waitPanel;

	public LynkDialog() {
		setModalityType(ModalityType.APPLICATION_MODAL);
		waitPanel = new InfiniteProgressPanel();
		waitPanel.setFont(LynkFrame.WAIT_PANEL_FONT);
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
