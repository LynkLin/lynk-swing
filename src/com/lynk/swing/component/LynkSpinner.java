package com.lynk.swing.component;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.lynk.swing.common.Constants;

public class LynkSpinner extends JSpinner implements Constants {
	private static final long serialVersionUID = 1L;

	public LynkSpinner() {
		super();
		initStyle();
		setFocus();
	}

	public LynkSpinner(SpinnerModel model) {
		super(model);
		initStyle();
		setFocus();
	}
	
	/**
	 * 值改变事件
	 * @param valueChangedListener
	 */
	public void addValueChangedListener(final SpinnerValueChangedListener valueChangedListener) {
		addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent evt) {
				valueChangedListener.valueChanged();
			}
		});
	}
	
	private void setFocus() {
		JComponent c = LynkSpinner.this.getEditor();
		if(c instanceof DefaultEditor) {
			final JFormattedTextField jftf = ((DefaultEditor) c).getTextField();
			jftf.addFocusListener(new FocusAdapter() {

				@Override
				public void focusGained(FocusEvent e) {
					SwingUtilities.invokeLater(new Runnable() {
						
						@Override
						public void run() {
							jftf.selectAll();
						}
					});
				}
			});
		}
	}
	
	private void initStyle() {
		setFont(APP_FONT);
	}
	
	public interface SpinnerValueChangedListener {
		void valueChanged();
	}
}
