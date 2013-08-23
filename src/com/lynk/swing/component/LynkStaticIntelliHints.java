package com.lynk.swing.component;

import java.util.List;

import javax.swing.JTextField;

public class LynkStaticIntelliHints extends LynkIntelliHints {
	
	private List<?> items;
	
	public static void install(JTextField textField, List<?> items) {
		new LynkStaticIntelliHints(textField, items);
	}
	
	private LynkStaticIntelliHints(JTextField textField, List<?> items) {
		super(textField);
		this.items = items;
	}
	
	@Override
	protected void updateList() {
		model.removeAllElements();
		String input = getText();
		if (!input.isEmpty()) {
			for (Object item : items) {
				if (item.toString().toLowerCase().startsWith(input.toLowerCase())) {
					model.addElement(item);
				}
			}
		}
		setPopupVisible(model.getSize() > 0);
	}
}
