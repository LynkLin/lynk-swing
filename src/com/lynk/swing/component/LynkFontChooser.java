package com.lynk.swing.component;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;

import com.lynk.swing.common.Constants;
import com.lynk.swing.component.LynkSpinner.SpinnerValueChangedListener;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import net.miginfocom.swing.MigLayout;

import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class LynkFontChooser extends JDialog implements Constants {
	private static final long serialVersionUID = 1L;

	
	private Font font;
	private LynkTextField uiFontName;
	private LynkTextField uiFontType;
	private LynkSpinner uiFontSize;
	private JList<String> uiFontNameList;
	private JList<FontType> uiFontTypeList;
	private JList<Integer> uiFontSizeList;
	private JLabel uiFontShow;
	
	private FontType[] fontTypes = new FontType[]{new FontType(Font.PLAIN, "常规"), new FontType(Font.BOLD, "粗体"), new FontType(Font.ITALIC, "斜体"), new FontType(Font.BOLD + Font.ITALIC, "粗体 + 斜体")};
	private Integer[] fontSizes = new Integer[]{6, 8, 9, 10, 11, 12, 14, 16, 18, 20, 22, 24, 26, 28, 36, 48, 72};
	
	public static Font showDialog(Component parent, Font font) {
		LynkFontChooser dialog = new LynkFontChooser(font);
		dialog.setLocationRelativeTo(parent);
		dialog.setVisible(true);
		return dialog.font;
	}

	private LynkFontChooser(Font font) {
		if(font == null) {
			font = new Font(APP_FONT.getName(), APP_FONT.getStyle(), APP_FONT.getSize());
		}
		this.font = new Font(font.getName(), font.getStyle(), font.getSize());
		initComponents(font);
		addListeners();
	}
	
	private void addListeners() {
		uiFontNameList.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent evt) {
				if(!evt.getValueIsAdjusting()) {
					fontChanged(true);
				}
			}
		});
		uiFontTypeList.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent evt) {
				if(!evt.getValueIsAdjusting()) {
					fontChanged(true);
				}
			}
		});
		
		uiFontSize.addValueChangedListener(new SpinnerValueChangedListener() {
			
			@Override
			public void valueChanged() {
				fontChanged(false);
			}
		});
		
		uiFontSizeList.addListSelectionListener(new ListSelectionListener() {
		
			@Override
			public void valueChanged(ListSelectionEvent evt) {
				if(!evt.getValueIsAdjusting()) {
					fontChanged(true);
				}
			}
		});
	}
	
	private void fontChanged(boolean flag) {
		String fontName = uiFontNameList.getSelectedValue();
		uiFontName.setText(fontName);
		
		FontType fontType = uiFontTypeList.getSelectedValue();
		uiFontType.setText(fontType.typeName);
		
		Integer fontSize;
		if(flag) {
			fontSize = uiFontSizeList.getSelectedValue();
			uiFontSize.setValue(fontSize);
		} else {
			fontSize = (Integer) uiFontSize.getValue();
		}
		
		Font font = new Font(fontName, fontType.typeCode, fontSize);
		uiFontShow.setFont(font);
	}
	private void initComponents(Font font) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(LynkFontChooser.class.getResource("/resources/images/fonts.png")));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("字体选择");
		setBounds(100, 100, 639, 557);
		getContentPane().setLayout(new BorderLayout());
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("确认");
				okButton.setFont(APP_FONT);
				okButton.setFocusable(false);
				buttonPane.add(okButton);
				okButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent evt) {
						LynkFontChooser.this.font = uiFontShow.getFont();
						dispose();
					}
				});
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("取消");
				cancelButton.setFont(APP_FONT);
				cancelButton.setFocusable(false);
				cancelButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent evt) {
						dispose();
					}
				});
				buttonPane.add(cancelButton);
			}
		}
		{
			JPanel panel = new JPanel();
			getContentPane().add(panel, BorderLayout.CENTER);
			panel.setLayout(new MigLayout("", "[grow][grow][grow]", "[grow][60px:n]"));
			{
				JPanel pane = new JPanel();
				pane.setBorder(new TitledBorder(null, "\u5B57\u4F53", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				panel.add(pane, "cell 0 0,grow");
				pane.setLayout(new MigLayout("", "[grow]", "[][grow]"));
				{
					uiFontName = new LynkTextField();
					pane.add(uiFontName, "cell 0 0,growx");
					uiFontName.setText(font.getName());
					uiFontName.setEditable(false);
				}
				{
					JScrollPane scrollPane = new JScrollPane();
					pane.add(scrollPane, "cell 0 1,grow");
					{
						uiFontNameList = new JList<String>(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
						uiFontNameList.setFont(APP_FONT);
						uiFontNameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						uiFontNameList.setSelectedValue(font.getName(), true);
						scrollPane.setViewportView(uiFontNameList);
					}
				}
			}
			{
				JPanel pane = new JPanel();
				pane.setBorder(new TitledBorder(null, "\u5B57\u5F62", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				panel.add(pane, "cell 1 0,grow");
				pane.setLayout(new MigLayout("", "[grow]", "[][grow]"));
				{
					uiFontType = new LynkTextField();
					pane.add(uiFontType, "cell 0 0,growx");
					int index = Arrays.binarySearch(fontTypes, new FontType(font.getStyle()));
					if(index > -1) {
						uiFontType.setText(fontTypes[index].typeName);
					}
					uiFontType.setEditable(false);
				}
				{
					JScrollPane scrollPane = new JScrollPane();
					pane.add(scrollPane, "cell 0 1,grow");
					{
						uiFontTypeList = new JList<FontType>(fontTypes);
						uiFontTypeList.setFont(APP_FONT);
						uiFontTypeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						uiFontTypeList.setSelectedValue(new FontType(font.getStyle()), true);
						scrollPane.setViewportView(uiFontTypeList);
					}
				}
			}
			{
				JPanel pane = new JPanel();
				pane.setBorder(new TitledBorder(null, "\u5B57\u53F7", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				panel.add(pane, "cell 2 0,grow");
				pane.setLayout(new MigLayout("", "[grow]", "[][grow]"));
				{
					uiFontSize = new LynkSpinner(new SpinnerNumberModel(1, 1, null, 2));
					pane.add(uiFontSize, "cell 0 0,growx");
					uiFontSize.setValue(font.getSize());
				}
				{
					JScrollPane scrollPane = new JScrollPane();
					pane.add(scrollPane, "cell 0 1,grow");
					{
						uiFontSizeList = new JList<Integer>(fontSizes);
						uiFontSizeList.setFont(APP_FONT);
						uiFontSizeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						if(Arrays.binarySearch(fontSizes, font.getSize()) > -1) {
							uiFontSizeList.setSelectedValue(font.getSize(), true);
						}
						scrollPane.setViewportView(uiFontSizeList);
					}
				}
			}
			{
				JPanel pane = new JPanel();
				pane.setBorder(new TitledBorder(null, "\u9884\u89C8", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				panel.add(pane, "cell 0 1 3 1,grow");
				pane.setLayout(new BorderLayout(0, 0));
				{
					uiFontShow = new JLabel("字体  Font");
					uiFontShow.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
					uiFontShow.setHorizontalAlignment(SwingConstants.CENTER);
					pane.add(uiFontShow, BorderLayout.CENTER);
				}
			}
		}
	}
	
	class FontType implements Comparable<FontType>{
		private Integer typeCode;
		
		private String typeName;
		
		public FontType(Integer typeCode) {
			this.typeCode = typeCode;
		}
		
		public FontType(Integer typeCode, String typeName) {
			this.typeCode = typeCode;
			this.typeName = typeName;
		}
		public Integer getTypeCode() {
			return typeCode;
		}
		public void setTypeCode(Integer typeCode) {
			this.typeCode = typeCode;
		}
		public String getTypeName() {
			return typeName;
		}
		public void setTypeName(String typeName) {
			this.typeName = typeName;
		}
		
		@Override
		public boolean equals(Object obj) {
			if(obj instanceof FontType) {
				return typeCode == ((FontType) obj).typeCode;
			}
			return false;
		}
		@Override
		public String toString() {
			return typeName;
		}

		@Override
		public int compareTo(FontType o) {
			return typeCode.compareTo(o.typeCode);
		}
	}
}
