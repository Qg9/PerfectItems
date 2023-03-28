package fr.perfect.items.data.simple;

import fr.perfect.items.data.ItemData;

public class DoubleData implements ItemData {

	private double value;

	public DoubleData() {
	}

	public DoubleData(double value) {
		this.value = value;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
}
