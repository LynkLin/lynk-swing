package com.lynk.swing.component.common;

import javax.swing.JPanel;
import javax.swing.JProgressBar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import net.miginfocom.swing.MigLayout;

import org.apache.commons.lang.StringUtils;

import com.jidesoft.swing.StyledLabel;
import com.lynk.swing.common.Constants;

public class WaitingPanel extends JPanel implements MouseListener, MouseMotionListener, FocusListener, Constants {
	private static final long serialVersionUID = 1L;

	private StyledLabel uiInfo;
	
	public WaitingPanel() {
		initComponents();
	}
	private void initComponents() {
		setOpaque(false);
		setLayout(new MigLayout("", "[grow][grow][grow]", "[grow][][30][grow]"));
		addMouseListener(this);
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
	@Override
	public void mouseClicked(MouseEvent e) {
	}
	@Override
	public void mousePressed(MouseEvent e) {
	}
	@Override
	public void mouseReleased(MouseEvent e) {
	}
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}
	@Override
	public void focusGained(FocusEvent e) {
	}
	@Override
	public void focusLost(FocusEvent e) {
	}
	@Override
	public void mouseDragged(MouseEvent e) {
	}
	@Override
	public void mouseMoved(MouseEvent e) {
	}
}
