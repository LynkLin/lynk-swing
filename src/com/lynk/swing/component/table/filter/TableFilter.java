package com.lynk.swing.component.table.filter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.swing.DefaultRowSorter;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.table.TableModel;

public class TableFilter extends HashMap<Integer, List<FilterItem>>{
	private static final long serialVersionUID = 1L;
	
	private JTable table;
	private TableRowFilter filter = new TableRowFilter();
	
	/**
	 * 是否正在载入数据中
	 */
	private boolean isSettingFilter = true;

	public boolean isSettingFilter() {
		return isSettingFilter;
	}

	public TableFilter(JTable table) {
		super();
		this.table = table;
	}
	
	public void setFilter(int columnIndex) {
		isSettingFilter = true;
		TableModel model = table.getModel();
		List<FilterItem> items = new ArrayList<>();
//		for(int rowIndex = 0; rowIndex < model.getRowCount(); rowIndex++) {
//			FilterItem item = new FilterItem(model.getValueAt(rowIndex, columnIndex));
//			if(!items.contains(item)) {
//				items.add(item);
//			}
//		}
		for(int rowIndex = 0; rowIndex < table.getRowCount(); rowIndex++) {
			FilterItem item = new FilterItem(model.getValueAt(table.convertRowIndexToModel(rowIndex), columnIndex));
			if(!items.contains(item)) {
				items.add(item);
			}
		}
		Collections.sort(items);
		put(columnIndex, items);
		isSettingFilter = false;
	}
	
	
//	/**
//	 * 重设筛选
//	 * @param cIndexNotIn
//	 */
//	public void resetFilter(int cIndexNotIn, DefaultRowSorter<?, ?> sorter) {
//		isSettingFilter = true;
//		List<FilterItem> oriItems = get(cIndexNotIn);
//		clear();
//		put(cIndexNotIn, oriItems);
//		TableModel model = table.getModel();
//		for(int columnIndex = 0; columnIndex < model.getColumnCount(); columnIndex++) {
//			if(columnIndex == cIndexNotIn) {
//				continue;
//			}
//			List<FilterItem> items = new ArrayList<>();
//			for(int i = 0; i < sorter.getViewRowCount(); i++) {
//				int mRowIndex = ((DefaultRowSorter<?, ?>) sorter).convertRowIndexToModel(i);
//				items.add(new FilterItem(model.getValueAt(mRowIndex, columnIndex)));
//				
//			}
//			Collections.sort(items);
//			put(columnIndex, items);
//		}
//		isSettingFilter = false;
//	}
	
	public boolean isSelected(int columnIndex, Object obj) {
		return isSelected(get(columnIndex), obj);
	}
	
	public boolean isSelected(List<FilterItem> items, Object obj) {
		if(items == null) {
			return true;
		}
		int index = items.indexOf(new FilterItem(obj));
		if(index < 0) {
			return false;
		}
		FilterItem item = items.get(index);
		if(item == null) {
			return false;
		}
		return item.isSelected();
	}
	
	@SuppressWarnings("unchecked")
	public boolean execute(int columnIndex) {
		RowSorter<?> sorter = table.getRowSorter();
		if(sorter instanceof DefaultRowSorter<?, ?>) {
			RowFilter<Object, Object> parentFilter = (RowFilter<Object, Object>) ((DefaultRowSorter<?, ?>) sorter).getRowFilter();
			if(!(parentFilter instanceof TableRowFilter)) {
				filter.setParentFilter(parentFilter);
			}
			((DefaultRowSorter<?, ?>) sorter).setRowFilter(filter);
			table.getTableHeader().repaint();
			return true;
		}
		return false;
	}
	
	public boolean isFilted(int columnIndex) {
		List<FilterItem> items = get(columnIndex);
		if(items != null) {
			for(FilterItem item: items) {
				if(!item.isSelected()) {
					return true;
				}
			}
		}
		return false;
	}
	
	class TableRowFilter extends RowFilter<Object, Object> implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private RowFilter<Object, Object> parentFilter;

		public void setParentFilter(RowFilter<Object, Object> parentFilter ) {
			this.parentFilter = (parentFilter == null ||  parentFilter == this )? null: parentFilter;
		}
		@Override
		public boolean include(RowFilter.Entry<? extends Object, ? extends Object> entry) {
			if (parentFilter != null && !parentFilter.include(entry)) {
				return false;
			}
			for(int i = 0; i < entry.getValueCount(); i ++) {
				if(!isFilted(i)) {
					continue;
				}
				List<FilterItem> items = get(i);
				if(!isSelected(items, entry.getValue(i))) {
//					System.out.println("false: " + entry.getValue(i));
					return false;
				}
//				System.out.println("true: " + entry.getValue(i));
			}
			return true;
		}
		
	}
}
