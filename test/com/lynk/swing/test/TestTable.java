package com.lynk.swing.test;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.lynk.swing.component.LynkFilterTable;
import com.lynk.swing.component.LynkTableExportDialog;
import com.lynk.swing.component.table.TableRowHead;

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
	private LynkFilterTable lynkTable;

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

		DefaultTableModel model = new DefaultTableModel(new String[][] {
				{ "姓名1", "年龄1", "男", "出生日期" }
				, { "姓名2", "年龄2", "男", "出生日期" }
				, { "姓名3", "年龄3", "男", "出生日期" }
				, { "姓名4", "年龄4", "男", "出生日期" }
				, { "姓名5", "年龄5", "女", "出生日期" }
				, { "姓名6", "年龄6", "女", "出生日期" }
				, { "姓名7", "年龄7", "女", "出生日期" }
				, { "姓名8", "年龄8", "女", "出生日期" }},
				new String[] { "姓名", "年龄", "性别", "出生日期" });
		lynkTable = new LynkFilterTable(model, true);
		scrollPane.setViewportView(lynkTable);
		scrollPane.setRowHeaderView(new TableRowHead(lynkTable));
	}

	protected void buttonActionPerformed(ActionEvent e) {
		LynkTableExportDialog.showDialog(this, lynkTable);
	}
}
