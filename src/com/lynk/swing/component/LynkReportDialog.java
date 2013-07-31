package com.lynk.swing.component;

import java.awt.Component;
import java.awt.Toolkit;

import javax.swing.JFrame;

import com.lynk.swing.component.LynkDialog;
import com.lynk.swing.component.report.LynkReportView;
import com.lynk.swing.util.LynkSwingUtil;

import net.sf.jasperreports.engine.JasperPrint;

public class LynkReportDialog extends LynkDialog {
	private static final long serialVersionUID = 1L;
	
	public static void showDialog(Component parent, JasperPrint jasperPrint) {
		LynkReportDialog dialog = new LynkReportDialog(jasperPrint);
		dialog.setLocationRelativeTo(parent);
		dialog.setVisible(true);
	}

	private LynkReportDialog(JasperPrint jasperPrint) {
		initComponents(jasperPrint);
	}
	private void initComponents(JasperPrint jasperPrint) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage(LynkReportDialog.class.getResource("/resource/image/icon.png")));
		setSize(LynkSwingUtil.getMaxDialogSize());
		add(new LynkReportView(this, jasperPrint));
	}
}
