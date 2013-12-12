package com.lynk.swing.component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

public class LynkTimePicker extends JSpinner {
	private static final long serialVersionUID = 1L;

	public static final DateFormat df = new SimpleDateFormat("HH:mm");
	
	public LynkTimePicker() {
		super(new SpinnerDateModel());
		init();
	}
	
	private void init() {
		DateEditor editor = new DateEditor(this, "HH:mm");
		setEditor(editor);
	}

	public String getTimer() {
		if(super.getValue() instanceof Date) {
			return df.format((Date) super.getValue());
		}
		return null;
	}
}
