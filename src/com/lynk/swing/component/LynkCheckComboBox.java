package com.lynk.swing.component;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.lynk.swing.common.Constants;
import com.lynk.swing.component.common.checkcombobox.CheckComboBoxPopup;

import net.miginfocom.swing.MigLayout;

import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

public class LynkCheckComboBox<E> extends JPanel implements Constants {
	private static final long serialVersionUID = 1L;

	private LynkTextField textField;
	private CheckComboBoxPopup<E> popup;
	
	public LynkCheckComboBox(List<E> datas, String seperate) {
		initComponents(datas, seperate);
	}

	private void initComponents(List<E> datas, String seperate) {
		setLayout(new MigLayout("insets 0", "[grow]0[]", "[grow]"));
		{
			textField = new LynkTextField();
			add(textField, "cell 0 0,grow");
		}
		{
			popup = new CheckComboBoxPopup<>(datas, textField, seperate);
		}
		{
			JButton uiCombo = new JButton(new ImageIcon(getClass().getResource("/resources/images/combo.png")));
            uiCombo.setFocusable(false);
			uiCombo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					uiComboActionPerformed(e);
				}
			});
			uiCombo.setMargin(new Insets(0, 0, 0, 0));
			add(uiCombo, "cell 1 0,grow");
		}
	}

	protected void uiComboActionPerformed(ActionEvent evt) {
		if(!popup.isVisible()) {
			popup.show(textField, 0, textField.getHeight());
		} else {
			popup.setVisible(false);
		}
	}
	
	public List<E> getValues() {
		return popup.getValues();
	}
	
	public String getValue() {
		return textField.getText();
	}

    @Override
    public void transferFocus() {
        textField.transferFocus();
    }
}
