package com.lynk.swing.component.table;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTable;

import org.jdesktop.swingx.JXTable;

public class LynkSummaryTable extends JXTable {
	private static final long serialVersionUID = 1L;

	private final String COMBO_SUM = "合计";
	private final String COMBO_AVG = "平均值";
	private final String COMBO_COUNT = "计数";
	
	@SuppressWarnings("unused")
	private JTable dataTable;

	public LynkSummaryTable(JTable dataTable) {
		super(new LynkSummaryRowModel(dataTable));
		this.dataTable = dataTable;
		initUi();
		initCellRenderer();
	}
	
	private void initUi() {
		setAutoResizeMode(AUTO_RESIZE_OFF);
		
	}
	
	private void initCellRenderer() {
		JComboBox<String> comboBox = new JComboBox<>(new DefaultComboBoxModel<String>(new String[]{COMBO_SUM, COMBO_AVG, COMBO_COUNT}));
		for(int i = 0; i < getColumnModel().getColumnCount(); i++) {
			getColumnModel().getColumn(i).setCellEditor(new DefaultCellEditor(comboBox));
		}
	}
}
