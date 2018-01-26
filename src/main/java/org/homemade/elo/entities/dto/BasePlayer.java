package org.homemade.elo.entities.dto;

import lombok.Getter;

@Getter
public class BasePlayer {
	protected static final String MESSAGE = "%s: %s - %d";
	private String name;
	private int rank;

	public BasePlayer(String name, int rank) {
		this.name = name;
		this.rank = rank;
	}

	public String formatString(int nameLength) {
		return String.format(BasePlayer.MESSAGE, this.reformatName(nameLength), "Rank", this.rank);
	}

	String reformatName(int nameLength) {
		// append whitespaces so becomes of desired length
		return String.format("%1$-" + nameLength + "s", this.name);
	}
}
