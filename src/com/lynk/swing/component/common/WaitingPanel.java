package com.lynk.swing.component.common;

import javax.swing.JPanel;
import javax.swing.JProgressBar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import net.miginfocom.swing.MigLayout;

import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

import org.apache.commons.lang.StringUtils;

import com.jidesoft.swing.StyledLabel;
import com.lynk.swing.common.Constants;

public class WaitingPanel extends JPanel implements Constants {
	private static final long serialVersionUID = 1L;

	private StyledLabel uiInfo;
	
	public WaitingPanel() {
		initComponents();
	}
	private void initComponents() {
		setOpaque(false);
		setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		setLayout(new MigLayout("", "[grow][grow][grow]", "[grow][][30][grow]"));
		{
			uiInfo = new StyledLabel("请稍候...");
			uiInfo.setFont(APP_FONT.deriveFont(20f));
			add(uiInfo, "cell 1 1,alignx center");
		}
		{
			JProgressBar progressBar = new JProgressBar();
			progressBar.setStringPainted(false);
			progressBar.setIndeterminate(true);
			add(progressBar, "cell 1 2,grow");
		}
	}
	
	public void setText(String msg) {
		if(StringUtils.isEmpty(msg)) {
			msg = "请稍后...";
		}
		uiInfo.setText(msg);
	}
	
	public void start() {
		setVisible(true);
	}
	
	public void stop() {
		uiInfo.setText("请稍后...");
		setVisible(false);
	}

	@Override
	public void setVisible(boolean b) {
		super.setVisible(b);
	}
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(204, 204, 204, 200));
		g2.fillRect(0, 0, getWidth(), getHeight());
	}
}
