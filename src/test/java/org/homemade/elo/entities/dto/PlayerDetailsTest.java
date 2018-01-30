package org.homemade.elo.entities.dto;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlayerDetailsTest {
	private PlayerDetails details;

	@Before
	public void setUp() {
		this.details = new PlayerDetails("Kate");
	}

	@Test
	public void shouldConstructProperDetails() {
		StringBuilder sb = new StringBuilder(System.lineSeparator());
		sb.append("---- Kate ----");
		assertEquals(sb.toString(), this.details.formatString());
	}

	@Test
	public void shouldConstructProperDetails_failsOnly() {
		this.details.addCurrentLostFromPlayer(0, "Jorn");
		this.details.addCurrentLostFromPlayer(1, "Anton");
		this.details.addCurrentLostFromPlayer(2, "Miriam");
		this.details.addCurrentLostFromPlayer(0, "Jorn");

		StringBuilder sb = new StringBuilder(System.lineSeparator());
		sb.append("---- Kate ----")
			.append(System.lineSeparator())
			.append(System.lineSeparator())
			.append("* Was beaten by:")
			.append(System.lineSeparator())
			.append("  Jorn (2 times)")
			.append(System.lineSeparator())
			.append("  Anton")
			.append(System.lineSeparator())
			.append("  Miriam")
			.append(System.lineSeparator());
		assertEquals(sb.toString(), this.details.formatString());
	}

	@Test
	public void shouldConstructProperDetails_WinsOnly() {
		this.details.addBeatByCurrentPlayer(0, "Jorn");
		this.details.addBeatByCurrentPlayer(1, "Anton");
		this.details.addBeatByCurrentPlayer(2, "Miriam");
		this.details.addBeatByCurrentPlayer(0, "Jorn");

		StringBuilder sb = new StringBuilder(System.lineSeparator());
		sb.append("---- Kate ----")
			.append(System.lineSeparator())
			.append(System.lineSeparator())
			.append("* Won matches against:")
			.append(System.lineSeparator())
			.append("  Jorn (2 times)")
			.append(System.lineSeparator())
			.append("  Anton")
			.append(System.lineSeparator())
			.append("  Miriam")
			.append(System.lineSeparator());
		assertEquals(sb.toString(), this.details.formatString());
	}

	@Test
	public void shouldConstructProperDetails_FullObject() {
		this.details.addBeatByCurrentPlayer(0, "Jorn");
		this.details.addBeatByCurrentPlayer(1, "Anton");
		this.details.addBeatByCurrentPlayer(2, "Miriam");
		this.details.addBeatByCurrentPlayer(2, "Miriam");
		this.details.addBeatByCurrentPlayer(3, "Marco");
		this.details.addBeatByCurrentPlayer(0, "Jorn");

		this.details.addCurrentLostFromPlayer(3, "Marco");
		this.details.addCurrentLostFromPlayer(4, "Alex");
		this.details.addCurrentLostFromPlayer(5, "Frederik");
		this.details.addCurrentLostFromPlayer(6, "Moises");
		this.details.addCurrentLostFromPlayer(6, "Moises");

		StringBuilder sb = new StringBuilder(System.lineSeparator());
		sb.append("---- Kate ----")
			.append(System.lineSeparator())
			.append(System.lineSeparator())
			.append("* Won matches against:")
			.append(System.lineSeparator())
			.append("  Jorn (2 times)")
			.append(System.lineSeparator())
			.append("  Anton")
			.append(System.lineSeparator())
			.append("  Miriam (2 times)")
			.append(System.lineSeparator())
			.append("  Marco")
			.append(System.lineSeparator())
			.append(System.lineSeparator())
			.append("* Was beaten by:")
			.append(System.lineSeparator())
			.append("  Marco")
			.append(System.lineSeparator())
			.append("  Alex")
			.append(System.lineSeparator())
			.append("  Frederik")
			.append(System.lineSeparator())
			.append("  Moises (2 times)")
			.append(System.lineSeparator());
		assertEquals(sb.toString(), this.details.formatString());
	}
	
	
}
