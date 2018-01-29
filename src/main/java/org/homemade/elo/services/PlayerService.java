package org.homemade.elo.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.homemade.elo.entities.Match;
import org.homemade.elo.entities.Player;
import org.homemade.elo.entities.dto.PlayerDetails;
import org.homemade.elo.entities.dto.PlayerWithProperty;
import org.homemade.elo.enums.Order;
import org.homemade.elo.util.RankingProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public List<PlayerWithProperty> getPlayers(Order order) {
		List<Player> players = new ArrayList<>(this.resourcesService.getPlayers().values());
		return players
			.stream()
			.map(player -> new PlayerWithProperty(
				player.getName(),
				player.getRank(),
				this.playerProperty(player, order),
				order)
			)
			.sorted(Comparator
					.comparing(PlayerWithProperty::getPropertyValue)
					.thenComparing(PlayerWithProperty::getRank)
					.reversed()
			)
			.collect(Collectors.toList());
	}

	public PlayerDetails getPlayerDetails(int id) {
		if (!this.resourcesService.getPlayers().containsKey(id)) {
			return new PlayerDetails("User not found");
		}
		final PlayerDetails playerDetails = new PlayerDetails(this.resourcesService.getPlayers().get(id).getName());
		for (Match match: this.resourcesService.getMatches()) {
			if (id == match.getWinner()) {
				playerDetails.addBeatByCurrentPlayer(match.getLooser(), this.resourcesService.getPlayers().get(match.getLooser()).getName());
			} else if (id == match.getLooser()) {
				playerDetails.addCurrentLostFromPlayer(match.getWinner(), this.resourcesService.getPlayers().get(match.getWinner()).getName());
			}
		}
		return playerDetails;
	}

	public int getPlayerNameMaxLength() {
		List<Player> players = new ArrayList<>(this.resourcesService.getPlayers().values());
		return players.stream().mapToInt(player -> player.getName().length()).max().getAsInt() + 3;
	}

	public Match registerMatch(Match match) {
		Map<Integer, Player> players = this.resourcesService.getPlayers();
		if (players.containsKey(match.getWinner()) && players.containsKey(match.getLooser())) {
			Player winner = players.get(match.getWinner());
			Player looser = players.get(match.getLooser());
			int newWinnerRank = this.rankingProvider.calculateRank(winner, looser, 1);
			int newLooserRank = this.rankingProvider.calculateRank(looser, winner, 0);
			winner.setRank(newWinnerRank).incrementGames(true);
			looser.setRank(newLooserRank).incrementGames(false);
			return match;
		}
		return null;
	}

	public void reset() {
		this.resourcesService.getPlayers().values().forEach(Player::reset);
		this.calculateRanks();
	}

	private void calculateRanks() {
		for (final Match match: this.resourcesService.getMatches()) {
			this.registerMatch(match);
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
