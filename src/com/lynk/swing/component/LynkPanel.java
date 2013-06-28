package com.lynk.swing.component;

import java.awt.AWTKeyStroke;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.FocusManager;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class LynkPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public LynkPanel() {
		this(true);
	}

	public LynkPanel(boolean isDoubleBuffered) {
		this(new FlowLayout(), isDoubleBuffered);
	}

	public LynkPanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		Set<AWTKeyStroke> forwordDefaultKeys = getFocusTraversalKeys(FocusManager.FORWARD_TRAVERSAL_KEYS);
		Set<AWTKeyStroke> forwordNewKeys = new HashSet<AWTKeyStroke>(forwordDefaultKeys);
		forwordNewKeys.add(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0));
		setFocusTraversalKeys(FocusManager.FORWARD_TRAVERSAL_KEYS, forwordNewKeys);
	}

	public LynkPanel(LayoutManager layout) {
		this(layout, true);
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		Component[] components = this.getComponents();
		for(Component c: components) {
			c.setEnabled(enabled);
		}
		super.setEnabled(enabled);
	}

	@Override
	public Component add(Component comp) {
		comp.setEnabled(this.isEnabled());
		return super.add(comp);
	}
}
