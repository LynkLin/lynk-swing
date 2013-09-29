package com.lynk.swing.component.common;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import java.awt.Component;

import net.miginfocom.swing.MigLayout;

import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

import com.jidesoft.swing.StyledLabel;
import com.lynk.swing.common.Constants;

public class WaitingDialog extends JDialog implements Constants {
	private static final long serialVersionUID = 1L;

	private Component parent;
	private StyledLabel uiInfo;
	private JProgressBar progressBar;
	
	public WaitingDialog(Component parent) {
		this.parent = parent;
		initComponents();
	}
	private void initComponents() {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setUndecorated(true);
		setSize(197, 67);
		{
			JPanel panel = new JPanel();
			panel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
			setContentPane(panel);
		}
		
		getContentPane().setLayout(new MigLayout("", "[grow]", "[][grow]"));
		{
			uiInfo = new StyledLabel("请稍候...");
			uiInfo.setFont(APP_FONT);
			getContentPane().add(uiInfo, "cell 0 0,alignx center");
		}
		{
			progressBar = new JProgressBar();
			progressBar.setStringPainted(false);
			progressBar.setIndeterminate(true);
			getContentPane().add(progressBar, "cell 0 1,grow");
		}
	}
	
	public void setText(String msg) {
		if(msg != null) {
			uiInfo.setText(msg);
		}
	}
	
	public void start() {
		setVisible(true);
		parent.setFocusable(false);
	}
	
	public void stop() {
		uiInfo.setText("请稍后");
		setVisible(false);
		parent.setFocusable(true);
		progressBar.setValue(0);
	}

	@Override
	public void setVisible(boolean b) {
		setLocationRelativeTo(parent);
		super.setVisible(b);
	}
}
