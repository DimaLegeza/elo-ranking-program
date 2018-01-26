package org.homemade.elo.entities.dto;

import org.homemade.elo.enums.Order;

import lombok.Getter;

@Getter
public class PlayerWithQuality extends BasePlayer {
	protected static final String MESSAGE = "%s: %s - %s (rank - %d)";
	private double quality;
	private String qualityName;

	public PlayerWithQuality(String name, int rank, double quality, Order order) {
		super(name, rank);
		this.quality = quality;
		this.qualityName = order.getName();
	}

	@Override
	public String formatString(int nameLength) {
		return String.format(PlayerWithQuality.MESSAGE, this.reformatName(nameLength), this.qualityName, this.quality, this.getRank());
	}
}
