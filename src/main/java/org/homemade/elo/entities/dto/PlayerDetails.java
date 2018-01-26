package org.homemade.elo.entities.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class PlayerDetails {
	private String name;
	private List<String> beatedBy = new ArrayList<>();
	private List<String> lostFrom = new ArrayList<>();

	public PlayerDetails(String name) {
		this.name = name;
	}

	public void addBeatedByCurrentPlayer(String name) {
		this.beatedBy.add(name);
	}

	public void addCurrentLostFromPlayer(String name) {
		this.lostFrom.add(name);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("---- ").append(this.name).append(" ----").append(System.lineSeparator());
		if (this.beatedBy.size() > 0) {
			builder.append(System.lineSeparator());
			builder.append("* Won matches against:").append(System.lineSeparator());
			builder.append(this.playerWithMatchCounter(this.beatedBy));
		}
		if (this.lostFrom.size() > 0) {
			builder.append(System.lineSeparator());
			builder.append("* Was beaten by:").append(System.lineSeparator());
			builder.append(this.playerWithMatchCounter(this.lostFrom));
		}
		return builder.toString();
	}

	private String playerWithMatchCounter(List<String> players) {
		Map<String, Integer> playerWithMatch = new HashMap<>();
		for (String player: players) {
			if (playerWithMatch.containsKey(player)) {
				playerWithMatch.put(player, playerWithMatch.get(player) + 1);
			} else {
				playerWithMatch.put(player, 1);
			}
		}
		StringBuilder builder = new StringBuilder();
		for (Entry<String, Integer> playerEntry: playerWithMatch.entrySet()) {
			builder
				.append("  ")
				.append(playerEntry.getKey())
				.append(playerEntry.getValue() > 1 ? String.format(" (%d times)", playerEntry.getValue()) : "")
				.append(System.lineSeparator());
		}
		return builder.toString();
	}
}
