package org.homemade.elo.services;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.homemade.elo.entities.Player;
import org.homemade.elo.entities.dto.PlayerDetails;
import org.homemade.elo.entities.dto.PlayerWithProperty;
import org.homemade.elo.enums.Order;
import org.homemade.elo.repo.MatchRepository;
import org.homemade.elo.repo.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {
	private PlayerRepository playerRepo;
	private MatchRepository matchRepo;

	@Autowired
	public PlayerService(final PlayerRepository playerRepo, final MatchRepository matchRepo) {
		this.playerRepo = playerRepo;
		this.matchRepo = matchRepo;
	}

	public List<PlayerWithProperty> getPlayers(Order order) {
		int maxNameLength = this.getPlayerNameMaxLength();
		return StreamSupport
				.stream(this.playerRepo.findAll().spliterator(), false)
				.map(player -> new PlayerWithProperty(
					player.getName(),
					player.getRank(),
					this.playerProperty(player, order),
					maxNameLength,
					order)
				)
				.sorted(Comparator
						.comparing(PlayerWithProperty::getPropertyValue)
						.thenComparing(PlayerWithProperty::getRank)
						.reversed()
				)
				.collect(Collectors.toList());
	}

	public PlayerDetails getPlayerDetails(long id) {
		Player player = this.playerRepo.findOne(id);
		if (player == null) {
			return new PlayerDetails("User not found");
		}
		final PlayerDetails playerDetails = new PlayerDetails(player.getName());
		this.matchRepo.findByWinnerId(player.getId())
				.forEach(match -> playerDetails.addBeatByCurrentPlayer(match.getLooserId(), this.playerRepo.findOne(match.getLooserId()).getName()));
		this.matchRepo.findByLooserId(player.getId())
				.forEach(match -> playerDetails.addCurrentLostFromPlayer(match.getWinnerId(), this.playerRepo.findOne(match.getWinnerId()).getName()));
		return playerDetails;
	}

	public int getPlayerNameMaxLength() {
		return StreamSupport
				.stream(this.playerRepo.findAll().spliterator(), false)
				.mapToInt(player -> player.getName().length()).max().getAsInt() + 3;
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
