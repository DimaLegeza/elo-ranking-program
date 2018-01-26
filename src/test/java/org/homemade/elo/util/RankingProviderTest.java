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
}
