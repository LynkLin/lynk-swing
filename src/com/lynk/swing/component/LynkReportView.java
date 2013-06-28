package com.lynk.swing.component;

import java.awt.Component;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;
import net.sf.jasperreports.swing.JRViewerToolbar;

public class LynkReportView extends JRViewer {
	private static final long serialVersionUID = 1L;

	public LynkReportView(Component parent, JasperPrint jrPrint) {
		super(jrPrint);
	}

	@Override
	protected JRViewerToolbar createToolbar() {
		return new LynkReportViewToolbar(viewerContext);
	}
}
