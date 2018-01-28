package org.homemade.elo.util;

import org.homemade.elo.entities.Player;
import org.springframework.stereotype.Component;

@Component
public class RankingProvider {

	// recalculation of rank for firstPlayer supplied
	public int calculateRank(Player firstPlayer, Player secondPlayer, int actuallyScored) {
		double expectedScore = 1 / (
				1 + Math.pow(10, ((float)(secondPlayer.getRank() - firstPlayer.getRank())/400))
		);
		int rankCoeff = this.getRankCoefficient(firstPlayer.getRank(), firstPlayer.getGamesPlayed());
		return (int)Math.round(firstPlayer.getRank() + rankCoeff * (actuallyScored - expectedScore));
	}

	/*
	 * Using FIDE standards
	 * https://en.wikipedia.org/wiki/Elo_rating_system
	 */
	private int getRankCoefficient(int currentRank, int gamesPlayed) {
		if (gamesPlayed < 30) {
			return 40;
		} else if (currentRank >= 2400) {
			return 10;
		} else {
			return 20;
		}
	}

}
