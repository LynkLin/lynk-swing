package com.lynk.swing.component.table;

import java.awt.Component;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;


public class FilterTableHeaderRenderer extends JLabel implements TableCellRenderer {
	private static final long serialVersionUID = 1L;

	private ImageIcon icon;
	
	private boolean hasGetTextPosition = false;
	private int textPosition;
	
	private TableFilter filter;
	
	public FilterTableHeaderRenderer(TableFilter filter) {
		super();
		this.filter = filter;
	}

	private Icon getFilterIcon() {
		if (icon == null) {
			icon = new ImageIcon( getClass().getResource("/resources/images/filter.png"));
			icon = new ImageIcon( icon.getImage().getScaledInstance(12, 12, Image.SCALE_SMOOTH));
		}
		return icon;
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		final JLabel label = (JLabel) table.getTableHeader().getDefaultRenderer().getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		if(!hasGetTextPosition) {
			textPosition = label.getHorizontalTextPosition();
			hasGetTextPosition = true;
		}
		int modelColumn =  table.convertColumnIndexToModel(column);
		if(filter.isFilted(modelColumn)) {
        	Icon originalIcon = label.getIcon();
        	if ( originalIcon == null ) {
        	  label.setIcon( getFilterIcon()); 	
        	}
            label.setHorizontalTextPosition( JLabel.TRAILING );
		}else {
			label.setHorizontalTextPosition(textPosition);
		}
        return label;
	}
}
