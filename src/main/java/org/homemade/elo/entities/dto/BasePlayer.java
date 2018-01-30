package org.homemade.elo.entities.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BasePlayer {
	protected static final String MESSAGE = "%s: %s - %d";
	private String name;
	private int rank;
	private int nameLength;

	public BasePlayer(String name, int rank) {
		this(name, rank, 0);
	}

	String formatString() {
		return String.format(BasePlayer.MESSAGE, this.reformatName(this.nameLength), "Rank", this.rank);
	}

	String reformatName(int nameLength) {
		// append whitespaces so becomes of desired length
		return String.format("%1$-" + nameLength + "s", this.name);
	}
}
