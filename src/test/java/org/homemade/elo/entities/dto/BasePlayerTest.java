package org.homemade.elo.entities.dto;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BasePlayerTest {

	@Test
	public void testBasePlayerCreation() {
		BasePlayer player = new BasePlayer("Kate", 1400, 10);
		assertEquals("Kate      : Rank - 1400", player.formatString());
	}

	@Test
	public void testBasePlayerCreation_nameShouldNotBeTrimmed() {
		BasePlayer player = new BasePlayer("Benjamin", 1300, 5);
		assertEquals("Benjamin: Rank - 1300", player.formatString());
	}
}
