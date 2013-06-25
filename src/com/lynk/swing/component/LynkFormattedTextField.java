package com.lynk.swing.component;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;

import javax.swing.JFormattedTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.text.DocumentFilter;
import javax.swing.text.InternationalFormatter;

import com.lynk.swing.common.Constants;
import com.lynk.swing.common.NumberFilter;

public class LynkFormattedTextField extends JFormattedTextField implements Constants {
	private static final long serialVersionUID = 1L;

	public LynkFormattedTextField() {
		super();
		setValue(0);
		initStyle();
		addSelectListener();
	}
	
	public LynkFormattedTextField(Object value) {
		super();
		setValue(value);
		initStyle();
		addSelectListener();
	}
	
	public LynkFormattedTextField(int min, int max) {
		super(initFormat(min, max));
		setValue(0);
		initStyle();
		addSelectListener();
	}
	
	public LynkFormattedTextField(int min, int max, Object value) {
		super(initFormat(min, max));
		setValue(value);
		initStyle();
		addSelectListener();
	}
	
	private void addSelectListener() {
		addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						LynkFormattedTextField.this.selectAll();
					}
				});
			}
		});
	}

	/**
	 * 值改变
	 * @param valueChangedListener
	 */
	public void addValueChangedListener(final ValueChangedListener valueChangedListener) {
		addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if(evt.getPropertyName().equals("value")) {
					valueChangedListener.valueChanged();
				}
			}
		});
	}
	
	private void initStyle() {
		setHorizontalAlignment(SwingConstants.RIGHT);
		setFont(APP_FONT);
	}
	
	private static InternationalFormatter initFormat(int min, int max) {
		NumberFormat format = NumberFormat.getNumberInstance();
		format.setMinimumFractionDigits(min);
		format.setMaximumFractionDigits(max);
		return new InternationalFormatter(format){
			private static final long serialVersionUID = -7024340913090600945L;

			@Override
			protected DocumentFilter getDocumentFilter() {
				return new NumberFilter();
			}
		};
	}
	
	public interface ValueChangedListener {
		void valueChanged();
	}
}
