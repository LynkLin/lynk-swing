package com.lynk.swing.component;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JTextField;

import com.lynk.swing.common.Constants;

public class LynkTextField extends JTextField implements Constants {
	private static final long serialVersionUID = 1L;

	public LynkTextField() {
		super();
		initStyle();
		setFocusListener();
	}

	public LynkTextField(String text) {
		super(text);
		initStyle();
		setFocusListener();
	}
	
	private void initStyle() {
		setFont(APP_FONT);
	}
	
	private void setFocusListener() {
		addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent e) {
				LynkTextField.this.selectAll();
			}
		});
	}

	@Override
	public String getText() {
		return super.getText().trim();
	}
}
