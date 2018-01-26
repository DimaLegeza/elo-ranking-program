package org.homemade.elo.enums;

public enum Order {
	RANK("Rank"), SCORE("Score"), WINS("Wins"), LOSSES("Losses");

	private String orderName;
	Order(String name) {
		this.orderName = name;
	}

	public String getName() {
		return this.orderName;
	}
}
