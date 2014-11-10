package com.lynk.swing.component;

import javax.swing.JFormattedTextField;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import javax.swing.JButton;
import javax.swing.text.JTextComponent;

import com.lynk.swing.common.Constants;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class LynkKeyboard extends JPanel implements Constants {
	private static final long serialVersionUID = 1L;

	private List<JTextComponent> uiSources;
	
	public LynkKeyboard() {
		initComponents();
	}
	
	public void addSource(JTextComponent source) {
		if(uiSources == null) {
			uiSources = new ArrayList<>();
		}
		this.uiSources.add(source);
	}
	
	public void setFontSize(float size) {
		setSubFont(this, APP_FONT_BLOD.deriveFont(size));
	}

	private void setSubFont(Component component, Font font) {
		if(component instanceof JPanel) {
			for(Component c: ((JPanel) component).getComponents()) {
				setSubFont(c, font);
			}
		} else if(component instanceof JButton) {
			component.setFont(font);
		}
	}

	private void initComponents() {
		setLayout(new MigLayout("", "[grow]5[grow]", "[grow]5[grow]5[grow]"));
		{
			JPanel panel = new JPanel();
			add(panel, "cell 1 0 1 3,grow");
			panel.setLayout(new MigLayout("insets 1", "[grow][grow][grow]", "[grow][grow][grow][grow]"));
			{
				{
					JButton button = new JButton("1");
					button.setFocusable(false);
					button.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							buttonActionPerformed(e);
						}
					});
					panel.add(button, "cell 0 0,grow");
				}
				{
					JButton button = new JButton("2");
					button.setFocusable(false);
					button.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							buttonActionPerformed(e);
						}
					});
					panel.add(button, "cell 1 0,grow");
				}
				{
					JButton button = new JButton("3");
					button.setFocusable(false);
					button.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							buttonActionPerformed(e);
						}
					});
					panel.add(button, "cell 2 0,grow");
				}
				{
					JButton button = new JButton("4");
					button.setFocusable(false);
					button.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							buttonActionPerformed(e);
						}
					});
					panel.add(button, "cell 0 1,grow");
				}
				{
					JButton button = new JButton("5");
					button.setFocusable(false);
					button.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							buttonActionPerformed(e);
						}
					});
					panel.add(button, "cell 1 1,grow");
				}
				{
					JButton button = new JButton("6");
					button.setFocusable(false);
					button.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							buttonActionPerformed(e);
						}
					});
					panel.add(button, "cell 2 1,grow");
				}
				{
					JButton button = new JButton("7");
					button.setFocusable(false);
					button.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							buttonActionPerformed(e);
						}
					});
					panel.add(button, "cell 0 2,grow");
				}
				{
					JButton button = new JButton("8");
					button.setFocusable(false);
					button.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							buttonActionPerformed(e);
						}
					});
					panel.add(button, "cell 1 2,grow");
				}
				{
					JButton button = new JButton("9");
					button.setFocusable(false);
					button.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							buttonActionPerformed(e);
						}
					});
					panel.add(button, "cell 2 2,grow");
				}
				{
					JButton button = new JButton("0");
					button.setFocusable(false);
					button.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							buttonActionPerformed(e);
						}
					});
					panel.add(button, "cell 0 3,grow");
				}
			}
			{
				JButton buttonDelete = new JButton("后退");
				buttonDelete.setFocusable(false);
				buttonDelete.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						buttonDeleteActionPerformed(e);
					}
				});
				buttonDelete.setForeground(Color.RED);
				panel.add(buttonDelete, "cell 1 3 2 1,grow");
			}
		}
		{
			JPanel panel = new JPanel();
			add(panel, "cell 0 0,grow");
			panel.setLayout(new MigLayout("insets 1", "[grow][grow][grow][grow][grow][grow][grow][grow][grow][grow]", "[grow]"));
			{
				JButton button = new JButton("Q");
				button.setFocusable(false);
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						buttonActionPerformed(e);
					}
				});
				panel.add(button, "cell 0 0,grow");
			}
			{
				JButton button = new JButton("W");
				button.setFocusable(false);
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						buttonActionPerformed(e);
					}
				});
				panel.add(button, "cell 1 0,grow");
			}
			{
				JButton button = new JButton("E");
				button.setFocusable(false);
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						buttonActionPerformed(e);
					}
				});
				panel.add(button, "cell 2 0,grow");
			}
			{
				JButton button = new JButton("R");
				button.setFocusable(false);
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						buttonActionPerformed(e);
					}
				});
				panel.add(button, "cell 3 0,grow");
			}
			{
				JButton button = new JButton("T");
				button.setFocusable(false);
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						buttonActionPerformed(e);
					}
				});
				panel.add(button, "cell 4 0,grow");
			}
			{
				JButton button = new JButton("Y");
				button.setFocusable(false);
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						buttonActionPerformed(e);
					}
				});
				panel.add(button, "cell 5 0,grow");
			}
			{
				JButton button = new JButton("U");
				button.setFocusable(false);
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						buttonActionPerformed(e);
					}
				});
				panel.add(button, "cell 6 0,grow");
			}
			{
				JButton button = new JButton("I");
				button.setFocusable(false);
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						buttonActionPerformed(e);
					}
				});
				panel.add(button, "cell 7 0,grow");
			}
			{
				JButton button = new JButton("O");
				button.setFocusable(false);
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						buttonActionPerformed(e);
					}
				});
				panel.add(button, "cell 8 0,grow");
			}
			{
				JButton button = new JButton("P");
				button.setFocusable(false);
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						buttonActionPerformed(e);
					}
				});
				panel.add(button, "cell 9 0,grow");
			}
		}
		{
			JPanel panel = new JPanel();
			add(panel, "cell 0 1,grow");
			panel.setLayout(new MigLayout("insets 1", "[grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow]", "[grow]"));
			{
				JButton button = new JButton("A");
				button.setFocusable(false);
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						buttonActionPerformed(e);
					}
				});
				panel.add(button, "cell 1 0,grow");
			}
			{
				JButton button = new JButton("S");
				button.setFocusable(false);
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						buttonActionPerformed(e);
					}
				});
				panel.add(button, "cell 2 0,grow");
			}
			{
				JButton button = new JButton("D");
				button.setFocusable(false);
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						buttonActionPerformed(e);
					}
				});
				panel.add(button, "cell 3 0,grow");
			}
			{
				JButton button = new JButton("F");
				button.setFocusable(false);
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						buttonActionPerformed(e);
					}
				});
				panel.add(button, "cell 4 0,grow");
			}
			{
				JButton button = new JButton("G");
				button.setFocusable(false);
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						buttonActionPerformed(e);
					}
				});
				panel.add(button, "cell 5 0,grow");
			}
			{
				JButton button = new JButton("H");
				button.setFocusable(false);
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						buttonActionPerformed(e);
					}
				});
				panel.add(button, "cell 6 0,grow");
			}
			{
				JButton button = new JButton("J");
				button.setFocusable(false);
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						buttonActionPerformed(e);
					}
				});
				panel.add(button, "cell 7 0,grow");
			}
			{
				JButton button = new JButton("K");
				button.setFocusable(false);
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						buttonActionPerformed(e);
					}
				});
				panel.add(button, "cell 8 0,grow");
			}
			{
				JButton button = new JButton("L");
				button.setFocusable(false);
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						buttonActionPerformed(e);
					}
				});
				panel.add(button, "cell 9 0,grow");
			}
		}
		{
			JPanel panel = new JPanel();
			add(panel, "cell 0 2,grow");
			panel.setLayout(new MigLayout("insets 1", "[grow][grow][grow][grow][grow][grow][grow][grow][grow][grow]", "[grow]"));
			{
				JButton button = new JButton("Z");
				button.setFocusable(false);
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						buttonActionPerformed(e);
					}
				});
				panel.add(button, "cell 1 0,grow");
			}
			{
				JButton button = new JButton("X");
				button.setFocusable(false);
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						buttonActionPerformed(e);
					}
				});
				panel.add(button, "cell 2 0,grow");
			}
			{
				JButton button = new JButton("C");
				button.setFocusable(false);
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						buttonActionPerformed(e);
					}
				});
				panel.add(button, "cell 3 0,grow");
			}
			{
				JButton button = new JButton("V");
				button.setFocusable(false);
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						buttonActionPerformed(e);
					}
				});
				panel.add(button, "cell 4 0,grow");
			}
			{
				JButton button = new JButton("B");
				button.setFocusable(false);
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						buttonActionPerformed(e);
					}
				});
				panel.add(button, "cell 5 0,grow");
			}
			{
				JButton button = new JButton("N");
				button.setFocusable(false);
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						buttonActionPerformed(e);
					}
				});
				panel.add(button, "cell 6 0,grow");
			}
			{
				JButton button = new JButton("M");
				button.setFocusable(false);
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						buttonActionPerformed(e);
					}
				});
				panel.add(button, "cell 7 0,grow");
			}
			{
				JButton button = new JButton("-");
				button.setFocusable(false);
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						buttonActionPerformed(e);
					}
				});
				panel.add(button, "cell 8 0,grow");
			}
		}
	}
	
	protected void buttonActionPerformed(ActionEvent evt) {
		for(JTextComponent uiSource: uiSources) {
			if(uiSource.isFocusOwner()) {
				String text = uiSource.getText().trim();
				JButton button = (JButton) evt.getSource();
				String key = button.getText();
				if(uiSource instanceof JFormattedTextField && (key.compareTo("9") > 0 || key.compareTo("0") < 0)) {
					break;
				}
				uiSource.setText(text + key);
				if(uiSource instanceof JFormattedTextField) {
					try {
						((JFormattedTextField) uiSource).commitEdit();
						((JFormattedTextField) uiSource).setValue(((JFormattedTextField) uiSource).getValue());
					} catch (ParseException e) {
						((JFormattedTextField) uiSource).setValue(0);
					}
				}
				break;
			}
		}
	}
	
	protected void buttonDeleteActionPerformed(ActionEvent evt) {
		for(JTextComponent uiSource: uiSources) {
			if(uiSource.isFocusOwner()) {
				String text = uiSource.getText().trim();
				if(text.length() != 0) {
					uiSource.setText(text.substring(0, text.length() - 1));
					if(uiSource instanceof JFormattedTextField) {
						try {
							((JFormattedTextField) uiSource).commitEdit();
							((JFormattedTextField) uiSource).setValue(((JFormattedTextField) uiSource).getValue());
						} catch (ParseException e) {
							((JFormattedTextField) uiSource).setValue(0);
						}
					}
				}
				break;
			}
		}
	}
}
