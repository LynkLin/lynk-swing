package com.lynk.swing.component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

import com.lynk.swing.common.Constants;

public class LynkTimePicker extends JSpinner {
	private static final long serialVersionUID = 1L;

	public static final DateFormat df = new SimpleDateFormat("HH:mm");
	
	public LynkTimePicker() {
		super(new SpinnerDateModel());
		init();
	}
	
	public LynkTimePicker(String time) {
		super(new SpinnerDateModel());
		init();
		setTime(time);
	}
	
	private void init() {
		DateEditor editor = new DateEditor(this, "HH:mm");
		setEditor(editor);
		setFont(Constants.APP_FONT);
	}

	public String getTime() {
		if(getValue() instanceof Date) {
			return df.format((Date) getValue());
		}
		return null;
	}
	
	/**
	 * HH:mm
	 * @param time
	 */
	public void setTime(String time) {
		try {
			Date date = df.parse(time);
			setValue(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
