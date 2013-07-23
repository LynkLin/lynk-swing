package com.lynk.swing.test;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.lynk.swing.component.LynkTable;
import com.lynk.swing.component.LynkTableExportDialog;

import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TestTable extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private LynkTable lynkTable;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestTable frame = new TestTable();
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
	public TestTable() {
		initComponents();
	}
	private void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 582, 484);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		contentPane.add(toolBar, BorderLayout.NORTH);
		
		JButton button = new JButton("导出");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonActionPerformed(e);
			}
		});
		toolBar.add(button);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		DefaultTableModel model = new DefaultTableModel(new String[][]{}, new String[]{"姓名", "年龄", "性别", "性别", "性别", "性别", "性别", "性别", "性别", "性别", "性别", "性别", "性别", "性别", "性别", "性别", "性别", "性别", "性别", "性别", "性别", "性别", "性别", "性别", "性别", "性别", "性别", "性别", "性别", "性别", "性别"});
		lynkTable = new LynkTable(model, true);
		scrollPane.setViewportView(lynkTable);
	}

	protected void buttonActionPerformed(ActionEvent e) {
		LynkTableExportDialog.showDialog(this, lynkTable);
	}
}
