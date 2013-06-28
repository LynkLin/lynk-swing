package com.lynk.swing.component.table.popup;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import com.lynk.swing.component.LynkTextField;

public class LynkSearchTextField extends LynkTextField {

	private static final long serialVersionUID = 1L;
	
	private static ImageIcon icon;

	private static Image getScaledImage( int size ) {
		
		if (icon == null) {
			icon = new ImageIcon( LynkSearchTextField.class.getResource("/resources/images/search.png"));
		}
		return new ImageIcon(icon.getImage().getScaledInstance( size, size, Image.SCALE_SMOOTH )).getImage();
	}
	
	private static int PAD = 4;
	private static int PAD2 = PAD*2;
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		int size = getHeight()-PAD2;
		g.drawImage( getScaledImage(size), getWidth()-size-PAD, PAD, null);
	}

}
