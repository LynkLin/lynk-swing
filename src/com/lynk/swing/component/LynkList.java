package com.lynk.swing.component;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;

import com.lynk.swing.common.Constants;

public class LynkList<E> extends JList<E> implements Constants {
	private static final long serialVersionUID = 1L;

	private MouseDoubleClick<E> mouseDoubleClick;
	
	public LynkList() {
		super();
		init();
	}

	public LynkList(ListModel<E> dataModel) {
		super(dataModel);
		init();
	}
	
	public void setMouseDoubleClick(MouseDoubleClick<E> mouseDoubleClick) {
		this.mouseDoubleClick = mouseDoubleClick;
	}
	
	private void init() {
		setFont(APP_FONT);
		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent evt) {
				if(SwingUtilities.isLeftMouseButton(evt) && evt.getClickCount() == 2 && mouseDoubleClick != null) {
					int index = locationToIndex(evt.getPoint());
					if(index == -1) {
						return;
					}
					setSelectedIndex(index);
					if(mouseDoubleClick != null) {
						E data = getModel().getElementAt(index);
						mouseDoubleClick.doubleClick(data);
					}
				}
			}
		});
	}
	
	public interface MouseDoubleClick<E> {
		void doubleClick(E data);
	}
}
