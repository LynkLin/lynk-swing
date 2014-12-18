package com.lynk.swing.component;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LynkInputDialog extends LynkDialog {
	private static final long serialVersionUID = 1L;
	
	private LynkTextField uiInput;
	private String value;
	
	public static String showDialog(Component parent, String title) {
		LynkInputDialog dialog = new LynkInputDialog(title);
		dialog.setLocationRelativeTo(parent);
		dialog.setVisible(true);
		return dialog.value;
	}

	public LynkInputDialog(String title) {
		initComponents(title);
	}
	private void initComponents(String title) {
		setSize(237, 112);
		if(title != null) {
			setTitle(title);
		} else {
			setTitle("输入");
		}
		setIconImage(Toolkit.getDefaultToolkit().getImage(LynkInputDialog.class.getResource("/resource/image/icon.png")));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton uiOk = new JButton("确认");
				uiOk.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						uiOkActionPerformed(e);
					}
				});
				uiOk.setFont(APP_FONT);
				uiOk.setFocusable(false);
				buttonPane.add(uiOk);
				getRootPane().setDefaultButton(uiOk);
			}
			{
				JButton uiCancel = new JButton("取消");
				uiCancel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						uiCancelActionPerformed(e);
					}
				});
				uiCancel.setFont(APP_FONT);
				uiCancel.setFocusable(false);
				buttonPane.add(uiCancel);
			}
		}
		{
			JPanel panel = new JPanel();
			getContentPane().add(panel, BorderLayout.CENTER);
			panel.setLayout(new MigLayout("", "[grow]", "[grow]"));
			{
				uiInput = new LynkTextField();
				panel.add(uiInput, "cell 0 0, grow");
			}
		}
	}

	protected void uiOkActionPerformed(ActionEvent e) {
		if(uiInput.getText().length() == 0) {
			value = null;
		} else {
			value = uiInput.getText();
		}
		dispose();
	}
	
	protected void uiCancelActionPerformed(ActionEvent e) {
		value = null;
		dispose();
	}
}
