package org.homemade.elo.entities.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.homemade.elo.entities.dto.helper.BasePlayerDeserializer;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Forecast implements FormattedOut {
	@JsonDeserialize(keyUsing = BasePlayerDeserializer.class)
	private Map<ChampionshipBucket, List<List<BasePlayer>>> championshipClasses;

	@Override
	public String formatString() {
		StringBuilder sb = new StringBuilder("---- CHAMPIONSHIPS SUGGESTED ----")
			.append(System.lineSeparator());
		for (Entry<ChampionshipBucket, List<List<BasePlayer>>> bucket: this.championshipClasses.entrySet()) {
			sb
				.append(System.lineSeparator())
				.append("  * ")
				.append(bucket.getKey().toString())
				.append(System.lineSeparator())
				.append("    ")
				.append(String.join("", Collections.nCopies(bucket.getKey().toString().length(), "-")))
				.append(System.lineSeparator());
			// would be two elements only
			for (List<BasePlayer> pair: bucket.getValue()) {
				sb
					.append(String.format("    %s (rank: %d) - %s (rank: %d)",
						pair.get(0).getName(),
						pair.get(0).getRank(),
						pair.get(1).getName(),
						pair.get(1).getRank())
					)
					.append(System.lineSeparator());
			}
		}
		return sb.toString();
	}
}
