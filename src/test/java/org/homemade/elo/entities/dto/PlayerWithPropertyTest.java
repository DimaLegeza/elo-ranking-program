package org.homemade.elo.entities.dto;

import static org.junit.Assert.assertEquals;

import org.homemade.elo.enums.Order;
import org.junit.Test;

public class PlayerWithPropertyTest {

	@Test
	public void shouldProperlyConstructPlayer_Wins() {
		PlayerWithProperty player = new PlayerWithProperty("Kate", 1400, 5, Order.WINS);
		assertEquals("Kate      : Wins - 5.0 (rank - 1400)", player.formatString(10));
	}

	@Test
	public void shouldProperlyConstructPlayer_Losses() {
		PlayerWithProperty player = new PlayerWithProperty("Kate", 1400, 5, Order.LOSSES);
		assertEquals("Kate      : Losses - 5.0 (rank - 1400)", player.formatString(10));
	}

	@Test
	public void shouldProperlyConstructPlayer_Rank() {
		PlayerWithProperty player = new PlayerWithProperty("Kate", 1400, 0.6543372, Order.SCORE);
		assertEquals("Kate      : Score - 0.6543372 (rank - 1400)", player.formatString(10));
	}

	@Test
	public void shouldProperlyConstructPlayer_Unknown() {
		PlayerWithProperty player = new PlayerWithProperty("Kate", 1400, 12, null);
		assertEquals("Kate      : Unknown property - 12.0 (rank - 1400)", player.formatString(10));
	}
}
