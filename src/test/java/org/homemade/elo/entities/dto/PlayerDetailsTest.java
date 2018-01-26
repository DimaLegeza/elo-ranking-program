package org.homemade.elo.entities.dto;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class PlayerDetailsTest {
	private PlayerDetails details;

	@Before
	public void setUp() {
		this.details = new PlayerDetails("Kate");
	}

	@Test
	public void shouldConstructProperDetails() {
		assertEquals("---- Kate ----", this.details.toString());
	}

	@Test
	public void shouldConstructProperDetails_failsOnly() {
		this.details.addCurrentLostFromPlayer("Jorn");
		this.details.addCurrentLostFromPlayer("Anton");
		this.details.addCurrentLostFromPlayer("Miriam");
		this.details.addCurrentLostFromPlayer("Jorn");

		StringBuilder sb = new StringBuilder();
		sb.append("---- Kate ----")
			.append(System.lineSeparator())
			.append(System.lineSeparator())
			.append("* Was beaten by:")
			.append(System.lineSeparator())
			.append("  Anton")
			.append(System.lineSeparator())
			.append("  Jorn (2 times)")
			.append(System.lineSeparator())
			.append("  Miriam")
			.append(System.lineSeparator());
		assertEquals(sb.toString(), this.details.toString());
	}

	@Test
	public void shouldConstructProperDetails_WinsOnly() {
		this.details.addBeatedByCurrentPlayer("Jorn");
		this.details.addBeatedByCurrentPlayer("Anton");
		this.details.addBeatedByCurrentPlayer("Miriam");
		this.details.addBeatedByCurrentPlayer("Jorn");

		StringBuilder sb = new StringBuilder();
		sb.append("---- Kate ----")
			.append(System.lineSeparator())
			.append(System.lineSeparator())
			.append("* Won matches against:")
			.append(System.lineSeparator())
			.append("  Anton")
			.append(System.lineSeparator())
			.append("  Jorn (2 times)")
			.append(System.lineSeparator())
			.append("  Miriam")
			.append(System.lineSeparator());
		assertEquals(sb.toString(), this.details.toString());
	}

	@Test
	public void shouldConstructProperDetails_FullObject() {
		this.details.addBeatedByCurrentPlayer("Jorn");
		this.details.addBeatedByCurrentPlayer("Anton");
		this.details.addBeatedByCurrentPlayer("Miriam");
		this.details.addBeatedByCurrentPlayer("Miriam");
		this.details.addBeatedByCurrentPlayer("Marco");
		this.details.addBeatedByCurrentPlayer("Jorn");

		this.details.addCurrentLostFromPlayer("Marco");
		this.details.addCurrentLostFromPlayer("Alex");
		this.details.addCurrentLostFromPlayer("Frederik");
		this.details.addCurrentLostFromPlayer("Moises");
		this.details.addCurrentLostFromPlayer("Moises");

		StringBuilder sb = new StringBuilder();
		sb.append("---- Kate ----")
			.append(System.lineSeparator())
			.append(System.lineSeparator())
			.append("* Won matches against:")
			.append(System.lineSeparator())
			.append("  Marco")
			.append(System.lineSeparator())
			.append("  Anton")
			.append(System.lineSeparator())
			.append("  Jorn (2 times)")
			.append(System.lineSeparator())
			.append("  Miriam (2 times)")
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
		assertEquals(sb.toString(), this.details.toString());
	}
	
	
}
