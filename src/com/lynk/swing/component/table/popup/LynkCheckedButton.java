package com.lynk.swing.component.table.popup;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicButtonUI;

/**
 * This class provides a JCheckBoxMenuItrem Functionality, optionally working like a JMenuItem, primarily used with
 * LynkPopupMenu. Rationale for development of this component was the inability of a JMenuItem to work in a Scrollable
 * Popup menu as in LynkPopupMenu
 * @author balajihe
 *
 */
public class LynkCheckedButton extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//	Icon to be used to for the Checked Icon of the Button
	private static ImageIcon	checkedIcon;
	public static final Icon EMPTY_IMAGE_ICON = new ImageIcon(LynkCheckedButton.class.getResource("/resources/images/menu_spacer.gif"));

	/**
	 * These colors are required in order to simulate the JMenuItem's L&F
	 */
	public static final Color	MENU_HIGHLIGHT_BG_COLOR	= UIManager.getColor("MenuItem.selectionBackground");
	public static final Color	MENU_HIGHLIGHT_FG_COLOR	= UIManager.getColor("MenuItem.selectionForeground");
	public static final Color	MENUITEM_BG_COLOR		= UIManager.getColor("MenuItem.background");
	public static final Color	MENUITEM_FG_COLOR		= UIManager.getColor("MenuItem.foreground");

	//  This property if set to false, will result in the checked Icon not being displayed when the button is selected
	private boolean				displayCheck			= true;

	public LynkCheckedButton() {
		super();
		init();

	}

	public LynkCheckedButton(Action a) {
		super(a);
		init();
	}

	public LynkCheckedButton(Icon icon) {
		super(icon);
		init();
	}

	public LynkCheckedButton(String text, Icon icon) {
		super(text, icon);
		init();
	}

	public LynkCheckedButton(String text) {
		super(text);
		init();
	}

	/**
	 * Initialize component LAF and add Listeners
	 */
	private void init() {
		MouseAdapter mouseAdapter = getMouseAdapter();

		//	Basically JGoodies LAF UI for JButton does not allow Background color to be set.
		// So we need to set the default UI,        
		ComponentUI ui = BasicButtonUI.createUI(this);
		this.setUI(ui);
		setBorder(BorderFactory.createEmptyBorder(3, 0, 3, 2));
		setMenuItemDefaultColors();
		//        setContentAreaFilled(false);
		setHorizontalTextPosition(SwingConstants.RIGHT);
		setHorizontalAlignment(SwingConstants.LEFT);
		//        setModel(new JToggleButton.ToggleButtonModel());
		setModel(new LynkCheckedButtonModel());
		setSelected(false);
		this.addMouseListener(mouseAdapter);

	}

	private void setMenuItemDefaultColors() {
		LynkCheckedButton.this.setBackground(MENUITEM_BG_COLOR);
		LynkCheckedButton.this.setForeground(MENUITEM_FG_COLOR);
	}

	/**
	 * @return
	 */
	private MouseAdapter getMouseAdapter() {
		return new MouseAdapter() {
			/*
			 * For static menuitems, the background color remains the highlighted color, if this is not overridden
			 */
			public void mousePressed(MouseEvent e) {
				setMenuItemDefaultColors();
			}

			public void mouseEntered(MouseEvent e) {
				LynkCheckedButton.this.setBackground(MENU_HIGHLIGHT_BG_COLOR);
				LynkCheckedButton.this.setForeground(MENU_HIGHLIGHT_FG_COLOR);
			}

			public void mouseExited(MouseEvent e) {
				setMenuItemDefaultColors();
			}

		};
	}

	/**
	 * @param checkedFlag
	 */
	public void displayIcon(boolean checkedFlag) {
		//		logger.debug("displayIcon checkedFlag b :"+checkedFlag+"\tButton Text :"+getText());
		if (checkedFlag && isDisplayCheck()) {
			if (checkedIcon == null) {
				checkedIcon = new ImageIcon(this.getClass().getResource("/resources/images/check.gif"));
			}
			this.setIcon(checkedIcon);
		} else {
			this.setIcon(EMPTY_IMAGE_ICON);
		}
		this.repaint();
	}

	private class LynkCheckedButtonModel extends JToggleButton.ToggleButtonModel {
		private static final long serialVersionUID = 1L;

		/*
		 * Need to Override keeping the super code, else the check mark won't come  
		 */
		public void setSelected(boolean b) {

			ButtonGroup group = getGroup();
			if (group != null) {
				// use the group model instead
				group.setSelected(this, b);
				b = group.isSelected(this);
			}

			if (isSelected() == b) {
				return;
			}

			if (b) {
				stateMask |= SELECTED;
			} else {
				stateMask &= ~SELECTED;
			}

			//			 Send ChangeEvent
			fireStateChanged();

			// Send ItemEvent
			fireItemStateChanged(new ItemEvent(this, ItemEvent.ITEM_STATE_CHANGED, this,
					this.isSelected() ? ItemEvent.SELECTED : ItemEvent.DESELECTED));

			LynkCheckedButton.this.displayIcon(b);

		}

	}

	/**
	 * Returns true if Button will display Checked Icon on Click. Default Behaviour is to display a Checked Icon
	 * @return
	 */
	public boolean isDisplayCheck() {
		return displayCheck;
	}

	/**
	 * Sets the property which determines whether a checked Icon should be displayed or not
	 * Setting to false, makes this button display like a normal button 
	 * @param displayCheck
	 */
	public void setDisplayCheck(boolean displayCheck) {
		this.displayCheck = displayCheck;
	}

}
