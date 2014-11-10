package com.lynk.swing.component;

import javax.swing.JFormattedTextField;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import javax.swing.JButton;

import com.lynk.swing.common.Constants;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class LynkNumKeyboard extends JPanel implements Constants {
	private static final long serialVersionUID = 1L;

	private List<JFormattedTextField> uiSources;
	
	public LynkNumKeyboard() {
		initComponents();
	}
	
	public void addSource(JFormattedTextField source) {
		if(uiSources == null) {
			uiSources = new ArrayList<>();
		}
		this.uiSources.add(source);
	}
	
	public void setFontSize(float size) {
		setSubFont(this, APP_FONT_BLOD.deriveFont(size));
	}

	private void setSubFont(Component component, Font font) {
		if(component instanceof JPanel) {
			for(Component c: ((JPanel) component).getComponents()) {
				setSubFont(c, font);
			}
		} else if(component instanceof JButton) {
			component.setFont(font);
		}
	}

	private void initComponents() {
		setLayout(new MigLayout("", "[grow]", "[grow]5[grow]5[grow]"));
		{
			JPanel panel = new JPanel();
			add(panel, "cell 0 0 1 3,grow");
			panel.setLayout(new MigLayout("insets 1", "[grow][grow][grow]", "[grow][grow][grow][grow]"));
			{
				{
					JButton button = new JButton("1");
					button.setFocusable(false);
					button.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							buttonActionPerformed(e);
						}
					});
					panel.add(button, "cell 0 0,grow");
				}
				{
					JButton button = new JButton("2");
					button.setFocusable(false);
					button.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							buttonActionPerformed(e);
						}
					});
					panel.add(button, "cell 1 0,grow");
				}
				{
					JButton button = new JButton("3");
					button.setFocusable(false);
					button.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							buttonActionPerformed(e);
						}
					});
					panel.add(button, "cell 2 0,grow");
				}
				{
					JButton button = new JButton("4");
					button.setFocusable(false);
					button.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							buttonActionPerformed(e);
						}
					});
					panel.add(button, "cell 0 1,grow");
				}
				{
					JButton button = new JButton("5");
					button.setFocusable(false);
					button.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							buttonActionPerformed(e);
						}
					});
					panel.add(button, "cell 1 1,grow");
				}
				{
					JButton button = new JButton("6");
					button.setFocusable(false);
					button.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							buttonActionPerformed(e);
						}
					});
					panel.add(button, "cell 2 1,grow");
				}
				{
					JButton button = new JButton("7");
					button.setFocusable(false);
					button.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							buttonActionPerformed(e);
						}
					});
					panel.add(button, "cell 0 2,grow");
				}
				{
					JButton button = new JButton("8");
					button.setFocusable(false);
					button.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							buttonActionPerformed(e);
						}
					});
					panel.add(button, "cell 1 2,grow");
				}
				{
					JButton button = new JButton("9");
					button.setFocusable(false);
					button.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							buttonActionPerformed(e);
						}
					});
					panel.add(button, "cell 2 2,grow");
				}
				{
					JButton button = new JButton("0");
					button.setFocusable(false);
					button.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							buttonActionPerformed(e);
						}
					});
					panel.add(button, "cell 0 3,grow");
				}
				{
					JButton button = new JButton(".");
					button.setFocusable(false);
					button.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							buttonActionPerformed(e);
						}
					});
					panel.add(button, "cell 1 3,grow");
				}
				{
					JButton buttonDelete = new JButton("后退");
					buttonDelete.setFocusable(false);
					buttonDelete.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							buttonDeleteActionPerformed(e);
						}
					});
					buttonDelete.setForeground(Color.RED);
					panel.add(buttonDelete, "cell 2 3,grow");
				}
			}
		}
	}
	
	protected void buttonActionPerformed(ActionEvent evt) {
		for(JFormattedTextField uiSource: uiSources) {
			if(uiSource.isFocusOwner()) {
				String text = uiSource.getText().trim();
				JButton button = (JButton) evt.getSource();
				String key = button.getText();
				uiSource.setText(text + key);
				if(!key.equals(".")) {
					try {
						uiSource.commitEdit();
						uiSource.setValue(uiSource.getValue());
					} catch (ParseException e) {
						((JFormattedTextField) uiSource).setValue(0);
					}
				}
				break;
			}
		}
	}
	
	protected void buttonDeleteActionPerformed(ActionEvent evt) {
		for(JFormattedTextField uiSource: uiSources) {
			if(uiSource.isFocusOwner()) {
				String text = uiSource.getText().trim();
				if(text.length() != 0) {
					uiSource.setText(text.substring(0, text.length() - 1));
					try {
						uiSource.commitEdit();
						uiSource.setValue(uiSource.getValue());
					} catch (ParseException e) {
						((JFormattedTextField) uiSource).setValue(0);
					}
				}
				break;
			}
		}
	}
}
