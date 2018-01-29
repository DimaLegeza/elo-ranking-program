package org.homemade.elo.entities.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@NoArgsConstructor
class BasePlayer {
	protected static final String MESSAGE = "%s: %s - %d";
	private String name;
	private int rank;

	protected BasePlayer(String name, int rank) {
		this.name = name;
		this.rank = rank;
	}

	protected String formatString(int nameLength) {
		return String.format(BasePlayer.MESSAGE, this.reformatName(nameLength), "Rank", this.rank);
	}

	String reformatName(int nameLength) {
		// append whitespaces so becomes of desired length
		return String.format("%1$-" + nameLength + "s", this.name);
	}
}
