package org.homemade.elo.controllers.e2e;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.homemade.elo.entities.Match;
import org.homemade.elo.entities.dto.PlayerWithProperty;
import org.homemade.elo.services.PlayerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MatchControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private PlayerService playerService;

	@Before
	public void setUp() {
		this.playerService.reset();
	}

	@Test
	public void testMatchRegistration() {
		ParameterizedTypeReference<List<PlayerWithProperty>> responseType = new ParameterizedTypeReference<List<PlayerWithProperty>>() {};
		ResponseEntity<List<PlayerWithProperty>> players = this.restTemplate.exchange("/players", HttpMethod.GET, null, responseType);
		assertEquals(HttpStatus.OK, players.getStatusCode());
		assertNotNull(players.getBody());
		assertEquals(2, players.getBody().size());
		assertEquals(1405, players.getBody().get(0).getRank());
		assertEquals(1395, players.getBody().get(1).getRank());

		Match match = new Match(1, 0);
		ResponseEntity<Match> persisted = this.restTemplate.postForEntity("/match", match, Match.class);
		assertEquals(HttpStatus.CREATED, persisted.getStatusCode());

		ResponseEntity<List<PlayerWithProperty>> updatedPlayers = this.restTemplate.exchange("/players", HttpMethod.GET, null, responseType);
		assertEquals(HttpStatus.OK, updatedPlayers.getStatusCode());
		assertNotNull(updatedPlayers.getBody());
		assertEquals(2, updatedPlayers.getBody().size());
		assertEquals(1416, updatedPlayers.getBody().get(0).getRank());
		assertEquals(1384, updatedPlayers.getBody().get(1).getRank());
	}

	@Test
	public void testShouldWorkForNonExistentId() {
		Match match = new Match(2, 0);
		ResponseEntity<Match> persisted = this.restTemplate.postForEntity("/match", match, Match.class);
		assertEquals(HttpStatus.BAD_REQUEST, persisted.getStatusCode());
	}
}
