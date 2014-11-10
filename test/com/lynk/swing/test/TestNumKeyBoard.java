package com.lynk.swing.test;

import java.awt.EventQueue;

import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

import com.lynk.swing.component.LynkNumKeyboard;

public class TestNumKeyBoard extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JFormattedTextField uiNum;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestNumKeyBoard frame = new TestNumKeyBoard();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public TestNumKeyBoard() {
		initComponents();
	}
	private void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 835, 386);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow]", "[][][grow]"));
		{
			uiNum = new JFormattedTextField();
			contentPane.add(uiNum, "cell 0 1,growx");
		}
		{
			LynkNumKeyboard uiKeyBoard = new LynkNumKeyboard();
			uiKeyBoard.addSource(uiNum);
			contentPane.add(uiKeyBoard, "cell 0 2,grow");
		}
	}

}
