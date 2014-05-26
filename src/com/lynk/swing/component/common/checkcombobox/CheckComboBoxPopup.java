package com.lynk.swing.component.common.checkcombobox;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.PopupMenuEvent;

import net.miginfocom.swing.MigLayout;

import com.jidesoft.swing.CheckBoxList;
import com.lynk.swing.common.Constants;
import com.lynk.swing.component.LynkPanel;
import com.lynk.swing.component.LynkSearchTextField;
import com.lynk.swing.component.table.popup.ResizablePopupMenu;

/**
 * 下拉多选控件的弹出框
 * @author Lynk
 *
 */
public class CheckComboBoxPopup<E> extends ResizablePopupMenu implements Constants {
	private static final long serialVersionUID = 1L;

	private Dimension popSize;
	private LynkSearchTextField uiSearchFiled;
	private DefaultListModel<CheckStore<E>> model;
	private CheckBoxList uiCheckBoxList;
	
	private JTextField textFieldShowed;
	private String seperate;
	
	public CheckComboBoxPopup(List<E> values, JTextField textFieldShowed, String seperate) {
		super();
		this.textFieldShowed = textFieldShowed;
		this.seperate = seperate;
		initComponents(values);
	}
	
	public void initComponents(List<E> values) {
		JPanel rootPanel = new JPanel(new BorderLayout());
		add(rootPanel);
		{
			LynkPanel listPanel = new LynkPanel();
			listPanel.setLayout(new BorderLayout(0, 5));
			listPanel.setBorder( BorderFactory.createEmptyBorder(4, 4, 4, 4));
			rootPanel.add(listPanel, BorderLayout.CENTER);
			{
				JPanel panel = new JPanel();
				panel.setLayout(new BorderLayout());
				listPanel.add(panel, BorderLayout.NORTH);
				{
					uiSearchFiled = new LynkSearchTextField();
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
				model = new DefaultListModel<>();
				for(E value: values) {
					model.addElement(new CheckStore<>(value));
				}
				uiCheckBoxList = new CheckBoxList(model);
				uiCheckBoxList.setFont(APP_FONT);
				selectNone();
				scrollPane.setViewportView(uiCheckBoxList);
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
				listPanel.add(toolBar, BorderLayout.SOUTH);
			}
		}
	}

	/**
	 * 搜索文本
	 */
	private void search() {
		String search = uiSearchFiled.getText().toLowerCase();
		if(search.length() == 0) {
			return;
		}
		for(int i = 0; i < model.getSize(); i++) {
			CheckStore<E> store = model.get(i);
			if(store.getValue().toString().toLowerCase().startsWith(search)) {
				uiCheckBoxList.setSelectedIndex(i);
				uiCheckBoxList.ensureIndexIsVisible(i);
				uiCheckBoxList.repaint();
				break;
			}
		}
	}
	
	/**
	 * 全选和全消
	 */
	private void selectAll() {
		uiCheckBoxList.selectAll();
	}
	
	/**
	 * 全选和全消
	 */
	private void selectNone() {
		uiCheckBoxList.clearCheckBoxListSelection();
	}
	
	private void applyFilter() {
		int[] indexes = uiCheckBoxList.getCheckBoxListSelectedIndices();
		
		List<Integer> indexs = new ArrayList<>(indexes.length);
		String value = "";
		for(int index: indexes) {
			indexs.add(index);
			value = value + model.get(index).getValue().toString() + seperate;
		}
		for(int i = 0; i < model.getSize(); i++) {
			CheckStore<E> store = model.get(i);
			store.setSelected(indexs.contains(i));
		}
		if(value.length() > 0) {
			textFieldShowed.setText(value.substring(0, value.length()- seperate.length()));
		} else {
			textFieldShowed.setText("");
		}
		
		setVisible(false);
	}
	
	private void cancelFilter() {
		setVisible(false);
	}
	
	@Override
	public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
		//刷新选中状态
		selectNone();
		for(int i = 0; i < model.size(); i++) {
			CheckStore<E> store = model.get(i);
			if(store.isSelected()) {
				uiCheckBoxList.addCheckBoxListSelectedIndex(i);
			}
		}
		//大小
		if(popSize != null && popSize.height != 0 && popSize.width != 0) {
			setPreferredSize(popSize);
		} else {
			setPreferredSize(new Dimension(textFieldShowed.getWidth(), 350));
		}
		//清空搜索框
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
		popSize = getPreferredSize();
	}
	
	public List<E> getValues() {
		List<E> values = new ArrayList<>();
		for(int i = 0; i < model.size(); i++) {
			CheckStore<E> store = model.get(i);
			if(store.isSelected()) {
				values.add(store.getValue());
			}
		}
		return values;
	}
}

