package org.homemade.elo.services;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.homemade.elo.entities.Player;
import org.homemade.elo.entities.dto.BasePlayer;
import org.homemade.elo.entities.dto.ChampionshipBucket;
import org.homemade.elo.entities.dto.Forecast;
import org.homemade.elo.repo.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

@Service
public class ForecastingService {
	private List<ChampionshipBucket> championshipBuckets;

	private final PlayerRepository playerRepo;

	@Autowired
	public ForecastingService(final PlayerRepository playerRepo) {
		this.playerRepo = playerRepo;
		/**
		 * Used FIDE tournament categories
		 * https://en.wikipedia.org/wiki/Elo_rating_system
		 */
		this.championshipBuckets = ImmutableList.of(
			new ChampionshipBucket(null, 1000, "Class F"),
			new ChampionshipBucket(1000, 1200, "Class E"),
			new ChampionshipBucket(1200, 1400, "Class D"),
			new ChampionshipBucket(1400, 1600, "Class C"),
			new ChampionshipBucket(1600, 1800, "Class B"),
			new ChampionshipBucket(1800, 2000, "Class A"),
			new ChampionshipBucket(2000, 2200, "Expert"),
			new ChampionshipBucket(2200, 2400, "National Master"),
			new ChampionshipBucket(2400, 2500, "International Master"),
			new ChampionshipBucket(2500, null, "Grandmaster")
		);
	}

	public Forecast forecastMatches() {
		Map<ChampionshipBucket, List<Pair<BasePlayer, BasePlayer>>> forecast = new HashMap<>();
		Map<ChampionshipBucket, List<Player>> playersPerBucket = new HashMap<>();
		Iterable<Player> repoPlayers = this.playerRepo.findAll();
		List<Player> players = new ArrayList<>();
		repoPlayers.forEach(players::add);
		Collections.shuffle(players);
		players.forEach(player -> {
			for (ChampionshipBucket bucket: this.championshipBuckets) {
				if (bucket.playerMatches(player)) {
					if (playersPerBucket.containsKey(bucket)) {
						playersPerBucket.get(bucket).add(player);
					} else {
						List<Player> playerList = new ArrayList<>();
						playerList.add(player);
						playersPerBucket.put(bucket, playerList);
					}
				}
			}
		});
		for (Entry<ChampionshipBucket, List<Player>> entry: playersPerBucket.entrySet()) {
			if (entry.getValue().size() > 1) {
				List<Pair<BasePlayer, BasePlayer>> pairs = new ArrayList<>();
				for (int i = 0; i < Math.floor(entry.getValue().size() / 2); i += 2) {
					Player first = entry.getValue().get(i);
					Player second = entry.getValue().get(i + 1);
					pairs.add(Pair.of(new BasePlayer(first.getName(), first.getRank()), new BasePlayer(second.getName(), second.getRank())));
				}
				forecast.put(entry.getKey(), pairs);
			}
		}
		return new Forecast(forecast);
	}

}
