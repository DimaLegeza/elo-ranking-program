package org.homemade.elo.entities.dto;

import org.homemade.elo.enums.Order;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlayerWithPropertyTest {

	@Test
	public void shouldProperlyConstructPlayer_Wins() {
		FormattedOut player = new PlayerWithProperty("Kate", 1400, 5, 10, Order.WINS);
		assertEquals("Kate      : Wins - 5 (rank - 1400)", player.formatString());
	}

	@Test
	public void shouldProperlyConstructPlayer_Losses() {
		FormattedOut player = new PlayerWithProperty("Kate", 1400, 5, 10, Order.LOSSES);
		assertEquals("Kate      : Losses - 5 (rank - 1400)", player.formatString());
	}

	@Test
	public void shouldProperlyConstructPlayer_Rank() {
		FormattedOut player = new PlayerWithProperty("Kate", 1400, 0.6543372, 10, Order.SCORE);
		assertEquals("Kate      : Score - 0.654 (rank - 1400)", player.formatString());
	}

	@Test
	public void shouldProperlyConstructPlayer_Unknown() {
		FormattedOut player = new PlayerWithProperty("Kate", 1400, 12, 10, null);
		assertEquals("Kate      : Unknown property - 12 (rank - 1400)", player.formatString());
	}
}
