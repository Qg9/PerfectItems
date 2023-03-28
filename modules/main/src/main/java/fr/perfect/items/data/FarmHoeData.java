package fr.perfect.items.data;

public class FarmHoeData implements ItemData{

	private double multiplier;
	private int radius;
	private boolean replant;
	private boolean sell;

	public FarmHoeData() {}

	public FarmHoeData(double multiplier, int radius, boolean replant, boolean sell) {
		this.multiplier = multiplier;
		this.radius = radius;
		this.replant = replant;
		this.sell = sell;
	}

	public double getMultiplier() {
		return multiplier;
	}

	public void setMultiplier(double multiplier) {
		this.multiplier = multiplier;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public boolean isReplant() {
		return replant;
	}

	public void setReplant(boolean replant) {
		this.replant = replant;
	}

	public boolean isSell() {
		return sell;
	}

	public void setSell(boolean sell) {
		this.sell = sell;
	}


}
