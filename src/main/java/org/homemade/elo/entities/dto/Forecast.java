package org.homemade.elo.entities.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.data.util.Pair;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Forecast implements FormattedOut {
	private Map<ChampionshipBucket, List<Pair<BasePlayer, BasePlayer>>> championshipClasses;

	@Override
	public String formatString() {
		StringBuilder sb = new StringBuilder("---- CHAMPIONSHIPS SUGGESTED ----")
			.append(System.lineSeparator());
		for (Entry<ChampionshipBucket, List<Pair<BasePlayer, BasePlayer>>> bucket: this.championshipClasses.entrySet()) {
			sb
				.append(System.lineSeparator())
				.append("  * ")
				.append(bucket.getKey().toString())
				.append(System.lineSeparator())
				.append("    ")
				.append(String.join("", Collections.nCopies(bucket.getKey().toString().length(), "-")))
				.append(System.lineSeparator());
			for (Pair<BasePlayer, BasePlayer> pair: bucket.getValue()) {
				sb
					.append(String.format("    %s (rank: %d) - %s (rank: %d)",
						pair.getFirst().getName(),
						pair.getFirst().getRank(),
						pair.getSecond().getName(),
						pair.getSecond().getRank())
					)
					.append(System.lineSeparator());
			}
		}
		return sb.toString();
	}
}
