package com.lynk.swing.component;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.lynk.swing.common.Constants;

public abstract class LynkIntelliHints implements Constants{
	protected DefaultComboBoxModel<Object> model;
	private JComboBox<Object> comboBox;
	private JTextField textField;

	public static final void install(JTextField textField, final DoUpdate doUpdate) {
		new LynkIntelliHints(textField, -1) {
			
			@Override
			protected void updateList() {
				doUpdate.updateList();
			}
		};
	}
	
	public LynkIntelliHints(JTextField textField, int comboBoxNum) {
		this.textField = textField;
		initUi(comboBoxNum);
		initListeners();
	}
	
	private void initUi(int comboBoxNum) {
		model = new DefaultComboBoxModel<>();
		comboBox = new JComboBox<Object>(model) {
			private static final long serialVersionUID = 1L;

			@Override
			public Dimension getPreferredSize() {
				return new Dimension(super.getPreferredSize().width, 0);
			}
		};
		comboBox.setFocusable(false);
		comboBox.setMaximumRowCount(comboBoxNum > 0? comboBoxNum: 8);
		comboBox.setFont(APP_FONT);
		textField.setLayout(new BorderLayout());
		textField.add(comboBox, BorderLayout.SOUTH);
	}
	
	private void initListeners() {
		textField.getDocument().addDocumentListener(new DocumentListener() {
			public void insertUpdate(DocumentEvent e) {
				changed();
			}

			public void removeUpdate(DocumentEvent e) {
				changed();
			}

			public void changedUpdate(DocumentEvent e) {
				changed();
			}
			
			public void changed() {
				setAdjusting(true);
				updateList();
				setAdjusting(false);
			}
		});
		
		comboBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
				if(!isAdjusting() && comboBox.getSelectedItem() != null) {
					textField.setText(comboBox.getSelectedItem().toString());
				}
			}
		});
		
		textField.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent evt) {
				setAdjusting(true);
				if(evt.getKeyCode() == KeyEvent.VK_SPACE && comboBox.isPopupVisible()) {
					evt.setKeyCode(KeyEvent.VK_ENTER);
				}
				if (evt.getKeyCode() == KeyEvent.VK_ENTER
						|| evt.getKeyCode() == KeyEvent.VK_UP
						|| evt.getKeyCode() == KeyEvent.VK_DOWN) {
					evt.setSource(comboBox);
					comboBox.dispatchEvent(evt);
					if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
						textField.setText(getComboText());
						setPopupVisible(false);
					}
				}
				if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
					setPopupVisible(false);
				}
				setAdjusting(false);
			}
		});
	}
	
	protected String getText() {
		return textField.getText().trim();
	}
	
	protected String getComboText() {
		Object obj = comboBox.getSelectedItem();
		return obj == null? "": obj.toString();
	}
	
	
	protected abstract void updateList();
	
	protected void setPopupVisible(boolean visible) {
		comboBox.setPopupVisible(visible);
	}
	
	protected boolean isAdjusting() {
		if(comboBox.getClientProperty("is_adjusting") == null) {
			return false;
		} else {
			return (boolean) comboBox.getClientProperty("is_adjusting");
		}
	}
	
	protected void setAdjusting(boolean adjusting) {
		comboBox.putClientProperty("is_adjusting", adjusting);
	}
	
	public interface DoUpdate {
		void updateList();
	}
}
