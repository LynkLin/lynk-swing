package com.lynk.swing.component;

import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.DefaultComboBoxModel;
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

import javax.swing.JComboBox;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.math.BigDecimal;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LynkPagerFilterTablePanel<T extends TableModel> extends JPanel implements Constants {
	private static final long serialVersionUID = 1L;
	
	private boolean listenPageChange = true;
	private int dataNum = 0;// 数据库记录数
	
	private T dataModel;
	private LynkFilterTable uiTable;
	private JComboBox<Integer> uiPageMax;
	private JComboBox<Integer> uiPageNow;
	private JLabel uiSum;
	private JLabel uiAverage;
	private JLabel uiCount;
	private JLabel uiPageNowCount;
	private JLabel uiPageNowCountFilter;
	private JLabel uiDataNum;
	private JLabel uiPageCount;
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
	public LynkPagerFilterTablePanel(T dm, boolean initHighLighter, PageChange pageChange) {
		this(dm, initHighLighter, pageChange, 40);
	}
	
	public LynkPagerFilterTablePanel(T dm, boolean initHighLighter, PageChange pageChange, int rowHeadwidth) {
		this.dataModel = dm;
		initComponents(initHighLighter, rowHeadwidth);
		addlistener(pageChange);
	}
	
	protected LynkFilterTable initTable() {
		return new LynkFilterTable(dataModel);
	}
	
	private void initComponents(boolean initHighLighter, int rowHeadwidth) {
		setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			panel.setBackground(SystemColor.controlHighlight);
			add(panel, BorderLayout.SOUTH);
			panel.setLayout(new MigLayout("", "[]10[][]20[]5[]20[][][][]20[][]20[][][grow][][]20[][]20[][]", "[]"));
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
				JLabel label = new JLabel("每页最多:");
				label.setFont(APP_FONT_BLOD);
				panel.add(label, "cell 3 0");
			}
			{
				uiPageMax = new JComboBox<Integer>();
				uiPageMax.setForeground(Color.BLUE);
				uiPageMax.setFont(APP_FONT);
				uiPageMax.setModel(new DefaultComboBoxModel<>(new Integer[]{1000, 5000, 10000, 30000, 50000, 100000}));
				uiPageMax.setEditable(true);
				uiPageMax.setSelectedItem(10000);
				panel.add(uiPageMax, "cell 4 0");
			}
			{
				JLabel label = new JLabel("页号:");
				label.setFont(APP_FONT_BLOD);
				panel.add(label, "cell 5 0");
			}
			{
				uiPageNow = new JComboBox<Integer>(new DefaultComboBoxModel<>(new Integer[]{1}));
				uiPageNow.setForeground(Color.BLUE);
				uiPageNow.setEnabled(false);
				uiPageNow.setMaximumRowCount(10);
				uiPageNow.setFont(APP_FONT);
				uiPageNow.setFocusable(false);
				panel.add(uiPageNow, "cell 6 0");
			}
			{
				JLabel label = new JLabel("/");
				label.setFont(new Font("微软雅黑", Font.BOLD, 14));
				panel.add(label, "cell 7 0");
			}
			{
				uiPageCount = new JLabel("1");
				uiPageCount.setForeground(Color.BLUE);
				uiPageCount.setFont(new Font("微软雅黑", Font.PLAIN, 14));
				panel.add(uiPageCount, "cell 8 0");
			}
			{
				JLabel label = new JLabel("本页数据：");
				label.setFont(APP_FONT_BLOD);
				panel.add(label, "cell 9 0");
			}
			{
				uiPageNowCount = new JLabel("0");
				uiPageNowCount.setForeground(Color.BLUE);
				uiPageNowCount.setFont(APP_FONT);
				panel.add(uiPageNowCount, "cell 10 0");
			}
			{
				JLabel label = new JLabel("筛选后：");
				label.setFont(APP_FONT_BLOD);
				panel.add(label, "cell 11 0");
			}
			{
				uiPageNowCountFilter = new JLabel("0");
				uiPageNowCountFilter.setForeground(Color.BLUE);
				uiPageNowCountFilter.setFont(APP_FONT);
				panel.add(uiPageNowCountFilter, "cell 12 0");
			}
			{
				JLabel label = new JLabel("计数:");
				label.setFont(APP_FONT_BLOD);
				panel.add(label, "cell 14 0");
			}
			{
				uiCount = new JLabel("0");
				uiCount.setForeground(Color.BLUE);
				uiCount.setFont(APP_FONT);
				panel.add(uiCount, "cell 15 0");
			}
			{
				JLabel label = new JLabel("和:");
				label.setFont(APP_FONT_BLOD);
				panel.add(label, "cell 16 0");
			}
			{
				uiSum = new JLabel("0");
				uiSum.setForeground(Color.BLUE);
				uiSum.setFont(APP_FONT);
				panel.add(uiSum, "cell 17 0");
			}
			{
				JLabel label = new JLabel("均值:");
				label.setFont(APP_FONT_BLOD);
				panel.add(label, "cell 18 0");
			}
			{
				uiAverage = new JLabel("0");
				uiAverage.setForeground(Color.BLUE);
				uiAverage.setFont(APP_FONT);
				panel.add(uiAverage, "cell 19 0");
			}
			
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			add(scrollPane, BorderLayout.CENTER);
			{
				uiTable = initTable();
				scrollPane.setViewportView(uiTable);
				rowHeadTable = new TableRowHead(uiTable, rowHeadwidth);
				scrollPane.setRowHeaderView(rowHeadTable);
			}
		}
	}
	
	private void addlistener(final PageChange pageChange) {
		uiPageMax.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(uiPageNow.isEnabled() && e.getStateChange() == ItemEvent.SELECTED) {
					setDataNum();
					pageChange.pageMaxChange((int) uiPageMax.getSelectedItem());
				}
			}
		});
		uiPageNow.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(listenPageChange && e.getStateChange() == ItemEvent.SELECTED) {
					pageChange.pageNowChange(getStartIndex(), getLimit()); 
				}
			}
		});
		uiTable.addModelOrSorterChanged(new IModelOrSorterChanged() {
			
			@Override
			public void modelOrSorterChanged() {
				int modelCount = dataModel.getRowCount();
				uiPageNowCount.setText(Integer.toString(modelCount));
				
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
	 * 初始行
	 * @return
	 */
	private int getStartIndex() {
		int pageMax = (int) uiPageMax.getSelectedItem();
		int pageNow = (int) uiPageNow.getSelectedItem();
		int startIndex = (pageNow - 1) * pageMax;
		return startIndex;
	}
	
	/**
	 * 结束行
	 * @return
	 */
	private int getLimit() {
		int pageMax = (int) uiPageMax.getSelectedItem();
		int pageNow = (int) uiPageNow.getSelectedItem();
		int startIndex = (pageNow - 1) * pageMax;
		int endIndex = pageNow * pageMax;
		if(endIndex > dataNum) {
			return dataNum - startIndex;
		} else {
			return pageMax;
		}
	}
	
	/**
	 * 设置数据总记录数
	 * @param dataNum
	 */
	public void setDataNum(int dataNum) {
		this.dataNum = dataNum;
		uiDataNum.setText(Integer.toString(dataNum));
		uiPageMax.addItem(dataNum);
		uiPageNow.setEnabled(true);
		setDataNum();
	}
	
	private void setDataNum() {
		listenPageChange = false;
		uiPageNow.removeAllItems();
		//计算页数
		int pageMax = (int) uiPageMax.getSelectedItem();
		int pageNum = dataNum / pageMax;
		if(dataNum % pageMax != 0) {
			pageNum++;
		}
		uiPageCount.setText(Integer.toString(pageNum));
		
		for(int i = 0; i < pageNum; i++) {
			uiPageNow.addItem(i+ 1);
		}
		if(uiPageNow.getItemCount() > 0) {
			uiPageNow.setSelectedIndex(0);
		}
		listenPageChange = true;
	}

	/**
	 * 数据总记录数
	 * @return
	 */
	public int getDataNum() {
		return dataNum;
	}

	/**
	 * 每页显示数量
	 * @return
	 */
	public int getPageMax() {
		return (int) uiPageMax.getSelectedItem();
	}
	/**
	 * 页号
	 * @return
	 */
	public int getPageNow() {
		return (int) uiPageNow.getSelectedItem();
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
	
	public interface PageChange{
		void pageMaxChange(int pageMax);
		
		void pageNowChange(int startIndex, int pageMax);
	}
	protected void uiClearFilterActionPerformed(ActionEvent evt) {
		uiTable.clearFilter();
	}
}