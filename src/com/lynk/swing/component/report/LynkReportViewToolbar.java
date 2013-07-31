package com.lynk.swing.component.report;

import net.sf.jasperreports.swing.JRViewerController;
import net.sf.jasperreports.swing.JRViewerToolbar;

public class LynkReportViewToolbar extends JRViewerToolbar {
	private static final long serialVersionUID = 1L;

	public LynkReportViewToolbar(JRViewerController viewerContext) {
		super(viewerContext);
		btnSave.setVisible(false);
	}
}
