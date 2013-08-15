package com.lynk.swing.component.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import com.lynk.swing.common.Constants;

/**
 * 自动行高
 * 
 * @author Lynk
 */
public class MultiLineTableHeadRenderer extends DefaultTableCellRenderer implements Constants {
	private static final long serialVersionUID = 1L;
	
	private int horizontalAlignment = SwingConstants.CENTER;
	
	public MultiLineTableHeadRenderer() {
		this(SwingConstants.CENTER);
	}
	
	public MultiLineTableHeadRenderer(int horizontalAlignment) {
		super();
		this.horizontalAlignment = horizontalAlignment;
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		label.setForeground(Color.BLUE);
		label.setFont(APP_FONT);
		label.setHorizontalAlignment(horizontalAlignment);
		
		// 得到列的宽度
		TableColumnModel columnModel = table.getColumnModel();
		int width = columnModel.getColumn(column).getWidth();

		value = getShowValue(value.toString(), width);
		label.setText(value.toString());
		label.setSize(new Dimension(width, this.getHeight()));

		return label;
	}

	private Object getShowValue(String value, int colWidth) {
		// 根据当前的字体和显示值得到需要显示的宽度
		FontMetrics fm = this.getFontMetrics(this.getFont());
		int width = fm.stringWidth(value.toString());
		if (width < colWidth) {
			return value;
		}
		StringBuffer sb = new StringBuffer("<html>");
		char str;
		int tempW = 0;
		for (int i = 0; i < value.length(); i++) {
			str = value.charAt(i);
			tempW += fm.charWidth(str);
			if (tempW > colWidth) {
				sb.append("<br>");
				tempW = 0;
			}
			sb.append(str);
		}
		sb.append("</html>");
		return sb.toString();
	}
}