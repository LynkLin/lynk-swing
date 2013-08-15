package com.lynk.swing.component;

import java.awt.Graphics;
import java.awt.Image;
import java.io.FileInputStream;
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

	public ImagePane() {
		
	}
	
	/**
	 * 
	 * @param imagePath, [injar: resource/image/xxx.png], [not injar: temp/xxx.png]
	 * @param inJar
	 */
	public ImagePane(String imagePath, boolean inJar) {
		InputStream is = null;
		try {
			if(inJar) {
				is = this.getClass().getClassLoader().getResourceAsStream(imagePath);
			} else {
				is = new FileInputStream(System.getProperty("user.dir") + imagePath);
			}
			
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
	
	/**
	 * 
	 * @param imagePath , [injar: resource/image/xxx.png], [not injar: temp/xxx.png]
	 * @param inJar
	 */
	public void setImage(String imagePath, boolean inJar) {
		InputStream is = null;
		try {
			if(inJar) {
				is = this.getClass().getClassLoader().getResourceAsStream(imagePath);
			} else {
				is = new FileInputStream(System.getProperty("user.dir") + imagePath);
			}
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
	
	/**
	 * 
	 * @param imagePath 绝对路径
	 */
	public void setImage(String imagePath) {
		InputStream is = null;
		try {
			is = new FileInputStream(imagePath);
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