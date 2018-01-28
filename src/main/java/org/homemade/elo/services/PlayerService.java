package org.homemade.elo.services;

import org.homemade.elo.entities.Match;
import org.homemade.elo.entities.Player;
import org.homemade.elo.entities.dto.BasePlayer;
import org.homemade.elo.entities.dto.PlayerDetails;
import org.homemade.elo.entities.dto.PlayerWithProperty;
import org.homemade.elo.enums.Order;
import org.homemade.elo.util.RankingProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.homemade.elo.enums.Order.RANK;

@Service
public class PlayerService {
	private final ResourcesService resourcesService;
	private final RankingProvider rankingProvider;

	@Autowired
	public PlayerService(final ResourcesService resourcesService, final RankingProvider rankingProvider) {
		this.resourcesService = resourcesService;
		this.rankingProvider = rankingProvider;
		this.calculateRanks();
	}

	public List<String> getPlayers(Order order) {
		List<Player> players = new ArrayList<>(this.resourcesService.getPlayers().values());
		int maxNameLength = players.stream().mapToInt(player -> player.getName().length()).max().getAsInt() + 3;
		if (RANK.equals(order)) {
			return players
				.stream()
				.map(player -> new BasePlayer(player.getName(), player.getRank()))
				.sorted(Comparator.comparing(BasePlayer::getRank).reversed())
				.map(player -> player.formatString(maxNameLength))
				.collect(Collectors.toList());
		} else {
			return players
				.stream()
				.map(player -> new PlayerWithProperty(
					player.getName(),
					player.getRank(),
					this.playerProperty(player, order),
					order)
				)
				.sorted(Comparator
						.comparing(PlayerWithProperty::getProperty)
						.thenComparing(PlayerWithProperty::getRank)
						.reversed()
				)
				.map(player -> player.formatString(maxNameLength))
				.collect(Collectors.toList());
		}
	}

	public String getPlayerDetails(int id) {
		if (!this.resourcesService.getPlayers().containsKey(id)) {
			return "User not found";
		}
		final PlayerDetails playerDetails = new PlayerDetails(this.resourcesService.getPlayers().get(id).getName());
		for (Match match: this.resourcesService.getMatches()) {
			if (id == match.getWinner()) {
				playerDetails.addBeatedByCurrentPlayer(this.resourcesService.getPlayers().get(match.getLooser()).getName());
			} else if (id == match.getLooser()) {
				playerDetails.addCurrentLostFromPlayer(this.resourcesService.getPlayers().get(match.getWinner()).getName());
			}
		}
		return playerDetails.toString();
	}

	private void calculateRanks() {
		Map<Integer, Player> players = this.resourcesService.getPlayers();
		for (final Match match: this.resourcesService.getMatches()) {
			Player winner = players.get(match.getWinner());
			Player looser = players.get(match.getLooser());
			int newWinnerRank = this.rankingProvider.calculateRank(winner, looser, 1);
			int newLooserRank = this.rankingProvider.calculateRank(looser, winner, 0);
			winner.setRank(newWinnerRank).incrementGames(true);
			looser.setRank(newLooserRank).incrementGames(false);
		}
	}

	private double playerProperty(Player player, Order order) {
		if (order == null) {
			return 0;
		}
		switch(order) {
			case RANK: 		return player.getRank();
			case SCORE:		return ((float)player.getWins()/player.getGamesPlayed());
			case WINS:		return player.getWins();
			case LOSSES:	return player.getLosses();
			default:		return 0;
		}
	}

}
