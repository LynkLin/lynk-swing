package com.lynk.swing.component;

import javax.swing.table.AbstractTableModel;

/**
 * 用于显示表头RowHeader的JTable的TableModel，不实际存储数据
 */
class TableRowHeaderModel extends AbstractTableModel {
	private static final long serialVersionUID = -5952656937246157186L;
	
	private int rowCount;

	public TableRowHeaderModel(int rowCount) {
		this.rowCount = rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
		fireTableDataChanged();
	}

	public int getRowCount() {
		return rowCount;
	}

	public int getColumnCount() {
		return 1;
	}

	public Object getValueAt(int row, int column) {
		return row + 1;
	}
}
