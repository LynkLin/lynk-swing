package com.lynk.swing.common;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class NumberFilter extends DocumentFilter {

	@Override
	public void insertString(FilterBypass fb, int offset, String string,
			AttributeSet attr) throws BadLocationException {
		StringBuilder builder = new StringBuilder(string);
		for (int i = builder.length() - 1; i >= 0; i--) {
			int cp = builder.codePointAt(i);
			if (!Character.isDigit(cp) && cp != '.') {
				builder.deleteCharAt(i);
				if (Character.isSupplementaryCodePoint(cp)) {
					i--;
					builder.deleteCharAt(i);
				}
			}
		}
		super.insertString(fb, offset, string, attr);
	}

	@Override
	public void replace(FilterBypass fb, int offset, int length, String text,
			AttributeSet attrs) throws BadLocationException {
		if (text != null) {
            StringBuilder builder = new StringBuilder(text);
            for (int i = builder.length() - 1; i >= 0; i--) {
                int cp = builder.codePointAt(i);
                if (!Character.isDigit(cp) && cp != '.') {
                    builder.deleteCharAt(i);
                    if (Character.isSupplementaryCodePoint(cp)) {
                        i--;
                        builder.deleteCharAt(i);
                    }
                }
            }
            text = builder.toString();
        }
		super.replace(fb, offset, length, text, attrs);
	}
	
}
