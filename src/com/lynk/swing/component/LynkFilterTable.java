package com.lynk.swing.component;

import java.util.Collections;

import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import com.lynk.swing.component.table.filter.FilterHeaderRenderer;
import com.lynk.swing.component.table.filter.FilterPopup;

/**
 * 自定义
 * AUTO_RESIZE_OFF, ColumnSelection, CellSelection, RowHeight 24, 
 * TableHeader CENTER, TableHeader bg blue, 
 * ColumnControlVisible true, Highlighter RowHighlighter
 * @author Administrator
 *
 */
public class LynkFilterTable extends LynkTable {
	private static final long serialVersionUID = 1L;

	private IModelOrSorterChanged modelOrSorterChanged;
	
	private FilterPopup filterPopup;
	
	public LynkFilterTable(TableModel dm) {
		this(dm, true, 1);
	}
	
	public LynkFilterTable(TableModel dm, boolean initHighLighter) {
		this(dm, initHighLighter, 1);
	}
	
	public LynkFilterTable(TableModel dm, boolean initHighLighter, int highLighterRowNum) {
		super(dm, initHighLighter, highLighterRowNum);
		init();
		dm.addTableModelListener(new TableModelListener() {
			
			@Override
			public void tableChanged(TableModelEvent e) {
				if(e.getType() == TableModelEvent.UPDATE && e.getLastRow() > e.getFirstRow()) {
					if(filterPopup != null) {
						clearFilter();
					}
				}
			}
		});
	}
	
	private void init() {
		setRowAlignCenter();
		filterPopup = new FilterPopup(true, this);
		FilterHeaderRenderer renderer = new FilterHeaderRenderer(filterPopup.getFilter());
		for(TableColumn column : Collections.list(getColumnModel().getColumns())) {
			column.setHeaderRenderer(renderer);
		}
	}
	
	public void clearFilter() {
		filterPopup.reset();
		setRowFilter(null);
		getTableHeader().repaint();
	}
	
	public void addModelOrSorterChanged(IModelOrSorterChanged evt) {
		modelOrSorterChanged = evt;
		getModel().addTableModelListener(new TableModelListener() {
			
			@Override
			public void tableChanged(TableModelEvent e) {
				if(modelOrSorterChanged != null) {
					modelOrSorterChanged.modelOrSorterChanged();
				}
			}
		});

		getRowSorter().addRowSorterListener(new RowSorterListener() {
			
			@Override
			public void sorterChanged(RowSorterEvent e) {
				if(modelOrSorterChanged != null) {
					modelOrSorterChanged.modelOrSorterChanged();
				}
			}
		});
	}
	
	@Override
	public void setModel(TableModel dataModel) {
		super.setModel(dataModel);
		
	}

	public interface IModelOrSorterChanged {
		void modelOrSorterChanged();
	}
}
