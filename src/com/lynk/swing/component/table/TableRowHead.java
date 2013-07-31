package com.lynk.swing.component.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.DefaultRowSorter;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

public class TableRowHead extends JTable {
	private static final long serialVersionUID = -3555759051631273490L;
	
	public TableRowHead(JTable jtable) {
		this(jtable, 40);
	}
	
	public TableRowHead(final JTable refTable, int columnWidth) {
		super(new TableRowHeaderModel(refTable.getRowCount()));
		setRowHeight(refTable.getRowHeight());
		setSelectionMode(refTable.getSelectionModel().getSelectionMode());
		
		setFocusable(false);
		
		setAutoResizeMode(JTable.AUTO_RESIZE_OFF);// 不可以调整列宽
		getColumnModel().getColumn(0).setPreferredWidth(columnWidth);
		setDefaultRenderer(Object.class, new RowHeaderRenderer(refTable, this));// 设置渲染器
		setPreferredScrollableViewportSize(new Dimension(columnWidth, 0));
		getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {

					@Override
					public void valueChanged(ListSelectionEvent evt) {
						int[] rowIndexes = TableRowHead.this.getSelectedRows();
						if (rowIndexes != null && rowIndexes.length > 0) {
							refTable.clearSelection();
							for (int index : rowIndexes) {
								refTable.addRowSelectionInterval(index, index);
								refTable.addColumnSelectionInterval(0,
										refTable.getColumnCount() - 1);
							}
						}
					}
				});
	}
	
	/**
	 * 用于显示RowHeader的JTable的渲染器，可以实现动态增加，删除行，在Table中增加、删除行时RowHeader
	 * 一起变化。当选择某行时，该行颜色会发生变化
	 */
	class RowHeaderRenderer extends JLabel implements TableCellRenderer {
		private static final long serialVersionUID = 2096154305323045184L;
		
		JTable dataTable;// 需要添加rowHeader的JTable
		JTable headTable;// 用于显示rowHeader的JTable

		public RowHeaderRenderer(JTable reftable, JTable tableShow) {
			this.dataTable = reftable;
			this.headTable = tableShow;
			// reftable中选择行时，RowHeader会发生颜色变化
			reftable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				
				@Override
				public void valueChanged(ListSelectionEvent evt) {
					if(!evt.getValueIsAdjusting()) {
						headTable.repaint();
					}
					
				}
			});
			reftable.getModel().addTableModelListener(new TableModelListener() {
				
				@Override
				public void tableChanged(TableModelEvent evt) {
					AbstractTableModel model = (AbstractTableModel) evt.getSource();
					((TableRowHeaderModel) headTable.getModel()).setRowCount(model.getRowCount());
					headTable.repaint();
				}
			});
			reftable.getRowSorter().addRowSorterListener(new RowSorterListener() {
				
				@Override
				public void sorterChanged(RowSorterEvent evt) {
					RowSorter<?> sorter = evt.getSource();
					int count = dataTable.getModel().getRowCount();
					if(sorter instanceof DefaultRowSorter<?, ?>) {
						if(((DefaultRowSorter<?, ?>) sorter).getRowFilter() != null) {
							count = sorter.getViewRowCount();
						}
					}
					((TableRowHeaderModel) headTable.getModel()).setRowCount(count);
					headTable.repaint();
				}
			});
		}

		public Component getTableCellRendererComponent(JTable table, Object obj, boolean isSelected, boolean hasFocus, int row, int col) {
			JTableHeader header = dataTable.getTableHeader();
			this.setOpaque(true);
			setBorder(header.getBorder());
			setHorizontalAlignment(CENTER);// 让text居中显示
			setBackground(header.getBackground());// 设置背景色为TableHeader的背景色
			setFont(header.getFont());
			setText(String.valueOf(row + 1));
			if (isSelect(row)) // 当选取单元格时,在row header上设置成选取颜色
			{
				setForeground(Color.white);
				setBackground(header.getForeground());
			} else {
				setForeground(header.getForeground());
				setBackground(new Color(220,220,220));
			}
			return this;
		}

		private boolean isSelect(int row) {
			int[] sel = dataTable.getSelectedRows();
			for (int i = 0; i < sel.length; i++) {
				if (sel[i] == row) {
					return true;
				}
			}
			return false;
		}
	}
}