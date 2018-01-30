package org.homemade.elo.entities.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.homemade.elo.entities.Player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ChampionshipBucket {
	private int lowLimit;
	private int upperLimit;
	private String name;

	public ChampionshipBucket(Integer lowLimit, Integer upperLimit, String name) {
		this.lowLimit = lowLimit != null ? lowLimit : 0;
		this.upperLimit = upperLimit != null ? upperLimit : Integer.MAX_VALUE;
		this.name = name;
	}

	public boolean playerMatches(Player player) {
		return lowLimit <= player.getRank() && upperLimit > player.getRank();
	}

	@Override
	public String toString() {
		return String.format("%s (ranks: %d - %d)", this.name, this.lowLimit, this.upperLimit);
	}
}
