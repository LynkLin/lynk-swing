package com.lynk.swing.component;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

/**
 *
 * @author  Lynkkk
 */
public class ImagePane extends javax.swing.JPanel {
	private static final long serialVersionUID = 1L;

	private Image image;

	public ImagePane(String imageName) {
		InputStream is = null;
		try {
			is = this.getClass().getClassLoader().getResourceAsStream("resource/image/" + imageName);
			image = ImageIO.read(is);
			image.flush();
			repaint();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (image != null) {
			Graphics g1 = g.create();
			g1.drawImage(image, 0, 0, image.getWidth(this),
					image.getHeight(this), this);
			g1.dispose();
		}
	}
}