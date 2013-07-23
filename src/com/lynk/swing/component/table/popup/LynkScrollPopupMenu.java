package com.lynk.swing.component.table.popup;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.lynk.swing.common.Constants;

public class LynkScrollPopupMenu extends ResizablePopupMenu implements Constants {
	private static final long serialVersionUID = 1L;

	private JPanel rootPanel;
	private JPanel panel;
	
	public LynkScrollPopupMenu() {
		super();
		setFocusable(true);
		
		rootPanel = new JPanel();
		rootPanel.setLayout(new BorderLayout(0, 5));
		rootPanel.setBorder( BorderFactory.createEmptyBorder(4, 4, 4, 4));
		
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(panel);
		rootPanel.add(scrollPane, BorderLayout.CENTER);
		
		JButton uiCancel = new JButton("关闭", new ImageIcon(getClass().getResource("/resources/images/disable.png")));
		uiCancel.setMargin(new Insets(5, 0, 5, 0));
		uiCancel.setFocusable(false);
		uiCancel.setMargin(new Insets(0, 0, 0, 0));
		uiCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
				setPopupVistible(false, true);
			}
		});
		uiCancel.setFont(APP_FONT);
		rootPanel.add(uiCancel, BorderLayout.NORTH);
		
		add(rootPanel);
	}
	
	@Override
	public JMenuItem add(JMenuItem menuItem) {
		panel.add(menuItem);
		return menuItem;
	}

	@Override
	public void removeAll() {
		panel.removeAll();
	}

	@Override
	public void addSeparator() {
		panel.add(new Separator());
	}

	public void setPopupVistible(boolean b, boolean enable) {
		if(b) {
			super.setVisible(true);
		} else {
			if(enable) {
				super.setVisible(false);
			}
		}
	}
	
	@Override
	public void setVisible(boolean b) {
		if(b) {
			super.setVisible(b);
		}
	}

	@Override
	public void show(Component invoker, int x, int y) {
		int height = Toolkit.getDefaultToolkit().getScreenSize().height - invoker.getLocationOnScreen().y - 80;
		int acHeight = rootPanel.getPreferredSize().height + 10;
		
		setPopupSize(250, height < acHeight? height: acHeight);
		
		super.show(invoker, x, y);
	}

	@Override
	public void menuSelectionChanged(boolean isIncluded) {
		super.menuSelectionChanged(isIncluded);
	}
	
}