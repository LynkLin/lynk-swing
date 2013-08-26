package com.lynk.swing.component;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class LynkStaticIntelliHints extends LynkIntelliHints {
	
	private List<?> items;
	
	public static void install(JTextField textField, List<?> items) {
		new LynkStaticIntelliHints(textField, items, -1);
	}
	
	public static void install(JTextField textField, List<?> items, int comboBoxNum) {
		new LynkStaticIntelliHints(textField, items, comboBoxNum);
	}
	
	private LynkStaticIntelliHints(JTextField textField, List<?> items, int comboBoxNum) {
		super(textField, comboBoxNum);
		this.items = items;
		textField.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if(SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {
					model.removeAllElements();
					for(Object item: LynkStaticIntelliHints.this.items) {
						model.addElement(item);
					}
					setPopupVisible(true);
				}
			}
		});
	}
	
	@Override
	protected void updateList() {
		model.removeAllElements();
		String input = getText();
		if (!input.isEmpty()) {
			for (Object item : items) {
				if (item.toString().toLowerCase().contains(input.toLowerCase())) {
					model.addElement(item);
				}
			}
		}
		setPopupVisible(model.getSize() > 0);
	}
}
