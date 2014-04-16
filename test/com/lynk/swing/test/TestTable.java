package com.lynk.swing.test;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.lynk.swing.component.LynkFilterTable;
import com.lynk.swing.component.LynkTableExportDialog;
import com.lynk.swing.component.table.TableRowHead;
import com.lynk.swing.lnf.LynkLookAndFeel;

import javax.swing.table.AbstractTableModel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.UIManager;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class TestTable extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private TestModel model;
	private LynkFilterTable lynkTable;
	
//	private DefaultTableModel model = new DefaultTableModel(new String[][] {
//			{ "姓名1", "年龄1", "男", "出生日期1" }
//			, { "姓名2", "年龄2", "男", "出生日期2" }
//			, { "姓名3", "年龄3", "男", "出生日期3" }
//			, { "姓名4", "年龄4", "男", "出生日期4" }
//			, { "姓名5", "年龄5", "女", "出生日期5" }
//			, { "姓名6", "年龄6", "女", "出生日期6" }
//			, { "姓名7", "年龄7", "女", "出生日期7" }
//			, { "姓名8", "年龄8", "女", "出生日期8" }},
//			new String[] { "姓名", "年龄", "性别", "出生日期9" });

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame.setDefaultLookAndFeelDecorated(true);
					UIManager.setLookAndFeel(LynkLookAndFeel.LNF_DEFAULT.getClassName());
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
		{
			JToolBar toolBar = new JToolBar();
			toolBar.setFloatable(false);
			contentPane.add(toolBar, BorderLayout.NORTH);
			{
				JButton button = new JButton("导出");
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						buttonActionPerformed(e);
					}
				});
				toolBar.add(button);
			}
			{
				JButton button = new JButton("加载");
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						buttonActionPerformed1(e);
					}
				});
				toolBar.add(button);
			}
			
		}
		
		
		

		{
			JPanel panel = new JPanel();
			panel.setLayout(new BorderLayout());
			contentPane.add(panel, BorderLayout.CENTER);
			{
				JScrollPane scrollPane = new JScrollPane();
				panel.add(scrollPane, BorderLayout.CENTER);
				model = new TestModel();
				lynkTable = new LynkFilterTable(model, true);
				scrollPane.setViewportView(lynkTable);
				scrollPane.setRowHeaderView(new TableRowHead(lynkTable));
			}
//			{
//				JScrollPane scrollPane = new JScrollPane();
//				panel.add(scrollPane, BorderLayout.SOUTH);
//				LynkSummaryTable summaryTable = new LynkSummaryTable(lynkTable);
//				scrollPane.setViewportView(summaryTable);
//				scrollPane.setRowHeaderView(new TableRowHead(summaryTable));
//			}
		}
		
	}

	protected void buttonActionPerformed(ActionEvent e) {
		LynkTableExportDialog.showDialog(this, lynkTable);
	}
	protected void buttonActionPerformed1(ActionEvent e) {
//		DefaultTableModel model = new DefaultTableModel(new String[][] {
//				{ "姓名1", "年龄1", "男", "出生日期1" }
//				, { "姓名2", "年龄2", "男", "出生日期2" }
//				, { "姓名3", "年龄3", "男", "出生日期3" }
//				, { "姓名4", "年龄4", "男", "出生日期4" }
//				, { "姓名5", "年龄5", "女", "出生日期5" }
//				, { "姓名6", "年龄6", "女", "出生日期6" }
//				, { "姓名7", "年龄7", "女", "出生日期7" }
//				, { "姓名8", "年龄8", "女", "出生日期8" }},
//				new String[] { "姓名", "年龄", "性别", "出生日期9" });
//		lynkTable.setModel(model);
//		lynkTable.clearFilter();
		List<String> datas = new ArrayList<>();
		datas.add("姓名1");
		datas.add("姓名2");
		datas.add("姓名3");
		datas.add("姓名4");
		datas.add("姓名5");
		datas.add("姓名6");
		datas.add("姓名7");
		model.setDatas(datas);
	}
}

class TestModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private String[] COLUMNS = {"姓名"};
	private List<String> datas = new ArrayList<>();
	
	public void setDatas(List<String> datas) {
		this.datas = datas;
		fireTableDataChanged();
	}

	@Override
	public int getRowCount() {
		return datas.size();
	}

	@Override
	public int getColumnCount() {
		return COLUMNS.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(columnIndex == 0) {
			return datas.get(rowIndex);
		}
		return null;
	}
	
}
