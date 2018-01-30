package org.homemade.elo.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

public class PlayerTest {
	private Player player;

	@Before
	public void setUp() {
		this.player = new Player("Kate");
	}

	@Test
	public void testDefaultPlayerCreation() {
		assertNull(this.player.getId());
		assertEquals("Kate", this.player.getName());
		assertEquals(1400, this.player.getRank());
		assertEquals(0, this.player.getGamesPlayed());
		assertEquals(0, this.player.getWins());
		assertEquals(0, this.player.getLosses());
	}

	@Test
	public void testUpdateGames() {
		this.player.incrementGames(true);
		assertEquals(1, this.player.getGamesPlayed());
		assertEquals(1, this.player.getWins());
		assertEquals(0, this.player.getLosses());
		this.player.incrementGames(false);
		assertEquals(2, this.player.getGamesPlayed());
		assertEquals(1, this.player.getWins());
		assertEquals(1, this.player.getLosses());
	}

}
