package com.lynk.swing.test;

import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;

import net.miginfocom.swing.MigLayout;

import javax.swing.JTextField;

import com.lynk.swing.component.LynkStaticIntelliHints;

public class TestIntelliHints extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestIntelliHints frame = new TestIntelliHints();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TestIntelliHints() {

		initComponents();
	}
	private void initComponents() {
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new MigLayout("", "[grow]", "[]"));
		{
			ArrayList<String> items = new ArrayList<String>();
			items.add("Agfgfg");
			items.add("Bfgdgd");
			items.add("Cfdhgdg");
			items.add("切断");
			textField = new JTextField();
			getContentPane().add(textField, "cell 0 0,growx");
			LynkStaticIntelliHints.install(textField, items);
			textField.setColumns(10);
		}
	}

}
