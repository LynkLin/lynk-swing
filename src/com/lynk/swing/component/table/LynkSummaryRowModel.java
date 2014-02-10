package com.lynk.swing.component.table;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;


public class LynkSummaryRowModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;

	private JTable dataTable;
	
	public LynkSummaryRowModel(JTable jtable) {
		super();
		this.dataTable = jtable;
	}

	@Override
	public int getRowCount() {
		return 2;
	}

	@Override
	public int getColumnCount() {
		return dataTable.getModel().getColumnCount();
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return rowIndex == 0;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return null;
	}
}
