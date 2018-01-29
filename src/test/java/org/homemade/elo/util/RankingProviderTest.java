package org.homemade.elo.util;

import static org.junit.Assert.assertEquals;

import org.homemade.elo.entities.Player;
import org.junit.Before;
import org.junit.Test;

public class RankingProviderTest {

	private RankingProvider rankingProvider;

	@Before
	public void setUp() {
		this.rankingProvider = new RankingProvider();
	}

	@Test
	public void rankReCalculationTest() {
		Player first = new Player(0, "Kate");
		Player second = new Player(1, "Dima");
		assertEquals(1420, this.rankingProvider.calculateRank(first, second, 1));
	}

	@Test
	public void testIfOneAlreadyPlayedMoreThenDefault() {
		Player first = new Player(0, "Kate");
		Player second = new Player(1, "Dima");
		// first won
		int firstPlayerNewRank1 = this.rankingProvider.calculateRank(first, second, 1);
		int secondPlayerNewRank1 = this.rankingProvider.calculateRank(second, first, 0);
		first.setRank(firstPlayerNewRank1).incrementGames(true);
		second.setRank(secondPlayerNewRank1).incrementGames(false);

		assertEquals(1420, first.getRank());
		assertEquals(1380, second.getRank());

		// first won again
		int firstPlayerNewRank2 = this.rankingProvider.calculateRank(first, second, 1);
		int secondPlayerNewRank2 = this.rankingProvider.calculateRank(second, first, 0);
		first.setRank(firstPlayerNewRank2).incrementGames(true);
		second.setRank(secondPlayerNewRank2).incrementGames(false);

		assertEquals(1438, first.getRank());
		assertEquals(1362, second.getRank());

		// first won again
		int firstPlayerNewRank3 = this.rankingProvider.calculateRank(first, second, 1);
		int secondPlayerNewRank3 = this.rankingProvider.calculateRank(second, first, 0);
		first.setRank(firstPlayerNewRank3).incrementGames(true);
		second.setRank(secondPlayerNewRank3).incrementGames(false);

		assertEquals(1454, first.getRank());
		assertEquals(1346, second.getRank());

	}

	@Test
	public void testRankCoeff() {
		assertEquals(40, this.rankingProvider.getRankCoefficient(1400, 2));
		assertEquals(40, this.rankingProvider.getRankCoefficient(3000, 2));
		assertEquals(20, this.rankingProvider.getRankCoefficient(1800, 30));
		assertEquals(10, this.rankingProvider.getRankCoefficient(2500, 31));
	}
}
