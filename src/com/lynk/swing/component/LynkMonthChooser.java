package com.lynk.swing.component;

import java.util.Calendar;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import org.apache.commons.lang.StringUtils;

import com.lynk.swing.common.Constants;

import net.miginfocom.swing.MigLayout;

public class LynkMonthChooser extends JPanel implements Constants {
	private static final long serialVersionUID = 1L;

	private JSpinner uiYear;
	private JComboBox<String> uiMonth;
	
	public LynkMonthChooser() {
		initComponents();
	}
	private void initComponents() {
		setLayout(new MigLayout("insets 0", "[grow][][grow][]", "[grow]"));
		{
			SpinnerNumberModel model = new SpinnerNumberModel(Calendar.getInstance().get(Calendar.YEAR), 1900, 9999, 1);
			uiYear = new JSpinner(model);
			JSpinner.NumberEditor ne_uiYear = new JSpinner.NumberEditor(uiYear, "####");
			uiYear.setEditor(ne_uiYear);
			uiYear.setFont(APP_FONT);
			add(uiYear, "cell 0 0,grow");
		}
		{
			JLabel label = new JLabel("年");
			label.setFont(APP_FONT);
			add(label, "cell 1 0,growy");
		}
		{
			uiMonth = new JComboBox<String>(new DefaultComboBoxModel<String>(new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
			uiMonth.setFont(APP_FONT);
			uiMonth.setFocusable(false);
			uiMonth.setMaximumRowCount(12);
			uiMonth.setSelectedItem(StringUtils.leftPad(Integer.toString(Calendar.getInstance().get(Calendar.MONTH) + 1), 2, "0"));
			add(uiMonth, "cell 2 0,grow");
		}
		{
			JLabel label = new JLabel("月");
			label.setFont(APP_FONT);
			add(label, "cell 3 0,growy");
		}
	}
	
	public String getMonth() {
		return uiYear.getValue().toString() + "-" + uiMonth.getSelectedItem().toString();
	}
}
