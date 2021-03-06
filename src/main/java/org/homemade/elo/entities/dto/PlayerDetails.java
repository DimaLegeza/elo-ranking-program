package org.homemade.elo.entities.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@NoArgsConstructor
public class PlayerDetails implements FormattedOut {
	private static final String PLAYER_NAME_PREFIX = "---- ";
	private static final String PLAYER_NAME_SUFFIX = " ----";
	private static final String PLAYER_WON_MATCHES = "* Won matches against:";
	private static final String PLAYER_LOST_MATCHES = "* Was beaten by:";
	private String name;
	private Map<Long, OpponentPlayerDetails> beat = new HashMap<>();
	private Map<Long, OpponentPlayerDetails> lostFrom = new HashMap<>();

	public PlayerDetails(String name) {
		this.name = name;
	}

	public void addBeatByCurrentPlayer(long id, String name) {
		if (this.beat.containsKey(id)) {
			this.beat.get(id).incMatches();
		} else {
			this.beat.put(id, new OpponentPlayerDetails(id, name));
		}
	}

	public void addCurrentLostFromPlayer(long id, String name) {
		if (this.lostFrom.containsKey(id)) {
			this.lostFrom.get(id).incMatches();
		} else {
			this.lostFrom.put(id, new OpponentPlayerDetails(id, name));
		}
	}

	public String formatString() {
		StringBuilder builder = new StringBuilder(System.lineSeparator());
		builder.append(PLAYER_NAME_PREFIX).append(this.name).append(PLAYER_NAME_SUFFIX);
		if (this.beat.size() > 0) {
			builder.append(System.lineSeparator());
			builder.append(System.lineSeparator());
			builder.append(PLAYER_WON_MATCHES).append(System.lineSeparator());
			for (final OpponentPlayerDetails details: this.beat.values()) {
				builder.append(details.formatString()).append(System.lineSeparator());
			}
		}
		if (this.lostFrom.size() > 0) {
			if (beat.size() == 0) {
				builder.append(System.lineSeparator());
			}
			builder.append(System.lineSeparator());
			builder.append(PLAYER_LOST_MATCHES).append(System.lineSeparator());
			for (final OpponentPlayerDetails details: this.lostFrom.values()) {
				builder.append(details.formatString()).append(System.lineSeparator());
			}
		}
		return builder.toString();
	}

}
