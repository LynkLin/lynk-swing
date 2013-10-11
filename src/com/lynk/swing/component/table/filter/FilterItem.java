package com.lynk.swing.component.table.filter;

import java.awt.Color;
import java.math.BigDecimal;

public class FilterItem implements Comparable<FilterItem> {
	private boolean isSelected;
	
	private Object obj;

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public FilterItem(Object obj) {
		this.isSelected = true;
		this.obj = obj;
	}

	public FilterItem(boolean isSelected, Object obj) {
		this.isSelected = isSelected;
		this.obj = obj;
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof FilterItem) {
			FilterItem fi = (FilterItem) o;
			if(obj instanceof Number) {
				return new BigDecimal(obj.toString()).doubleValue() == new BigDecimal(fi.obj.toString()).doubleValue();
			}
			if(obj instanceof Color) {
				return ((Color) obj).getRGB() == ((Color) fi.obj).getRGB();
			}
			return obj.toString().equals(fi.obj.toString());
		}
		return false;
	}

	@Override
	public String toString() {
		return obj.toString();
	}

	@Override
	public int compareTo(FilterItem o) {
		if(obj.getClass() != o.obj.getClass()) {
			return -1;
		}
		if(obj instanceof Number) {
			return new BigDecimal(obj.toString()).compareTo(new BigDecimal(o.obj.toString()));
		}
		if(obj instanceof Color) {
			return ((Color) obj).getRGB() - ((Color) o.obj).getRGB();
		}
		return obj.toString().compareTo(o.toString());
	}
}
