package org.homemade.elo.entities.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@NoArgsConstructor
public class OpponentPlayerDetails {
	private static final String MESSAGE = "  %s";
	private static final String EXTENDED_MESSAGE = "  %s (%d times)";
	private long id;
	private String name;
	private int matches;

	public OpponentPlayerDetails(long id, String name) {
		this.id = id;
		this.name = name;
		this.matches = 1;
	}

	public void incMatches() {
		this.matches++;
	}

	public String formatString() {
		return this.matches > 1
			? String.format(OpponentPlayerDetails.EXTENDED_MESSAGE, this.name, this.matches)
			: String.format(OpponentPlayerDetails.MESSAGE, this.name);
	}
}
