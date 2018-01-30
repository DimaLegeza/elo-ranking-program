package org.homemade.elo.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.homemade.elo.entities.Match;
import org.homemade.elo.entities.Player;
import org.homemade.elo.entities.dto.PlayerWithProperty;
import org.homemade.elo.enums.Order;
import org.homemade.elo.repo.MatchRepository;
import org.homemade.elo.repo.PlayerRepository;
import org.junit.Before;
import org.junit.Test;

public class PlayerServiceTest {
	private PlayerService fixture;

	@Before
	public void setUp() {
		PlayerRepository playerRepo = mock(PlayerRepository.class);
		MatchRepository matchRepo = mock(MatchRepository.class);

		Player first = new Player("Kate")
				.withId(0)
				.withRank(1416)
				.withGamesPlayed(3)
				.withWins(2)
				.withLosses(1);
		Player second = new Player("Wendy")
				.withId(1)
				.withRank(1361)
				.withGamesPlayed(3)
				.withWins(1)
				.withLosses(3);
		Player third = new Player("Gerard")
				.withId(2)
				.withRank(1440)
				.withGamesPlayed(4)
				.withWins(3)
				.withLosses(1);
		Player fourth = new Player("Dima")
				.withId(3)
				.withRank(1383)
				.withGamesPlayed(3)
				.withWins(1)
				.withLosses(2);
		List<Player> players = new ArrayList<>();
		players.add(first);
		players.add(second);
		players.add(third);
		players.add(fourth);

		List<Match> matches = new ArrayList<>();
		Match firstMatch = new Match(0, 1);
		Match secondMatch = new Match(0, 3);
		Match thirdMatch = new Match(1, 2);
		Match fourthMatch = new Match(2, 0);
		Match fifthMatch = new Match(3, 1);
		Match sixthMatch = new Match(2, 1);
		Match seventhMatch = new Match(2, 3);
		matches.add(firstMatch);
		matches.add(secondMatch);
		matches.add(thirdMatch);
		matches.add(fourthMatch);
		matches.add(fifthMatch);
		matches.add(sixthMatch);
		matches.add(seventhMatch);

		when(playerRepo.findAll()).thenReturn(players);
		when(playerRepo.findOne(eq(0L))).thenReturn(first);
		when(playerRepo.findOne(eq(1L))).thenReturn(second);
		when(playerRepo.findOne(eq(2L))).thenReturn(third);
		when(playerRepo.findOne(eq(3L))).thenReturn(fourth);
		when(matchRepo.findAll()).thenReturn(matches);
		when(matchRepo.findByWinnerId(anyLong())).thenReturn(Arrays.asList(firstMatch, secondMatch));
		when(matchRepo.findByLooserId(anyLong())).thenReturn(Arrays.asList(fourthMatch));

		this.fixture = new PlayerService(playerRepo, matchRepo);
	}

	@Test
	public void testGetPlayersByRank() {
		String[] ranks = {
			"Gerard   : Rank - 1440",
			"Kate     : Rank - 1416",
			"Dima     : Rank - 1383",
			"Wendy    : Rank - 1361"
		};
		assertEquals(
			Arrays.asList(ranks),
			this.fixture.getPlayers(Order.RANK)
				.stream()
				.map(PlayerWithProperty::formatString)
				.collect(Collectors.toList())
		);
	}

	@Test
	public void testGetPlayersByScore() {
		String[] ranks = {
			"Gerard   : Score - 0.750 (rank - 1440)",
			"Kate     : Score - 0.667 (rank - 1416)",
			"Dima     : Score - 0.333 (rank - 1383)",
			"Wendy    : Score - 0.333 (rank - 1361)"
		};
		assertEquals(
			Arrays.asList(ranks),
			this.fixture.getPlayers(Order.SCORE)
				.stream()
				.map(PlayerWithProperty::formatString)
				.collect(Collectors.toList())
		);
	}

	@Test
	public void testGetPlayersByWins() {
		String[] ranks = {
			"Gerard   : Wins - 3 (rank - 1440)",
			"Kate     : Wins - 2 (rank - 1416)",
			"Dima     : Wins - 1 (rank - 1383)",
			"Wendy    : Wins - 1 (rank - 1361)"
		};
		assertEquals(
			Arrays.asList(ranks),
			this.fixture.getPlayers(Order.WINS)
				.stream()
				.map(PlayerWithProperty::formatString)
				.collect(Collectors.toList())
		);
	}

	@Test
	public void testGetPlayersByLosses() {
		String[] ranks = {
			"Wendy    : Losses - 3 (rank - 1361)",
			"Dima     : Losses - 2 (rank - 1383)",
			"Gerard   : Losses - 1 (rank - 1440)",
			"Kate     : Losses - 1 (rank - 1416)"
		};
		assertEquals(
			Arrays.asList(ranks),
			this.fixture.getPlayers(Order.LOSSES)
				.stream()
				.map(PlayerWithProperty::formatString)
				.collect(Collectors.toList())
		);
	}

	@Test
	public void testGetPlayersByNull() {
		String[] ranks = {
			"Gerard   : Unknown property - 0 (rank - 1440)",
			"Kate     : Unknown property - 0 (rank - 1416)",
			"Dima     : Unknown property - 0 (rank - 1383)",
			"Wendy    : Unknown property - 0 (rank - 1361)"
		};
		assertEquals(
			Arrays.asList(ranks),
			this.fixture.getPlayers(null)
				.stream()
				.map(PlayerWithProperty::formatString)
				.collect(Collectors.toList())
		);
	}

	@Test
	public void getUserDetails_existingUser() {
		StringBuilder sb = new StringBuilder(System.lineSeparator());
		sb.append("---- Kate ----")
			.append(System.lineSeparator())
			.append(System.lineSeparator())
			.append("* Won matches against:")
			.append(System.lineSeparator())
			.append("  Wendy")
			.append(System.lineSeparator())
			.append("  Dima")
			.append(System.lineSeparator())
			.append(System.lineSeparator())
			.append("* Was beaten by:")
			.append(System.lineSeparator())
			.append("  Gerard")
			.append(System.lineSeparator());
		assertEquals(sb.toString(), this.fixture.getPlayerDetails(0).formatString());
	}

	@Test
	public void getUserDetails_notExistingUser() {
		StringBuilder sb = new StringBuilder(System.lineSeparator());
		sb.append("---- User not found ----");
		assertEquals(sb.toString(), this.fixture.getPlayerDetails(10).formatString());
	}


}
