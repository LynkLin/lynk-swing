package com.lynk.swing.component.table;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

/**
 * 自动行高
 * 
 * @author Lynk
 */
public class MultiLineTableHeadRenderer extends DefaultTableCellRenderer {
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

		JTableHeader header = table.getTableHeader();
		setForeground(header.getForeground());
		setBackground(header.getBackground());
		setFont(header.getFont());
		setOpaque(true);
		setBorder(UIManager.getBorder("TableHeader.cellBorder"));

		// 得到列的宽度
		TableColumnModel columnModel = table.getColumnModel();
		int width = columnModel.getColumn(column).getWidth();

		value = getShowValue(value.toString(), width);
		setText(value.toString());
		setSize(new Dimension(width, this.getHeight()));

		setHorizontalAlignment(horizontalAlignment);

		return this;
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