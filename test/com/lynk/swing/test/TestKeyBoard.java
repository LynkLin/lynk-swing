package com.lynk.swing.test;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

import com.lynk.swing.component.LynkFormattedTextField;
import com.lynk.swing.component.LynkKeyboard;
import com.lynk.swing.component.LynkTextField;

public class TestKeyBoard extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private LynkTextField uiText;
	private LynkFormattedTextField uiNum;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestKeyBoard frame = new TestKeyBoard();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public TestKeyBoard() {
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
			uiText = new LynkTextField();
			contentPane.add(uiText, "cell 0 0,growx");
		}
		{
			uiNum = new LynkFormattedTextField();
			contentPane.add(uiNum, "cell 0 1,growx");
		}
		{
			LynkKeyboard uiKeyBoard = new LynkKeyboard();
			uiKeyBoard.addSource(uiText);
			uiKeyBoard.addSource(uiNum);
			contentPane.add(uiKeyBoard, "cell 0 2,grow");
		}
	}

}
