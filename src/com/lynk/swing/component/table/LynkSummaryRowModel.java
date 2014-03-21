package com.lynk.swing.component.table;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;


public class LynkSummaryRowModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;

	private List<String> operations;
	
	private JTable dataTable;
	
	public LynkSummaryRowModel(JTable jtable) {
		super();
		this.dataTable = jtable;
		operations = new ArrayList<>(jtable.getModel().getColumnCount());
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
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		if(rowIndex == 0) {
			
		}
	}

	@Override
	public void fireTableCellUpdated(int row, int column) {
		super.fireTableCellUpdated(row, column);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(rowIndex == 0) {
			return operations.get(columnIndex);
		} else {
			
		}
		return null;
	}
}
