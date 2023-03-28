package fr.perfect.items.data.simple;

import fr.perfect.items.data.ItemData;

public class StringData implements ItemData {

	private int value;

	public StringData() {
	}

	public StringData(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
