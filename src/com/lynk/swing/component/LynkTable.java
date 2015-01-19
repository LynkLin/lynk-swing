package com.lynk.swing.component;

import java.awt.Color;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.AlignmentHighlighter;
import org.jdesktop.swingx.decorator.HighlighterFactory;
import org.jdesktop.swingx.table.TableColumnExt;

import com.lynk.swing.common.Constants;
import com.lynk.swing.component.table.LynkColumnControlButton;

/**
 * 自定义
 * AUTO_RESIZE_OFF, ColumnSelection, CellSelection, RowHeight 24, 
 * TableHeader CENTER, TableHeader bg blue, 
 * ColumnControlVisible true, Highlighter RowHighlighter
 * @author Administrator
 *
 */
public class LynkTable extends JXTable implements Constants {
	private static final long serialVersionUID = 1L;

	private static final String DEFAULT_SELECT_ALL_TEXT = "全选";
	private static final String DEFAULT_COPY_TEXT = "复制";
	private static final String DEFAULT_COPY_HEAD_TEXT = "复制(含标题)";
	private static final String DEFAULT_ADD_TEXT = "新增";
	private static final String DEFAULT_DELETE_TEXT = "删除";
	private static final String DEFAULT_RESTORE_TEXT = "还原删除";
	
	private static KeyStroke COPY = KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK, false);
	
	private JPopupMenu uiPopMenu;
	
	private String addText = DEFAULT_ADD_TEXT;
	private String deleteText = DEFAULT_DELETE_TEXT;
	private String restoreText = DEFAULT_RESTORE_TEXT;
	
	private JMenuItem uiSelectAll;
	private JMenuItem uiCpoy;
	private JMenuItem uiCpoyWithHead;
	private JMenuItem uiAdd;
	private JMenuItem uiDelete;
	private JMenuItem uiRestore;
	
	private MouseDoubleClick mouseDoubleClick;
	private MenuAddAction menuAddAction;
	private MenuDeleteAction menuDeleteAction;
	private MenuRestoreAction menuRestoreAction;
	
	private boolean initHighLighter = true;
	private int highLighterRowNum = 1;
	
	public JPopupMenu getUiPopMenu() {
		return uiPopMenu;
	}

	public LynkTable(TableModel dm) {
		this(dm, true, 1);
	}
	
	public LynkTable(TableModel dm, boolean initHighLighter) {
		this(dm, initHighLighter, 1);
	}
	
	public LynkTable(TableModel dm, boolean initHighLighter, int highLighterRowNum) {
		super(dm);
		this.initHighLighter = initHighLighter;
		this.highLighterRowNum = highLighterRowNum;
		init();
	}
	
	/**
	 * 新增文本
	 * @param addText
	 */
	public void setAddText(String addText) {
		this.addText = addText;
	}

	/**
	 * 删除文本
	 * @param deleteText
	 */
	public void setDeleteText(String deleteText) {
		this.deleteText = deleteText;
	}

	/**
	 * 恢复文本
	 * @param restoreText
	 */
	public void setRestoreText(String restoreText) {
		this.restoreText = restoreText;
	}

	/**
	 * 设置列宽度
	 * @param sizes, 小于0表示该列长度不可调整
	 */
	public void setColumnSize(int... sizes) {
		TableColumnModel cm = getColumnModel();
		for (int i = 0; i < (cm.getColumnCount() < sizes.length ? cm
				.getColumnCount() : sizes.length); i++) {
			if(sizes[i] < 0) {
				cm.getColumn(i).setResizable(false);
			}
			cm.getColumn(i).setPreferredWidth(Math.abs(sizes[i]));
		}
	}
	
	/**
	 * 设置列宽度
	 * @param sizeStr, 逗号隔开
	 */
	public void setColumnSize(String sizeStr) {
		if(sizeStr != null && sizeStr.length() > 0) {
			String[] sizeStrs = sizeStr.split(",");
			TableColumnModel cm = getColumnModel();
			for (int i = 0; i < (cm.getColumnCount() < sizeStrs.length ? cm
					.getColumnCount() : sizeStrs.length); i++) {
				if(Integer.parseInt(sizeStrs[i].trim()) < 0) {
					cm.getColumn(i).setResizable(false);
				}
				cm.getColumn(i).setPreferredWidth(Math.abs(Integer.parseInt(sizeStrs[i].trim())));
			}
		}
	}
	
	/**
	 * 设置列是否显示
	 * @param visibleColumnNameStr, 逗号隔开
	 */
	public void setColumnVisible(String visibleColumnNameStr) {
		if(visibleColumnNameStr != null && visibleColumnNameStr.length() > 0) {
			List<String> visibleColumnNames = new ArrayList<>(Arrays.asList(visibleColumnNameStr.split(",")));
			int columnCount = getColumnCount(true);
			for(int i = columnCount - 1; i >= 0 ; i--) {
				TableColumnExt columnExt =  getColumnExt(i);
				String columnName = columnExt.getTitle();
				if(visibleColumnNames.contains(columnName)) {
					columnExt.setVisible(true);
				} else {
					columnExt.setVisible(false);
				}
			}
		}
	}
	
	/**
	 * 设置列是否显示
	 * @param visibleColumnNameStr, 逗号隔开
	 */
	public void setColumnVisible(String[] visibleColumnNamesArray) {
		List<String> visibleColumnNames = Arrays.asList(visibleColumnNamesArray);
		int columnCount = getColumnCount(true);
		for(int i = columnCount - 1; i >= 0 ; i--) {
			TableColumnExt columnExt =  getColumnExt(i);
			String columnName = columnExt.getTitle();
			if(visibleColumnNames.contains(columnName)) {
				columnExt.setVisible(true);
			} else {
				columnExt.setVisible(false);
			}
		}
	}
	
	/**
	 * 设置table显示样式
	 * @param visibleColumnNames
	 * @param sizeStr
	 */
	public void customizeTable(String[] visibleColumnNames, String sizeStr) {
		setColumnVisible(visibleColumnNames);
		if(sizeStr != null && sizeStr.length() > 0) {
			setColumnSize(sizeStr);
		}
	}
	
	/**
	 * 设置table显示样式
	 * @param visibleColumnNameStr
	 * @param sizeStr
	 */
	public void customizeTable(String visibleColumnNameStr, String sizeStr) {
		if(visibleColumnNameStr != null && visibleColumnNameStr.length() > 0) {
			setColumnVisible(visibleColumnNameStr);
		}
		if(sizeStr != null && sizeStr.length() > 0) {
			setColumnSize(sizeStr);
		}
	}
	
	/**
	 * 得表格显示的列宽字符串, 用逗号隔开
	 * @return
	 */
	public String getVisibleColumnWidthStr() {
		String sizeStr = "";
		int count = getColumnCount();
		for(int i = 0; i < count; i++) {
			TableColumnExt columnExt = getColumnExt(i);
			String Size = Integer.toString(columnExt.getWidth());
			sizeStr = sizeStr + Size + ",";
		}
		if(sizeStr.endsWith(",")) {
			sizeStr = sizeStr.substring(0, sizeStr.length() - 1);
		}
		return sizeStr;
	}
	
	/**
	 * 获得表格显示的列名字符串, 用,隔开
	 * @param jTable
	 * @return
	 */
	public String getVisibleColumnNameStr() {
		String titleStr = "";
		int count = getColumnCount();
		for(int i = 0; i < count; i++) {
			TableColumnExt columnExt = getColumnExt(i);
			String title = columnExt.getTitle();
			titleStr = titleStr + title + ",";
		}
		if(titleStr.endsWith(",")) {
			titleStr = titleStr.substring(0, titleStr.length() - 1);
		}
		return titleStr;
	}
	
	
	
	@Override
	protected JComponent createDefaultColumnControl() {
		return new LynkColumnControlButton(this);
	}

	/**
	 * 鼠标双击事件
	 * @param mouseDoubleClick
	 */
	public void setMouseDoubleClick(MouseDoubleClick mouseDoubleClick) {
		this.mouseDoubleClick = mouseDoubleClick;
	}

	/**
	 * popmenu 新增事件
	 * @param menuAddAction
	 */
	public void setMenuAddAction(MenuAddAction menuAddAction) {
		this.menuAddAction = menuAddAction;
		uiAdd = new JMenuItem(addText, new ImageIcon(this.getClass().getResource("/resources/images/add.png")));
		uiAdd.setFont(APP_FONT);
		uiAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
				LynkTable.this.menuAddAction.add();
			}
		});
		uiPopMenu.add(uiAdd);
	}

	/**
	 * popmenu 删除事件
	 * @param menuDeleteAction
	 */
	public void setMenuDeleteAction(MenuDeleteAction menuDeleteAction) {
		this.menuDeleteAction = menuDeleteAction;
		uiDelete = new JMenuItem(deleteText, new ImageIcon(this.getClass().getResource("/resources/images/disable.png")));
		uiDelete.setFont(APP_FONT);
		uiDelete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
				LynkTable.this.menuDeleteAction.delete(getSelectedRows());
			}
		});
		uiPopMenu.add(uiDelete);
	}
	
	/**
	 * popmenu 还原事件
	 * @param menuRestoreAction
	 */
	public void setMenuRestoreAction(MenuRestoreAction menuRestoreAction) {
		this.menuRestoreAction = menuRestoreAction;
		uiRestore = new JMenuItem(restoreText, new ImageIcon(this.getClass().getResource("/resources/images/enable.png")));
		uiRestore.setFont(APP_FONT);
		uiRestore.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
				LynkTable.this.menuRestoreAction.restore(getSelectedRows());
			}
		});
		uiPopMenu.add(uiRestore);
	}

	private void init() {
		uiPopMenu = new JPopupMenu();
		//全选
		uiSelectAll = new JMenuItem(DEFAULT_SELECT_ALL_TEXT, new ImageIcon(this.getClass().getResource("/resources/images/select-all.png")));
		uiSelectAll.setFont(APP_FONT);
		uiSelectAll.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
				if(getRowCount() == 0) {
					return;
				}
				if(getRowCount() == getSelectedRowCount()) {
					clearSelection();
					return;
				}
				selectAll();
			}
		});
		uiPopMenu.add(uiSelectAll);
		
		JMenu menu = new JMenu("复制");
		menu.setIcon(new ImageIcon(this.getClass().getResource("/resources/images/copy.png")));
		menu.setFont(APP_FONT);
		uiPopMenu.add(menu);
		{
			//复制
			uiCpoy = new JMenuItem(DEFAULT_COPY_TEXT, new ImageIcon(this.getClass().getResource("/resources/images/copy.png")));
			uiCpoy.setFont(APP_FONT);
			uiCpoy.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent evt) {
					if(getSelectedRowCount() == 0 || getSelectedColumnCount() == 0) {
						return;
					}
					copyToClipboard();
				}
			});
			menu.add(uiCpoy);
			
			//复制(含标题)
			uiCpoyWithHead = new JMenuItem(DEFAULT_COPY_HEAD_TEXT, new ImageIcon(this.getClass().getResource("/resources/images/copy.png")));
			uiCpoyWithHead.setFont(APP_FONT);
			uiCpoyWithHead.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent evt) {
					if(getSelectedRowCount() == 0 || getSelectedColumnCount() == 0) {
						return;
					}
					copyToClipboardWithHead();
				}
			});
			menu.add(uiCpoyWithHead);
		}
		
		
		registerKeyboardAction(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equals("Copy")) {
					copyToClipboard();
				}
			}
		}, "Copy", COPY, JComponent.WHEN_FOCUSED);
		
		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent evt) {
				if(SwingUtilities.isLeftMouseButton(evt) && evt.getClickCount() == 2 && mouseDoubleClick != null) {
					int index = rowAtPoint(evt.getPoint());
					if(index != -1) {
						setRowSelectionInterval(index, index);
						setColumnSelectionInterval(0, getColumnCount() - 1);
						mouseDoubleClick.doubleClick(index);
					}
				}
				if(SwingUtilities.isRightMouseButton(evt)) {
					boolean bAdd;
					boolean bDelete;
					boolean bSelectAll;
					
					if(getRowCount() == 0) {
						bAdd = true;
						bDelete = false;
						bSelectAll = false;
					} else {
						Point p = evt.getPoint();
						int rowIndex = rowAtPoint(p);
						int columnIndex = columnAtPoint(p);
						if(rowIndex == -1 && columnIndex == -1) {
							clearSelection();
							bAdd = true;
							bDelete = false;
							bSelectAll = true;
						} else {//pointIndex != -1
							if(!isRowSelected(rowIndex)) {//鼠标处选中
								setRowSelectionInterval(rowIndex, rowIndex);
								setColumnSelectionInterval(columnIndex, columnIndex);
							}
							bAdd = true;
							bDelete = true;
							bSelectAll = true;
						} 
					}
					if(uiSelectAll != null) {
						uiSelectAll.setEnabled(bSelectAll);
					}
					if(uiCpoy != null) {
						uiCpoy.setEnabled(bSelectAll);
					}
					if(uiAdd != null) {
						uiAdd.setEnabled(bAdd);
					}
					if(uiDelete != null) {
						uiDelete.setEnabled(bDelete);
					}
					uiPopMenu.show(LynkTable.this, evt.getX(), evt.getY());
				}
			}
		});
		setTableProperties();
	}
	
	
	/**
	 * 复制内容到剪贴板
	 */
	private void copyToClipboard() {
		StringBuffer sb = new StringBuffer();
		int[] rows = getSelectedRows();
		int[] columns = getSelectedColumns();
		//复制内容
		for (int i = 0; i < rows.length; i++) {
			for (int j = 0; j < columns.length; j++) {
				sb.append(getValueAt(rows[i], columns[j]));
				if (j < columns.length - 1){
					sb.append("\t");
				}
			}
			sb.append("\n");
		}
		StringSelection selection = new StringSelection(sb.toString());
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, selection);
	}
	
	/**
	 * 复制内容到剪贴板(含标题)
	 */
	private void copyToClipboardWithHead() {
		StringBuffer sb = new StringBuffer();
		int[] rows = getSelectedRows();
		int[] columns = getSelectedColumns();
		//复制标题
		for(int i = 0; i < columns.length; i++) {
			String columnName = getColumnName(columns[i]);
			sb.append(columnName);
			if (i < columns.length - 1){
				sb.append("\t");
			}
		}
		sb.append("\n");
		//复制内容
		for (int i = 0; i < rows.length; i++) {
			for (int j = 0; j < columns.length; j++) {
				sb.append(getValueAt(rows[i], columns[j]));
				if (j < columns.length - 1){
					sb.append("\t");
				}
			}
			sb.append("\n");
		}
		StringSelection selection = new StringSelection(sb.toString());
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, selection);
	}
	
	/**
	 * 设置table样式
	 */
	protected void setTableProperties() {
		setAutoResizeMode(AUTO_RESIZE_OFF);
		setColumnSelectionAllowed(true);
		setCellSelectionEnabled(true);
		setRowHeight(24);
		getTableHeader().setFont(APP_FONT);
		getTableHeader().setForeground(Color.BLUE);
		((DefaultTableCellRenderer) getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
		setColumnControlVisible(true);
		setFont(APP_FONT);
		if(initHighLighter) {
			setHighlighters(HighlighterFactory.createAlternateStriping(new Color(255,251,191), new Color(191,255,222), highLighterRowNum));
		}
	}

	public void setRowAlignCenter() {
		addHighlighter(new AlignmentHighlighter(SwingConstants.CENTER));
	}
	
	public interface MouseDoubleClick {
		void doubleClick(int index);
	}
	
	public interface MenuAddAction {
		void add();
	}
	
	public interface MenuDeleteAction {
		void delete(int indexes[]);
	}
	
	public interface MenuRestoreAction {
		void restore(int indexes[]);
	}
}
