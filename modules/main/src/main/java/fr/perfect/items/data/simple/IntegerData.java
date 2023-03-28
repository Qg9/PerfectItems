package fr.perfect.items.data.simple;

import fr.perfect.items.data.ItemData;

public class IntegerData implements ItemData {

	private int value;

	public IntegerData() {
	}

	public IntegerData(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
