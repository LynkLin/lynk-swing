package com.lynk.swing.component.table.filter;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.JLabel;

import com.lynk.swing.common.Constants;

public class FilterHeaderRenderer extends JPanel implements Constants, TableCellRenderer {
	private static final long serialVersionUID = 1L;
	
	private TableFilter filter;
	
	public FilterHeaderRenderer(TableFilter filter) {
		super();
		this.filter = filter;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		final JLabel label = (JLabel) table.getTableHeader().getDefaultRenderer().getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		int modelColumn =  table.convertColumnIndexToModel(column);
		if(filter.isFilted(modelColumn)) {
			label.setForeground(Color.RED);
			label.setFont(label.getFont().deriveFont(Font.BOLD));
		} else {
			label.setForeground(Color.BLUE);
			label.setFont(label.getFont().deriveFont(Font.PLAIN));
		}
        return label;
	}
}
