package com.lynk.swing.component;

import java.awt.Component;
import java.awt.Toolkit;

import javax.swing.JFrame;

import com.lynk.swing.component.LynkDialog;
import com.lynk.swing.component.report.LynkReportView;
import com.lynk.swing.util.LynkSwingUtil;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;

public class LynkReportDialog extends LynkDialog {
	private static final long serialVersionUID = 1L;
	
	public static void showDialog(Component parent, JasperPrint jasperPrint) {
		LynkReportDialog dialog = new LynkReportDialog(jasperPrint, true);
//		dialog.setLocationRelativeTo(parent);
		dialog.setVisible(true);
	}
	
	public static void showDialog(Component parent, JasperPrint jasperPrint, boolean showSaveButton) {
		LynkReportDialog dialog = new LynkReportDialog(jasperPrint, showSaveButton);
		dialog.setLocationRelativeTo(parent);
		dialog.setVisible(true);
	}

	private LynkReportDialog(JasperPrint jasperPrint, boolean showSaveButton) {
		initComponents(jasperPrint, showSaveButton);
	}
	private void initComponents(JasperPrint jasperPrint, boolean showSaveButton) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage(LynkReportDialog.class.getResource("/resource/image/icon.png")));
		setLocation(LynkSwingUtil.getMaxDialogCenterPoint());
		setSize(LynkSwingUtil.getMaxDialogSize());
		if(showSaveButton) {
			add(new JRViewer(jasperPrint));
		} else {
			add(new LynkReportView(this, jasperPrint));
		}
	}
}
