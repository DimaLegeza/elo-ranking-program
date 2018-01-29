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

	public static Order fromString(String orderStr) {
		if (orderStr != null) {
			try {
				return Order.valueOf(orderStr.trim().toUpperCase());
			} catch(IllegalArgumentException ex) {
			}
		}
		return Order.RANK;
	}
}
