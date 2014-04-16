package com.lynk.swing.component.common.checkcombobox;

public class CheckStore<T> {
	private boolean isSelected;
	private T value;

	public CheckStore(T value) {
		isSelected = false;
		this.value = value;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public T getValue() {
		return value;
	}

	@Override
	public String toString() {
		return value.toString();
	}
}
