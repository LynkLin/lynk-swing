package com.lynk.swing.component.table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class LynkTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	
	protected String[] head;

	public LynkTableModel(String[] head) {
		this.head = head;
	}

	public LynkTableModel(String[] head, String[] visibleHead) {
		this.head = head;
		setColumnSort(visibleHead);
	}
	
	public LynkTableModel(String[] head, String visibleHead) {
		this.head = head;
		setColumnSort(visibleHead);
	}

	private void setColumnSort(String[] visibleHead) {
		if(visibleHead == null || visibleHead.length == 0) {
			return;
		}
		List<String> heads = new ArrayList<>(Arrays.asList(head));
		List<String> newHeads = new ArrayList<>(heads.size());
		for(int i = 0; i < visibleHead.length; i++) {
			String columnName = visibleHead[i];
			if(heads.contains(columnName)) {
				newHeads.add(columnName);
				heads.remove(visibleHead[i]);
			}
		}
		newHeads.addAll(heads);
		head = newHeads.toArray(new String[newHeads.size()]);
	}
	
	private void setColumnSort(String visibleHead) {
		if(visibleHead != null && visibleHead.length() > 0) {
			setColumnSort(visibleHead.split(","));
		}
	}
	
	@Override
	public int getColumnCount() {
		if(head != null) {
			return head.length;
		}
		return 0;
	}
	
	@Override
	public String getColumnName(int column) {
		return head[column];
	}
	
	@Override
	public int getRowCount() {
		return 0;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return null;
	}
}
