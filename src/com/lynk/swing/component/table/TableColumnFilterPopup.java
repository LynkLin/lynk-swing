package com.lynk.swing.component.table;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import com.jidesoft.swing.CheckBoxList;
import com.lynk.swing.common.Constants;
import com.lynk.swing.component.LynkPanel;
import com.lynk.swing.component.table.popup.LynkSearchTextField;
import com.lynk.swing.component.table.popup.ResizablePopupMenu;

public class TableColumnFilterPopup extends ResizablePopupMenu implements Constants, MouseListener {
	private static final long serialVersionUID = 1L;
	
	private int columnIndex = -1;

	private Map<Integer, Dimension> popupSizes = new HashMap<Integer, Dimension>();
	
	private TableFilter filter;
	
	private LynkSearchTextField uiSearchFiled = new LynkSearchTextField();
	private DefaultListModel<FilterItem> model = new DefaultListModel<>();
	private CheckBoxList uiFilterList = new CheckBoxList();
	
	public TableFilter getFilter() {
		return filter;
	}

	public TableColumnFilterPopup(boolean resizable, TableFilter filter) {
		super(resizable);
		this.filter = filter;
		filter.getTable().getTableHeader().addMouseListener(this);
	}
	
	public void refreshUiFilterList() {
		model.clear();
		if(columnIndex == -1) {
			return;
		}
		for(FilterItem item: filter.get(columnIndex)) {
			model.addElement(item);
		}
		uiFilterList.setModel(model);
		uiFilterList.clearCheckBoxListSelection();
		for (int i = 0; i < filter.get(columnIndex).size(); i++) {
			FilterItem item = filter.get(columnIndex).get(i);
			if(item.isSelected()) {
				uiFilterList.addCheckBoxListSelectedIndex(i);
			}
		}
	}
	
	/**
	 * 全选和全消
	 */
	private void selectAll() {
		if(uiFilterList.getCheckBoxListSelectedIndices().length == uiFilterList.getModel().getSize()) {
			uiFilterList.clearCheckBoxListSelection();
		} else {
			uiFilterList.selectAll();
		}
		
	}

	/**
	 * 应用选择
	 */
	private void applyFilter() {
		int[] indexes = uiFilterList.getCheckBoxListSelectedIndices();
		List<Integer> indexs = new ArrayList<>();
		for(int index: indexes) {
			indexs.add(index);
		}
		for(int i = 0; i < filter.get(columnIndex).size(); i++) {
			FilterItem item = filter.get(columnIndex).get(i);
			item.setSelected(indexs.contains(i));
		}
		filter.execute(columnIndex);
		setVisible(false);
	}
	
	private void cancelFilter() {
		uiFilterList.clearCheckBoxListSelection();
		for(int i = 0; i < filter.get(columnIndex).size(); i++) {
			FilterItem item = filter.get(columnIndex).get(i);
			if(item.isSelected()) {
				uiFilterList.addCheckBoxListSelectedIndex(i);
			}
		}
		filter.execute(columnIndex);
		setVisible(false);
	}

	@Override
	public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
		if(getComponentCount() == 0) {
			LynkPanel panel = new LynkPanel();
			panel.setLayout(new BorderLayout(0, 5));
			panel.setBorder( BorderFactory.createEmptyBorder(4, 4, 4, 4));
			{
				uiSearchFiled.setToolTipText("搜索框");
				uiSearchFiled.getDocument().addDocumentListener(new DocumentListener() {
					
					@Override
					public void removeUpdate(DocumentEvent evt) {
						search();
					}
					
					@Override
					public void insertUpdate(DocumentEvent evt) {
						search();
					}
					
					@Override
					public void changedUpdate(DocumentEvent evt) {
						search();
					}
					
					private void search() {
						List<FilterItem> items = filter.get(columnIndex);
						int index = items.indexOf(new FilterItem(uiSearchFiled.getText()));
						if(index < 0) {
							return;
						}
						uiFilterList.setSelectedIndex(index);
						uiFilterList.ensureIndexIsVisible(index);
						uiFilterList.repaint();
					}
				});
				panel.add(uiSearchFiled, BorderLayout.NORTH);
			}
			{
				JScrollPane scrollPane = new JScrollPane();
				uiFilterList.setFont(APP_FONT);
				scrollPane.setViewportView(uiFilterList);
				panel.add(scrollPane, BorderLayout.CENTER);
			}
			{
				JToolBar toolBar = new JToolBar();
				toolBar.setFloatable(false);
				toolBar.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
				
				JButton uiCancel = new JButton("取消", new ImageIcon(getClass().getResource("/resources/images/disable.png")));
				uiCancel.setFocusable(false);
				uiCancel.setMargin(new Insets(0, 0, 0, 0));
				uiCancel.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent evt) {
						cancelFilter();
					}
				});
				uiCancel.setFont(APP_FONT);
				toolBar.add(uiCancel);
				
				toolBar.addSeparator();
				
				JButton uiApply = new JButton("确认", new ImageIcon(getClass().getResource("/resources/images/enable.png")));
				uiApply.setFocusable(false);
				uiApply.setMargin(new Insets(0, 0, 0, 0));
				uiApply.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						applyFilter();
					}
				});
				uiApply.setFont(APP_FONT);
				toolBar.add(uiApply);
				
				toolBar.addSeparator();
				
				JButton uiSelectAll = new JButton("全选", new ImageIcon(getClass().getResource("/resources/images/select-all.png")));
				uiSelectAll.setFocusable(false);
				uiSelectAll.setMargin(new Insets(0, 0, 0, 0));
				uiSelectAll.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						selectAll();
					}
				});
				uiSelectAll.setFont(APP_FONT);
				toolBar.add(uiSelectAll);
				
				panel.add(toolBar, BorderLayout.SOUTH);
			}
			add(panel);
		}
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				uiSearchFiled.setText("");
				uiSearchFiled.requestFocusInWindow();
			}
		});
	}

	@Override
	public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
		popupSizes.put(columnIndex, getPreferredSize());
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.isPopupTrigger()) {
			showPopup(e);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.isPopupTrigger()) {
			showPopup(e);
		}
	}
	
	private void showPopup(MouseEvent e) {
		JTableHeader header = filter.getTable().getTableHeader();
		TableColumnModel cModel = filter.getTable().getColumnModel();
		
		int columnIndex = cModel.getColumnIndexAtX(e.getX());
		if(columnIndex < 0) {
			return;
		}
		columnIndex = filter.getTable().convertColumnIndexToModel(columnIndex);
		Dimension size = popupSizes.get(columnIndex);
		if(size != null && size.height != 0 && size.width != 0) {
			setPreferredSize(size);
		} else {
			setPreferredSize(new Dimension(250, 350));
		}
		if(this.columnIndex != columnIndex) {
			this.columnIndex = columnIndex;
			refreshUiFilterList();
		}
		show(header, e.getX(), e.getY());
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
	
}
