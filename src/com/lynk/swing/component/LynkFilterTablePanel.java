package com.lynk.swing.component;

import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.DefaultRowSorter;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.RowSorter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

import com.lynk.swing.common.Constants;
import com.lynk.swing.component.LynkFilterTable.IModelOrSorterChanged;
import com.lynk.swing.component.table.TableRowHead;

import net.miginfocom.swing.MigLayout;

import java.awt.SystemColor;

import java.math.BigDecimal;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LynkFilterTablePanel<T extends TableModel> extends JPanel implements Constants {
	private static final long serialVersionUID = 1L;
	
	private int dataNum = 0;// 数据库记录数
	
	private T dataModel;
	private LynkFilterTable uiTable;
	private JLabel uiSum;
	private JLabel uiAverage;
	private JLabel uiCount;
	private JLabel uiPageNowCountFilter;
	private JLabel uiDataNum;
	private TableRowHead rowHeadTable;
	
	public T getDataModel() {
		return dataModel;
	}
	
	public LynkFilterTable getTable() {
		return uiTable;
	}

	public TableRowHead getRowHeadTable() {
		return rowHeadTable;
	}

	/**
	 * @wbp.parser.constructor
	 */
	public LynkFilterTablePanel(T dm, boolean initHighLighter) {
		this(dm, initHighLighter, 40);
	}
	
	public LynkFilterTablePanel(T dm, boolean initHighLighter, int rowHeadwidth) {
		this.dataModel = dm;
		initComponents(initHighLighter, rowHeadwidth);
		addlistener();
	}
	
	protected LynkFilterTable initTable() {
		return new LynkFilterTable(dataModel);
	}
	
	protected TableRowHead initTableRowHead(LynkFilterTable uiTable, int rowHeadwidth) {
		return new TableRowHead(uiTable, rowHeadwidth);
	}
	
	private void initComponents(boolean initHighLighter, int rowHeadwidth) {
		setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			panel.setBackground(SystemColor.controlHighlight);
			add(panel, BorderLayout.SOUTH);
			panel.setLayout(new MigLayout("", "[]10[][]20[][][grow][][]20[][]20[][]", "[]"));
			{
				JButton uiClearFilter = new JButton("清除筛选");
				uiClearFilter.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						uiClearFilterActionPerformed(e);
					}
				});
				uiClearFilter.setFont(APP_FONT);
				uiClearFilter.setFocusable(false);
				uiClearFilter.setForeground(Color.BLUE);
				panel.add(uiClearFilter, "cell 0 0");
			}
			{
				JLabel label = new JLabel("共:");
				label.setFont(new Font("微软雅黑", Font.BOLD, 14));
				panel.add(label, "cell 1 0");
			}
			{
				uiDataNum = new JLabel("0");
				uiDataNum.setForeground(Color.BLUE);
				uiDataNum.setFont(new Font("微软雅黑", Font.PLAIN, 14));
				panel.add(uiDataNum, "cell 2 0");
			}
			{
				JLabel label = new JLabel("筛选后：");
				label.setFont(APP_FONT_BLOD);
				panel.add(label, "cell 3 0");
			}
			{
				uiPageNowCountFilter = new JLabel("0");
				uiPageNowCountFilter.setForeground(Color.BLUE);
				uiPageNowCountFilter.setFont(APP_FONT);
				panel.add(uiPageNowCountFilter, "cell 4 0");
			}
			{
				JLabel label = new JLabel("计数:");
				label.setFont(APP_FONT_BLOD);
				panel.add(label, "cell 6 0");
			}
			{
				uiCount = new JLabel("0");
				uiCount.setForeground(Color.BLUE);
				uiCount.setFont(APP_FONT);
				panel.add(uiCount, "cell 7 0");
			}
			{
				JLabel label = new JLabel("和:");
				label.setFont(APP_FONT_BLOD);
				panel.add(label, "cell 8 0");
			}
			{
				uiSum = new JLabel("0");
				uiSum.setForeground(Color.BLUE);
				uiSum.setFont(APP_FONT);
				panel.add(uiSum, "cell 9 0");
			}
			{
				JLabel label = new JLabel("均值:");
				label.setFont(APP_FONT_BLOD);
				panel.add(label, "cell 10 0");
			}
			{
				uiAverage = new JLabel("0");
				uiAverage.setForeground(Color.BLUE);
				uiAverage.setFont(APP_FONT);
				panel.add(uiAverage, "cell 11 0");
			}
			
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			add(scrollPane, BorderLayout.CENTER);
			{
				uiTable = initTable();
				scrollPane.setViewportView(uiTable);
				scrollPane.setRowHeaderView(initTableRowHead(uiTable, rowHeadwidth));
			}
		}
	}
	
	private void addlistener() {
		uiTable.addModelOrSorterChanged(new IModelOrSorterChanged() {
			
			@Override
			public void modelOrSorterChanged() {
				int modelCount = dataModel.getRowCount();
				
				int sortRowCount = -1;
				RowSorter<?> sorter = uiTable.getRowSorter();
				if(sorter instanceof DefaultRowSorter<?, ?>) {
					if(((DefaultRowSorter<?, ?>) sorter).getRowFilter() != null) {
						sortRowCount = uiTable.getRowSorter().getViewRowCount();
					}
				}
				int viewCount;
				if(sortRowCount == -1) {
					viewCount = modelCount;
				} else {
					viewCount = sortRowCount;
				}
				uiPageNowCountFilter.setText(Integer.toString(viewCount));
			}
		});
		uiTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent evt) {
				if(uiTable.getSelectedRowCount() == 0 || uiTable.getSelectedColumnCount() == 0) {
					uiCount.setText("0");
					uiSum.setText("0");
					uiAverage.setText("0");
				} else {
					int[] rows = uiTable.getSelectedRows();
					int[] columns = uiTable.getSelectedColumns();
					int count = rows.length * columns.length;
					uiCount.setText(Integer.toString(count));
					BigDecimal sum = BigDecimal.ZERO;
					for(int row: rows) {
						int modelRow = uiTable.convertRowIndexToModel(row);
						for(int column: columns) {
							int modelColumn = uiTable.convertColumnIndexToModel(column);
							Object value = dataModel.getValueAt(modelRow, modelColumn);
							if(value instanceof Number) {
								sum = sum.add(new BigDecimal(((Number) value).toString()));
							} else {
								uiSum.setText("-");
								uiAverage.setText("-");
								return;
							}
						}
					}
					BigDecimal average = sum.divide(new BigDecimal(count), 4, BigDecimal.ROUND_HALF_UP);
					uiSum.setText(sum.toString());
					uiAverage.setText(average.toString());
				}
			}
		});
	}
	
	/**
	 * 设置数据总记录数
	 * @param dataNum
	 */
	public void setDataNum(int dataNum) {
		this.dataNum = dataNum;
		uiDataNum.setText(Integer.toString(dataNum));
	}
	
	/**
	 * 数据总记录数
	 * @return
	 */
	public int getDataNum() {
		return dataNum;
	}
	
	public int getSelectedRowIndexView() {
		return uiTable.getSelectedRow();
	}
	
	public int getSelectedRowIndexModel() {
		return uiTable.convertRowIndexToModel(getSelectedRowIndexView());
	}
	
	public int[] getSelectedRowsIndexView() {
		return uiTable.getSelectedRows();
	}
	
	public int[] getSelectedRowsIndexModel() {
		int[] indexView = getSelectedRowsIndexView();
		int[] indexModel = new int[indexView.length];
		for(int i = 0; i < indexView.length; i++) {
			indexModel[i] = uiTable.convertRowIndexToModel(indexView[i]);
		}
		return indexModel;
	}
	
	protected void uiClearFilterActionPerformed(ActionEvent evt) {
		uiTable.clearFilter();
	}
}