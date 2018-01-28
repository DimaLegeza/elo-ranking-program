package org.homemade.elo.services;

import org.homemade.elo.entities.Match;
import org.homemade.elo.entities.Player;
import org.homemade.elo.enums.Order;
import org.homemade.elo.util.RankingProvider;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerServiceTest {
	private PlayerService fixture;

	@Before
	public void setUp() {
		ResourcesService resourcesService = mock(ResourcesService.class);
		RankingProvider rankingProvider = mock(RankingProvider.class);

		Player first = new Player(0, "Kate");
		Player second= new Player(1, "Wendy");
		Player third= new Player(2, "Gerard");
		Player fourth= new Player(3, "Dima");
		Map<Integer, Player> players = new HashMap<>();
		players.put(first.getId(), first);
		players.put(second.getId(), second);
		players.put(third.getId(), third);
		players.put(fourth.getId(), fourth);

		List<Match> matches = new ArrayList<>();
		matches.add(new Match(0, 1));
		matches.add(new Match(0, 3));
		matches.add(new Match(1, 2));
		matches.add(new Match(2, 0));
		matches.add(new Match(3, 1));
		matches.add(new Match(2, 1));
		matches.add(new Match(2, 3));

		when(resourcesService.getPlayers()).thenReturn(players);
		when(resourcesService.getMatches()).thenReturn(matches);

		when(rankingProvider.calculateRank(any(Player.class), any(Player.class), anyInt())).thenCallRealMethod();

		this.fixture = new PlayerService(resourcesService, rankingProvider);
	}

	@Test
	public void testGetPlayersByRank() {
		String[] ranks = {
			"Gerard   : Rank - 1440",
			"Kate     : Rank - 1416",
			"Dima     : Rank - 1383",
			"Wendy    : Rank - 1361"
		};
		assertEquals(Arrays.asList(ranks), this.fixture.getPlayers(Order.RANK));
	}

	@Test
	public void testGetPlayersByScore() {
		String[] ranks = {
			"Gerard   : Score - 0.75 (rank - 1440)",
			"Kate     : Score - 0.6666666865348816 (rank - 1416)",
			"Dima     : Score - 0.3333333432674408 (rank - 1383)",
			"Wendy    : Score - 0.25 (rank - 1361)"
		};
		assertEquals(Arrays.asList(ranks), this.fixture.getPlayers(Order.SCORE));
	}

	@Test
	public void testGetPlayersByWins() {
		String[] ranks = {
			"Gerard   : Wins - 3.0 (rank - 1440)",
			"Kate     : Wins - 2.0 (rank - 1416)",
			"Dima     : Wins - 1.0 (rank - 1383)",
			"Wendy    : Wins - 1.0 (rank - 1361)"
		};
		assertEquals(Arrays.asList(ranks), this.fixture.getPlayers(Order.WINS));
	}

	@Test
	public void testGetPlayersByLosses() {
		String[] ranks = {
			"Wendy    : Losses - 3.0 (rank - 1361)",
			"Dima     : Losses - 2.0 (rank - 1383)",
			"Gerard   : Losses - 1.0 (rank - 1440)",
			"Kate     : Losses - 1.0 (rank - 1416)"
		};
		assertEquals(Arrays.asList(ranks), this.fixture.getPlayers(Order.LOSSES));
	}

	@Test
	public void testGetPlayersByNull() {
		String[] ranks = {
			"Gerard   : Unknown property - 0.0 (rank - 1440)",
			"Kate     : Unknown property - 0.0 (rank - 1416)",
			"Dima     : Unknown property - 0.0 (rank - 1383)",
			"Wendy    : Unknown property - 0.0 (rank - 1361)"
		};
		assertEquals(Arrays.asList(ranks), this.fixture.getPlayers(null));
	}

	@Test
	public void getUserDetails_existingUser() {
		StringBuilder sb = new StringBuilder();
		sb.append("---- Kate ----")
			.append(System.lineSeparator())
			.append(System.lineSeparator())
			.append("* Won matches against:")
			.append(System.lineSeparator())
			.append("  Dima")
			.append(System.lineSeparator())
			.append("  Wendy")
			.append(System.lineSeparator())
			.append(System.lineSeparator())
			.append("* Was beaten by:")
			.append(System.lineSeparator())
			.append("  Gerard")
			.append(System.lineSeparator());
		assertEquals(sb.toString(), this.fixture.getPlayerDetails(0));
	}

	@Test
	public void getUserDetails_notExistingUser() {
		assertEquals("User not found", this.fixture.getPlayerDetails(10));
	}


}
