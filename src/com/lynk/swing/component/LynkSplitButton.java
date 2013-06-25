package com.lynk.swing.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;

import com.jidesoft.swing.ButtonStyle;
import com.jidesoft.swing.JideSplitButton;

public class LynkSplitButton extends JideSplitButton {
	private static final long serialVersionUID = 1L;

	public LynkSplitButton() {
		super();
		init();
	}

	public LynkSplitButton(String title) {
		super(title);
		init();
	}
	
	public LynkSplitButton(Icon icon) {
		super(icon);
		init();
	}

	public LynkSplitButton(String title, Icon icon) {
		super(title, icon);
		init();
	}

	private void init() {
		setButtonStyle(ButtonStyle.FLAT_STYLE);
		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LynkSplitButton.this.doClickOnMenu();
			}
		});
	}
}
