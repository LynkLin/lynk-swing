package com.lynk.swing.test;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.lynk.swing.component.LynkCheckComboBox;

import net.miginfocom.swing.MigLayout;

public class TestCheckBoxComboBox extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private LynkCheckComboBox<String> checkComboBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestCheckBoxComboBox frame = new TestCheckBoxComboBox();
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
	public TestCheckBoxComboBox() {
		initComponents();
	}
	private void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow]", "[][][]"));
		{
			List<String> datas = new ArrayList<>();
			for(Integer i = 0; i < 10; i++) {
				datas.add(i.toString());
			}
			checkComboBox = new LynkCheckComboBox<>(datas, "@");
			contentPane.add(checkComboBox, "cell 0 0,growx");
		}
		{
			JButton button = new JButton("输出");
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println(checkComboBox.getValue());
				}
			});
			contentPane.add(button, "cell 0 1");
		}
	}

}
