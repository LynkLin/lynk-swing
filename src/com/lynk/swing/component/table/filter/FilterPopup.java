package com.lynk.swing.component.table.filter;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import net.miginfocom.swing.MigLayout;

import com.jidesoft.swing.CheckBoxList;
import com.lynk.swing.common.Constants;
import com.lynk.swing.component.LynkPanel;
import com.lynk.swing.component.LynkSearchTextField;
import com.lynk.swing.component.table.filter.FilterItem;
import com.lynk.swing.component.table.popup.ResizablePopupMenu;

public class FilterPopup extends ResizablePopupMenu implements Constants, MouseListener {
	private static final long serialVersionUID = 1L;
	
	private LoadFilterThread loadThread;
	
	private int columnIndex = -1;
	private Map<Integer, Dimension> popupSizes = new HashMap<Integer, Dimension>();
	
	private JTable table;
	private TableFilter filter;
	
	private JPanel rootPanel;
	private CardLayout card;
	private LynkSearchTextField uiSearchFiled = new LynkSearchTextField();
	private DefaultListModel<FilterItem> model = new DefaultListModel<>();
	private CheckBoxList uiFilterList = new CheckBoxList();
	
	public TableFilter getFilter() {
		return filter;
	}

	public FilterPopup(boolean resizable, JTable table) {
		super(resizable);
		this.table = table;
		this.filter = new TableFilter(table);
		table.getTableHeader().addMouseListener(this);
		initUi();
	}
	
	public void reset() {
		columnIndex = -1;
		filter.clear();
	}
	
	private void initUi() {
		rootPanel = new JPanel();
		card = new CardLayout();
		rootPanel.setLayout(card);
		add(rootPanel);
		{
			JPanel waitPanel = new JPanel();
			waitPanel.setLayout(new MigLayout(null, "[grow]", "[grow]"));
			rootPanel.add(waitPanel, "wait");
			{
				JLabel waitLabel = new JLabel("正在努力加载中...");
				waitLabel.setFont(APP_FONT.deriveFont(20f));
				waitPanel.add(waitLabel, "cell 0 0,alignx center");
			}
		}
		{
			LynkPanel listPanel = new LynkPanel();
			listPanel.setLayout(new BorderLayout(0, 5));
			listPanel.setBorder( BorderFactory.createEmptyBorder(4, 4, 4, 4));
			rootPanel.add(listPanel, "list");
			{
				JPanel panel = new JPanel();
				panel.setLayout(new BorderLayout());
				listPanel.add(panel, BorderLayout.NORTH);
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
							for(int i = 0; i< items.size(); i++) {
								FilterItem item = items.get(i);
								String value;
								if(item.getObj() instanceof Number) {
									value = new BigDecimal(((Number) item.getObj()).doubleValue()).toString();
								} else {
									value = item.getObj().toString();
								}
								if(value.toLowerCase().startsWith(uiSearchFiled.getText().toLowerCase())) {
									uiFilterList.setSelectedIndex(i);
									uiFilterList.ensureIndexIsVisible(i);
									uiFilterList.repaint();
									return;
								}
							}
							
						}
					});
					panel.add(uiSearchFiled, BorderLayout.NORTH);
				}
				{
					JPanel buttonPanel = new JPanel();
					buttonPanel.setLayout(new MigLayout("insets 0", "[grow]30[grow]", "3[]"));
					{
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
						buttonPanel.add(uiSelectAll, "cell 0 0,grow");
					}
					{
						JButton uiSelectNone = new JButton("全消", new ImageIcon(getClass().getResource("/resources/images/select-all.png")));
						uiSelectNone.setFocusable(false);
						uiSelectNone.setMargin(new Insets(0, 0, 0, 0));
						uiSelectNone.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								selectNone();
							}
						});
						uiSelectNone.setFont(APP_FONT);
						buttonPanel.add(uiSelectNone, "cell 1 0,grow");
					}
					panel.add(buttonPanel, BorderLayout.SOUTH);
				}
			}
			{
				JScrollPane scrollPane = new JScrollPane();
				uiFilterList.setFont(APP_FONT);
				scrollPane.setViewportView(uiFilterList);
				listPanel.add(scrollPane, BorderLayout.CENTER);
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
				
//				toolBar.addSeparator();
				
//				JButton uiSelectAll = new JButton("全选", new ImageIcon(getClass().getResource("/resources/images/select-all.png")));
//				uiSelectAll.setFocusable(false);
//				uiSelectAll.setMargin(new Insets(0, 0, 0, 0));
//				uiSelectAll.addActionListener(new ActionListener() {
//					
//					@Override
//					public void actionPerformed(ActionEvent e) {
//						selectAll();
//					}
//				});
//				uiSelectAll.setFont(APP_FONT);
//				toolBar.add(uiSelectAll);
				
				listPanel.add(toolBar, BorderLayout.SOUTH);
			}
		}
	}
	
	public void refreshUiFilterList() {
		if(loadThread != null && !loadThread.isDone()) {
			loadThread.cancel(true);
		}
		card.show(rootPanel, "wait");
		loadThread = new LoadFilterThread();
		loadThread.execute();
	}
	
	/**
	 * 全选和全消
	 */
	private void selectAll() {
//		if(uiFilterList.getCheckBoxListSelectedIndices().length == uiFilterList.getModel().getSize()) {
//			uiFilterList.clearCheckBoxListSelection();
//		} else {
//			
//		}
		uiFilterList.selectAll();
	}
	
	/**
	 * 全选和全消
	 */
	private void selectNone() {
		uiFilterList.clearCheckBoxListSelection();
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
	
	class LoadFilterThread extends SwingWorker<List<FilterItem>, FilterItem> {

		@Override
		protected List<FilterItem> doInBackground() throws Exception {
			model.clear();
			if(columnIndex == -1) {
				return null;
			}
//			if(filter.get(columnIndex) == null) {
//				filter.setFilter(columnIndex);
//			}
			if(!filter.isFilted(columnIndex)) {
				filter.setFilter(columnIndex);
			}
			for(FilterItem item: filter.get(columnIndex)) {
				publish(item);
			}
			return null;
		}

		@Override
		protected void process(List<FilterItem> items) {
			for(FilterItem item: items) {
				model.addElement(item);
			}
		}

		@Override
		protected void done() {
			try {
				get();
				uiFilterList.setModel(model);
				uiFilterList.clearCheckBoxListSelection();
				for (int i = 0; i < filter.get(columnIndex).size(); i++) {
					FilterItem item = filter.get(columnIndex).get(i);
					if(item.isSelected()) {
						uiFilterList.addCheckBoxListSelectedIndex(i);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			card.show(rootPanel, "list");
		}
	}

	@Override
	public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
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
		JTableHeader header = table.getTableHeader();
		TableColumnModel cModel = table.getColumnModel();
		
		int columnIndex = cModel.getColumnIndexAtX(e.getX());
		if(columnIndex < 0) {
			return;
		}
		columnIndex = table.convertColumnIndexToModel(columnIndex);
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
