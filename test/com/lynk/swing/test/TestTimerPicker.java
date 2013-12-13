package com.lynk.swing.test;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.lynk.swing.component.LynkTimePicker;

import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

public class TestTimerPicker extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestTimerPicker frame = new TestTimerPicker();
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
	public TestTimerPicker() {
		initComponents();
		String time = "12:14";
		DateFormat df = new SimpleDateFormat("HH:mm");
		try {
			System.out.println(df.parse(time));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		{
			LynkTimePicker lynkTimePicker = new LynkTimePicker();
			contentPane.add(lynkTimePicker, BorderLayout.NORTH);
		}
		{
			JSpinner spinner = new JSpinner();
			spinner.setModel(new SpinnerDateModel());
			JSpinner.DateEditor ed = new JSpinner.DateEditor(spinner, "HH:ss");
			spinner.setEditor(ed);
			contentPane.add(spinner, BorderLayout.CENTER);
		}
	}

}
