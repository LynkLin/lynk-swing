package com.lynk.swing.component;

import javax.swing.JDialog;

import java.awt.Color;
import java.awt.Component;
import java.awt.Toolkit;
import javax.swing.JToolBar;
import java.awt.BorderLayout;
import javax.swing.JButton;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.RowSorter;
import javax.swing.SwingWorker;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.filechooser.FileFilter;
import javax.swing.table.TableModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.jidesoft.swing.CheckBoxList;
import com.lynk.swing.common.Constants;

public class LynkTableExportDialog extends LynkDialog implements Constants {
	private static final long serialVersionUID = 1L;
	
	private JFileChooser fileChooser;
	private JTextField uiFilePath;
	private CheckBoxList uiColumnName;
	
	private boolean autoWidth;
	private LynkTable table;

	public static void showDialog(Component parent, LynkTable table) {
		LynkTableExportDialog dialog = new LynkTableExportDialog(table, false);
		dialog.setLocationRelativeTo(parent);
		dialog.setVisible(true);
	}
	
	public static void showDialog(Component parent, LynkTable table, boolean autoWidth) {
		LynkTableExportDialog dialog = new LynkTableExportDialog(table, autoWidth);
		dialog.setLocationRelativeTo(parent);
		dialog.setVisible(true);
	}

	private LynkTableExportDialog(LynkTable table, boolean autoWidth) {
		this.autoWidth = autoWidth;
		this.table = table;
		initComponents();
	}
	private void initComponents() {
		setTitle("导出");
		setIconImage(Toolkit.getDefaultToolkit().getImage(LynkTableExportDialog.class.getResource("/resources/images/export.png")));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 582, 618);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		getContentPane().add(toolBar, BorderLayout.NORTH);
		
		JButton uiSave = new JButton("开始导出");
		uiSave.setFocusable(false);
		uiSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				uiSaveActionPerformed(e);
			}
		});
		uiSave.setIcon(new ImageIcon(LynkTableExportDialog.class.getResource("/resources/images/save.png")));
		uiSave.setFont(APP_FONT);
		toolBar.add(uiSave);
		
		JButton uiSelectAll = new JButton("全选");
		uiSelectAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				uiSelectAllActionPerformed(e);
			}
		});
		uiSelectAll.setIcon(new ImageIcon(LynkTableExportDialog.class.getResource("/resources/images/enable.png")));
		uiSelectAll.setFont(APP_FONT);
		toolBar.add(uiSelectAll);
		
		JButton uiDeselectAll = new JButton("全消");
		uiDeselectAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				uiDeselectAllActionPerformed(e);
			}
		});
		uiDeselectAll.setIcon(new ImageIcon(LynkTableExportDialog.class.getResource("/resources/images/disable.png")));
		uiDeselectAll.setFont(APP_FONT);
		toolBar.add(uiDeselectAll);
		
		fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setFileFilter(new FileFilter() {
			
			@Override
			public String getDescription() {
				return "Excel 2007(*.xlsx)";
			}
			
			@Override
			public boolean accept(File file) {
				if(file.isDirectory()) {
					return true;
				}
				if(file.getName().toLowerCase().endsWith(".xlsx")) {
					return true;
				}
				return false;
			}
		});
		fileChooser.setSelectedFile(new File("导出的资料.xlsx"));
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		
		uiFilePath = new JTextField();
		uiFilePath.setEditable(false);
		uiFilePath.setFont(APP_FONT);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JButton uiChooseFile = new JButton("保存路径");
		uiChooseFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				uiChooseFileActionPerformed(e);
			}
		});
		uiChooseFile.setFont(APP_FONT);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 554, Short.MAX_VALUE)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(uiChooseFile)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(uiFilePath, GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(uiFilePath, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(uiChooseFile))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		DefaultListModel<String> listModel = new DefaultListModel<>();
		TableModel model = table.getModel();
		for(int i = 0; i < model.getColumnCount(); i ++) {
			String columnName = model.getColumnName(i);
			listModel.addElement(columnName);
		}
		uiColumnName = new CheckBoxList(listModel);
		uiColumnName.setFont(APP_FONT);
		scrollPane.setViewportView(uiColumnName);
		uiColumnName.selectAll();
		panel.setLayout(gl_panel);
	}
	
	protected void uiChooseFileActionPerformed(ActionEvent evt) {
		int op = fileChooser.showSaveDialog(this);
		if(op == JFileChooser.APPROVE_OPTION) {
			File saveFile = fileChooser.getSelectedFile();
			uiFilePath.setText(saveFile.getPath());
		}
	}
	
	protected void uiSaveActionPerformed(ActionEvent evt) {
		if(StringUtils.isEmpty(uiFilePath.getText().trim())) {
			showErrorMsg("请选择保存路径!");
			return;
		}
		final int[] exportedColumnIndexes = uiColumnName.getCheckBoxListSelectedIndices();
		if(exportedColumnIndexes.length == 0) {
			showErrorMsg("请至少选择一项!");
			return;
		}
		showWaitPanel("导出中......");
		new SwingWorker<String, String>() {

			@Override
			protected String doInBackground() throws Exception {
				XSSFWorkbook wb = new XSSFWorkbook();
				
				XSSFFont titleFont = wb.createFont();
				titleFont.setBold(true);
				titleFont.setFontName("微软雅黑");
				
				XSSFFont contentFont = wb.createFont();
				contentFont.setBold(false);
				contentFont.setFontName("微软雅黑");
				
				XSSFCellStyle titleStyle = wb.createCellStyle();
				titleStyle.setFont(titleFont);
				titleStyle.setBorderTop(CellStyle.BORDER_THIN);
				titleStyle.setBorderBottom(CellStyle.BORDER_THIN);
				titleStyle.setBorderLeft(CellStyle.BORDER_THIN);
				titleStyle.setBorderRight(CellStyle.BORDER_THIN);
				
				XSSFCellStyle contentStyle = wb.createCellStyle();
				contentStyle.setFont(contentFont);
				contentStyle.setBorderTop(CellStyle.BORDER_THIN);
				contentStyle.setBorderBottom(CellStyle.BORDER_THIN);
				contentStyle.setBorderLeft(CellStyle.BORDER_THIN);
				contentStyle.setBorderRight(CellStyle.BORDER_THIN);
				
				XSSFSheet sheet = wb.createSheet();
				XSSFRow row = null;
				XSSFCell cell = null;
				
				//开始生产表格
				TableModel model = table.getModel();
				//先做表头
				row = sheet.createRow(0);
				for(int columnIndex = 0; columnIndex < exportedColumnIndexes.length; columnIndex++) {
					String columnName = model.getColumnName(exportedColumnIndexes[columnIndex]);
					cell = row.createCell(columnIndex);
					cell.setCellStyle(titleStyle);
					cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(columnName);
				}
				//插入数据
				RowSorter<?> sorter = table.getRowSorter();
				for(int rowIndex = 0; rowIndex < sorter.getViewRowCount(); rowIndex++) {
					row = sheet.createRow(rowIndex + 1);
					for(int columnIndex = 0; columnIndex < exportedColumnIndexes.length; columnIndex++) {
						cell = row.createCell(columnIndex);
						cell.setCellStyle(contentStyle);
						Object value = model.getValueAt(sorter.convertRowIndexToModel(rowIndex), table.convertColumnIndexToModel(exportedColumnIndexes[columnIndex]));
						if(value instanceof Color) {
							cell.setCellType(XSSFCell.CELL_TYPE_STRING);
							cell.setCellValue(((Color) value).getRGB());
						} else {
							cell.setCellType(XSSFCell.CELL_TYPE_STRING);
							cell.setCellValue(value.toString());
						}
					}
					publish("处理中, " + (rowIndex + 1) + "/" + sorter.getViewRowCount());
				}
				
				if(autoWidth) {
					publish("设置列宽......");
					for(short i = 0; i < exportedColumnIndexes.length; i++) {
						sheet.autoSizeColumn(i);
					}
				}
				publish("保存文件......");
				File saveFile = new File(uiFilePath.getText());
				OutputStream os = new FileOutputStream(saveFile);
				wb.write(os);
				os.flush();
				os.close();
				return null;
			}
			
			@Override
			protected void process(List<String> chunks) {
				for(String chunk: chunks) {
					setWaitText(chunk);
				}
			}

			@Override
			protected void done() {
				try {
					get();
					hideWaitPanel();
					showInfoMsg("导出成功!");
				} catch (Exception e) {
					hideWaitPanel();
					showErrorMsg(e);
				}
			}
			
		}.execute();
	}
	
	protected void uiDeselectAllActionPerformed(ActionEvent evt) {
		uiColumnName.selectNone();
	}
	
	protected void uiSelectAllActionPerformed(ActionEvent evt) {
		uiColumnName.selectAll();
	}
}