package fr.perfect.items.data;

public class FarmSwordData implements ItemData {

	private boolean sell;
	private double money_multiplier;
	private double exp_multiplier;
	private int killer;

	public FarmSwordData() {}

	public FarmSwordData(boolean sell, double money_multiplier, double exp_multiplier, int killer) {
		this.sell = sell;
		this.money_multiplier = money_multiplier;
		this.exp_multiplier = exp_multiplier;
		this.killer = killer;
	}

	public boolean isSell() {
		return sell;
	}

	public void setSell(boolean sell) {
		this.sell = sell;
	}

	public double getMoney_multiplier() {
		return money_multiplier;
	}

	public void setMoney_multiplier(double money_multiplier) {
		this.money_multiplier = money_multiplier;
	}

	public double getExp_multiplier() {
		return exp_multiplier;
	}

	public void setExp_multiplier(double exp_multiplier) {
		this.exp_multiplier = exp_multiplier;
	}

	public int getKiller() {
		return killer;
	}

	public void setKiller(int killer) {
		this.killer = killer;
	}
}
