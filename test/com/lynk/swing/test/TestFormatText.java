package com.lynk.swing.test;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DateFormatter;

import net.miginfocom.swing.MigLayout;

import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;

public class TestFormatText extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestFormatText frame = new TestFormatText();
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
	public TestFormatText() {
		initComponents();
	}
	private void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow][]", "[]"));
		{
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			DateFormatter formatter = new DateFormatter(format);  
			format.setLenient(false);  
			formatter.setAllowsInvalid(false);  
			formatter.setOverwriteMode(true);  
			JFormattedTextField formattedTextField = new JFormattedTextField(formatter);
			formattedTextField.setValue(new Date());
			formattedTextField.addKeyListener(new KeyAdapter() {

				@Override
				public void keyPressed(KeyEvent evt) {
					System.out.println(evt.getKeyChar());
				}
			});
			addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent evt) {
					if(evt.getPropertyName().equals("value")) {
						System.out.println("value");
					}
				}
			});
			contentPane.add(formattedTextField, "cell 0 0,growx");
		}
		{
			JButton btnNewButton = new JButton("New button");
			contentPane.add(btnNewButton, "cell 1 0");
		}
	}

}
