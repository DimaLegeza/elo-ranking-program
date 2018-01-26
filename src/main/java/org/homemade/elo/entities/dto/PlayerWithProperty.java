package org.homemade.elo.entities.dto;

import org.homemade.elo.enums.Order;

import lombok.Getter;

@Getter
public class PlayerWithProperty extends BasePlayer {
	protected static final String MESSAGE = "%s: %s - %s (rank - %d)";
	private double property;
	private String propertyName;

	public PlayerWithProperty(String name, int rank, double property, Order order) {
		super(name, rank);
		this.property = property;
		this.propertyName = order != null ? order.getName() : "Unknown property";
	}

	@Override
	public String formatString(int nameLength) {
		return String.format(PlayerWithProperty.MESSAGE, this.reformatName(nameLength), this.propertyName, this.property, this.getRank());
	}
}
