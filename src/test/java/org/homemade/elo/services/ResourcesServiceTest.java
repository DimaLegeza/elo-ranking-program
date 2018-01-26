package org.homemade.elo.services;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.homemade.elo.util.FileReaderUtil;
import org.junit.Before;
import org.junit.Test;

public class ResourcesServiceTest {
	private ResourcesService service;

	@Before
	public void setUp() throws IOException {
		service = new ResourcesService(new FileReaderUtil());
	}

	@Test
	public void testUsersFetched() {
		assertEquals(2, this.service.getPlayers().size());
	}

	@Test
	public void testMatchesFetched() {
		assertEquals(5, this.service.getMatches().size());
	}

	@Test(expected = NumberFormatException.class)
	public void testConfigurationFail() {
		service = new ResourcesService(new FileReaderUtil(), "wrong_names.tsv", "matches.tsv");
	}
}
