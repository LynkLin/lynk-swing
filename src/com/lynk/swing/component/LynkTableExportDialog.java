package com.lynk.swing.component;

import javax.swing.JDialog;

import java.awt.Color;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.RowSorter;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

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

import com.lynk.swing.common.Constants;
import com.lynk.swing.util.Utils;

import javax.swing.JCheckBox;
import javax.swing.border.TitledBorder;
import javax.swing.JList;

import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LynkTableExportDialog extends LynkDialog implements Constants {
	private static final long serialVersionUID = 1L;
	
	private JFileChooser fileChooser;
	
	
	private LynkTable table;
	private JCheckBox uiAutoWidth;
	
	private DefaultListModel<ExportedColumn> uiLeftColumnModel;
	private DefaultListModel<ExportedColumn> uiRightColumnModel;
	
	private JList<ExportedColumn> uiLeftColumn;
	private JList<ExportedColumn> uiRightColumn;

	public static void showDialog(Component parent, LynkTable table) {
		LynkTableExportDialog dialog = new LynkTableExportDialog(table);
		dialog.setLocationRelativeTo(parent);
		dialog.setVisible(true);
	}

	private LynkTableExportDialog(LynkTable table) {
		this.table = table;
		initComponents();
	}
	private void initComponents() {
		setResizable(false);
		setTitle("导出");
		setIconImage(Toolkit.getDefaultToolkit().getImage(LynkTableExportDialog.class.getResource("/resources/images/export.png")));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 582, 618);
		
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
		fileChooser.setSelectedFile(new File("HRM导出_" + Utils.getNowStr() + ".xlsx"));
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 43, 254, 533);
		scrollPane.setBorder(new TitledBorder(null, "\u4E0D\u7528\u5BFC\u51FA\u7684\u5217", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JButton uiSave = new JButton("保存");
		uiSave.setFocusable(false);
		uiSave.setBounds(10, 10, 81, 27);
		uiSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				uiSaveActionPerformed(e);
			}
		});
		uiSave.setIcon(new ImageIcon(LynkTableExportDialog.class.getResource("/resources/images/save.png")));
		uiSave.setFont(APP_FONT);
		
		JButton uiSelectAll = new JButton("全选");
		uiSelectAll.setFocusable(false);
		uiSelectAll.setBounds(101, 10, 81, 27);
		uiSelectAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				uiSelectAllActionPerformed(e);
			}
		});
		uiSelectAll.setIcon(new ImageIcon(LynkTableExportDialog.class.getResource("/resources/images/enable.png")));
		uiSelectAll.setFont(APP_FONT);
		
		uiAutoWidth = new JCheckBox("自动调整Excel列宽");
		uiAutoWidth.setFocusable(false);
		uiAutoWidth.setBounds(200, 10, 143, 27);
		uiAutoWidth.setFont(APP_FONT);
		uiAutoWidth.setToolTipText("数据量大时会导致导出缓慢!");
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBorder(new TitledBorder(null, "\u5BFC\u51FA\u7684\u5217", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrollPane_1.setBounds(312, 43, 254, 533);
		
		uiLeftColumnModel = new DefaultListModel<>();
		TableModel model = table.getModel();
		for(int i = 0; i < model.getColumnCount(); i ++) {
			ExportedColumn column = new ExportedColumn(model.getColumnName(i), i);
			uiLeftColumnModel.addElement(column);
		}
		uiLeftColumn = new JList<ExportedColumn>(uiLeftColumnModel);
		uiLeftColumn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				uiColumnNameMouseClicked(e);
			}
		});
		uiLeftColumn.setToolTipText("双击添加");
		uiLeftColumn.setFont(APP_FONT);
		scrollPane.setViewportView(uiLeftColumn);
		panel.setLayout(null);
		panel.add(scrollPane_1);
		
		uiRightColumnModel = new DefaultListModel<>();
		uiRightColumn = new JList<ExportedColumn>(uiRightColumnModel);
		uiRightColumn.setFont(APP_FONT);
		uiRightColumn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				uiExportedColumnMouseClicked(e);
			}
		});
		uiRightColumn.setToolTipText("双击删除");
		scrollPane_1.setViewportView(uiRightColumn);
		panel.add(scrollPane);
		panel.add(uiSave);
		panel.add(uiSelectAll);
		panel.add(uiAutoWidth);
		
		JButton uiToRight = new JButton(">>");
		uiToRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				uiToRightActionPerformed(e);
			}
		});
		uiToRight.setFocusable(false);
		uiToRight.setForeground(Color.BLUE);
		uiToRight.setToolTipText("");
		uiToRight.setFont(APP_FONT);
		uiToRight.setMargin(new Insets(0, 0, 0, 0));
		uiToRight.setBounds(274, 170, 28, 28);
		panel.add(uiToRight);
		
		JButton uiToLeft = new JButton("<<");
		uiToLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				uiToLeftActionPerformed(e);
			}
		});
		uiToLeft.setFocusable(false);
		uiToLeft.setForeground(Color.BLUE);
		uiToLeft.setToolTipText("");
		uiToLeft.setFont(APP_FONT);
		uiToLeft.setMargin(new Insets(0, 0, 0, 0));
		uiToLeft.setBounds(274, 245, 28, 28);
		panel.add(uiToLeft);
	}
	
	protected void uiSaveActionPerformed(ActionEvent evt) {
		int op = fileChooser.showSaveDialog(this);
		if(op != JFileChooser.APPROVE_OPTION) {
			return;
		}
		final File saveFile = fileChooser.getSelectedFile();
		
		if(uiRightColumnModel.size() == 0) {
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
				for(int columnIndex = 0; columnIndex < uiRightColumnModel.size(); columnIndex++) {
					String columnName = uiRightColumnModel.get(columnIndex).getColumnName();
					cell = row.createCell(columnIndex);
					cell.setCellStyle(titleStyle);
					cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(columnName);
				}
				//插入数据
				RowSorter<?> sorter = table.getRowSorter();
				for(int rowIndex = 0; rowIndex < sorter.getViewRowCount(); rowIndex++) {
					row = sheet.createRow(rowIndex + 1);
					for(int columnIndex = 0; columnIndex < uiRightColumnModel.getSize(); columnIndex++) {
						cell = row.createCell(columnIndex);
						cell.setCellStyle(contentStyle);
						Object value = model.getValueAt(sorter.convertRowIndexToModel(rowIndex), uiRightColumnModel.get(columnIndex).getModelIndex());
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
				
				if(uiAutoWidth.isSelected()) {
					publish("设置列宽......");
					for(short i = 0; i < uiRightColumnModel.size(); i++) {
						sheet.autoSizeColumn(i);
					}
				}
				publish("保存文件......");
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
	
	protected void uiSelectAllActionPerformed(ActionEvent evt) {
		if(uiLeftColumnModel.getSize() > 0) {
			uiLeftColumn.setSelectionInterval(0, uiLeftColumnModel.size() - 1);
			moveToRight();
		} else {
			uiRightColumn.setSelectionInterval(0, uiRightColumnModel.size() - 1);
			moveToLeft();
		}
	}
	
	protected void uiToLeftActionPerformed(ActionEvent evt) {
		moveToLeft();
	}
	
	protected void uiToRightActionPerformed(ActionEvent evt) {
		moveToRight();
	}
	
	protected void uiExportedColumnMouseClicked(MouseEvent evt) {
		if(SwingUtilities.isLeftMouseButton(evt) && evt.getClickCount() == 2) {
			moveToLeft();
		}
	}
	
	protected void uiColumnNameMouseClicked(MouseEvent evt) {
		if(SwingUtilities.isLeftMouseButton(evt) && evt.getClickCount() == 2) {
			moveToRight();
		}
	}
	
	private void moveToRight() {
		List<ExportedColumn> columns = uiLeftColumn.getSelectedValuesList();
		for(ExportedColumn column: columns) {
			uiRightColumnModel.addElement(column);
			uiLeftColumnModel.removeElement(column);
		}
	}
	
	private void moveToLeft() {
		List<ExportedColumn> columns = uiRightColumn.getSelectedValuesList();
		for(ExportedColumn column: columns) {
			uiLeftColumnModel.addElement(column);
			uiRightColumnModel.removeElement(column);
		}
	}
	
	class ExportedColumn {
		private String columnName;
		private int modelIndex;
		
		public ExportedColumn(String columnName, int modelIndex) {
			this.columnName = columnName;
			this.modelIndex = modelIndex;
		}

		public String getColumnName() {
			return columnName;
		}
		
		public void setColumnName(String columnName) {
			this.columnName = columnName;
		}
		
		public int getModelIndex() {
			return modelIndex;
		}
		
		public void setModelIndex(int modelIndex) {
			this.modelIndex = modelIndex;
		}

		@Override
		public boolean equals(Object obj) {
			if(obj instanceof ExportedColumn) {
				return ((ExportedColumn) obj).columnName.equals(columnName);
			}
			return false;
		}

		@Override
		public String toString() {
			return columnName;
		}
	}
}
