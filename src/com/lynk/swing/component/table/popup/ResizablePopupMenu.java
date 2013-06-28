package com.lynk.swing.component.table.popup;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

public class ResizablePopupMenu extends JPopupMenu implements PopupMenuListener {

	private static final long serialVersionUID = 1L;

	private static final int DOT_SIZE = 2;
	private static final int DOT_START = 2;
	private static final int DOT_STEP = 4;

	private final boolean resizable;

	public ResizablePopupMenu( boolean resizable ) {
		super();
		this.resizable = resizable;
		if ( resizable ) PopupMenuResizer.decorate(this);
		addPopupMenuListener(this);
	}

	@Override
	public void popupMenuWillBecomeVisible(PopupMenuEvent e) {}

	@Override
	public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {}

	@Override
	public  void popupMenuCanceled(PopupMenuEvent e) {}

	@Override
	public void paintChildren(Graphics g) {
		super.paintChildren(g);
		if ( resizable ) drawResizer(g);
	}

	private void drawResizer(Graphics g) {

		int x = getWidth()-2;
		int y = getHeight()-2;

		Graphics g2 = g.create();
		
		try {
			for ( int dy = DOT_START, j = 2; j > 0; j--, dy += DOT_STEP ) {
				for( int dx = DOT_START, i = 0; i < j; i++, dx += DOT_STEP ) {
					drawDot( g2, x-dx, y-dy );
				}
			}
		} finally {
			g2.dispose();
		}

	};

	private void drawDot( Graphics g, int x, int y) {
		g.setColor(Color.WHITE);
		g.fillRect( x, y, DOT_SIZE, DOT_SIZE);
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect( x-1, y-1, DOT_SIZE, DOT_SIZE);
	}

}

/**
 * Allows to resize popup with the mouse.
 *
 * Created on Aug 6, 2010
 * @author exr0bs5
 *
 */
final class PopupMenuResizer extends MouseAdapter {

	private final JPopupMenu menu;

	private static final int REZSIZE_SPOT_SIZE = 10;

	private Point mouseStart = new Point( Integer.MIN_VALUE, Integer.MIN_VALUE );

	private Dimension startSize;

	private boolean isResizing = false;


	public static void decorate( JPopupMenu menu ) {
		new PopupMenuResizer( menu );
	}

	private PopupMenuResizer( JPopupMenu menu ) {
		this.menu = menu;
		this.menu.setLightWeightPopupEnabled(true);
		menu.addMouseListener(this);
		menu.addMouseMotionListener(this);
	}

	private boolean isInResizeSpot( Point point ) {

		if ( point == null ) return false;

		Rectangle resizeSpot = new Rectangle(
			menu.getWidth()-REZSIZE_SPOT_SIZE,
			menu.getHeight()-REZSIZE_SPOT_SIZE,
			REZSIZE_SPOT_SIZE,
			REZSIZE_SPOT_SIZE );

		return resizeSpot.contains(point);

	}

	@Override
	public void mouseMoved(MouseEvent e) {

		menu.setCursor(
		   Cursor.getPredefinedCursor(
			  isInResizeSpot( e.getPoint() )? Cursor.SE_RESIZE_CURSOR: Cursor.DEFAULT_CURSOR ));
	}

	private Point toScreen( MouseEvent e ) {
		
		Point p = e.getPoint();
		SwingUtilities.convertPointToScreen(p, e.getComponent());
		return p;
		
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		mouseStart = toScreen(e);
		startSize = menu.getSize();
		isResizing = isInResizeSpot(e.getPoint());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseStart = new Point( Integer.MIN_VALUE, Integer.MIN_VALUE );
		isResizing = false;
	}

	@Override
	public void mouseDragged(MouseEvent e) {

		if ( !isResizing ) return;

		Point p = toScreen(e);
		
		int dx = p.x - mouseStart.x;
		int dy = p.y - mouseStart.y;

		
		Dimension minDim = menu.getMinimumSize();
//		Dimension maxDim = menu.getMaximumSize();
		Dimension newDim = new Dimension(startSize.width + dx, startSize.height + dy);

		if ( newDim.width >= minDim.width && newDim.height >= minDim.height /*&&
		     newDim.width <= maxDim.width && newDim.height <= maxDim.height*/	) {
			menu.setPopupSize( newDim );
		}

	}
}
