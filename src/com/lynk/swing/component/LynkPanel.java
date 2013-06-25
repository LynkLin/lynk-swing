package com.lynk.swing.component;

import java.awt.Component;

import javax.swing.JPanel;

public class LynkPanel extends JPanel {
	private static final long serialVersionUID = 1L;

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
