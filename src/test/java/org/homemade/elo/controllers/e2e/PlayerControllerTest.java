package org.homemade.elo.controllers.e2e;

import org.homemade.elo.entities.Player;
import org.homemade.elo.entities.dto.PlayerDetails;
import org.homemade.elo.entities.dto.PlayerRegistration;
import org.homemade.elo.entities.dto.PlayerWithProperty;
import org.homemade.elo.repo.PlayerRepository;
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

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PlayerControllerTest {
	@Autowired
	private TestRestTemplate restTemplate;
	@Autowired
	private PlayerRepository playerRepository;

	@Test
	public void testPlayers() throws Exception {
		ParameterizedTypeReference<List<PlayerWithProperty>> responseType = new ParameterizedTypeReference<List<PlayerWithProperty>>() {};
		ResponseEntity<List<PlayerWithProperty>> players = this.restTemplate.exchange("/players", HttpMethod.GET, null, responseType);
		assertEquals(HttpStatus.OK, players.getStatusCode());
		assertNotNull(players.getBody());
		assertEquals(2, players.getBody().size());
		assertEquals("Wesley", players.getBody().get(0).getName());
		assertEquals(1405, players.getBody().get(0).getRank());
		assertEquals("Melodie", players.getBody().get(1).getName());
		assertEquals(1395, players.getBody().get(1).getRank());
	}

	@Test
	public void testPlayersByRank() throws Exception {
		ParameterizedTypeReference<List<PlayerWithProperty>> responseType = new ParameterizedTypeReference<List<PlayerWithProperty>>() {};
		ResponseEntity<List<PlayerWithProperty>> players = this.restTemplate.exchange("/scores?order=RANK", HttpMethod.GET, null, responseType);
		assertEquals(HttpStatus.OK, players.getStatusCode());
		assertNotNull(players.getBody());
		assertEquals(2, players.getBody().size());
		assertEquals("Wesley", players.getBody().get(0).getName());
		assertEquals(1405, players.getBody().get(0).getRank());
		assertEquals(1405, players.getBody().get(0).getPropertyValue(), 0.0001);
		assertEquals("Melodie", players.getBody().get(1).getName());
		assertEquals(1395, players.getBody().get(1).getRank());
		assertEquals(1395, players.getBody().get(1).getPropertyValue(), 0.0001);
	}

	@Test
	public void testPlayersByScore() throws Exception {
		ParameterizedTypeReference<List<PlayerWithProperty>> responseType = new ParameterizedTypeReference<List<PlayerWithProperty>>() {};
		ResponseEntity<List<PlayerWithProperty>> players = this.restTemplate.exchange("/scores?order=SCORE", HttpMethod.GET, null, responseType);
		assertEquals(HttpStatus.OK, players.getStatusCode());
		assertNotNull(players.getBody());
		assertEquals(2, players.getBody().size());
		assertEquals("Wesley", players.getBody().get(0).getName());
		assertEquals(1405, players.getBody().get(0).getRank());
		assertEquals(0.6, players.getBody().get(0).getPropertyValue(), 0.0001);
		assertEquals("Melodie", players.getBody().get(1).getName());
		assertEquals(1395, players.getBody().get(1).getRank());
		assertEquals(0.4, players.getBody().get(1).getPropertyValue(), 0.0001);
	}

	@Test
	public void testPlayersByWins() throws Exception {
		ParameterizedTypeReference<List<PlayerWithProperty>> responseType = new ParameterizedTypeReference<List<PlayerWithProperty>>() {};
		ResponseEntity<List<PlayerWithProperty>> players = this.restTemplate.exchange("/scores?order=WINS", HttpMethod.GET, null, responseType);
		assertEquals(HttpStatus.OK, players.getStatusCode());
		assertNotNull(players.getBody());
		assertEquals(2, players.getBody().size());
		assertEquals("Wesley", players.getBody().get(0).getName());
		assertEquals(1405, players.getBody().get(0).getRank());
		assertEquals(3.0, players.getBody().get(0).getPropertyValue(), 0.0001);
		assertEquals("Melodie", players.getBody().get(1).getName());
		assertEquals(1395, players.getBody().get(1).getRank());
		assertEquals(2.0, players.getBody().get(1).getPropertyValue(), 0.0001);
	}

	@Test
	public void testPlayersByLosses() throws Exception {
		ParameterizedTypeReference<List<PlayerWithProperty>> responseType = new ParameterizedTypeReference<List<PlayerWithProperty>>() {};
		ResponseEntity<List<PlayerWithProperty>> players = this.restTemplate.exchange("/scores?order=LOSSES", HttpMethod.GET, null, responseType);
		assertEquals(HttpStatus.OK, players.getStatusCode());
		assertNotNull(players.getBody());
		assertEquals(2, players.getBody().size());
		assertEquals("Melodie", players.getBody().get(0).getName());
		assertEquals(1395, players.getBody().get(0).getRank());
		assertEquals(3.0, players.getBody().get(0).getPropertyValue(), 0.0001);
		assertEquals("Wesley", players.getBody().get(1).getName());
		assertEquals(1405, players.getBody().get(1).getRank());
		assertEquals(2.0, players.getBody().get(1).getPropertyValue(), 0.0001);
	}

	@Test
	public void testPlayersBySmth() throws Exception {
		ParameterizedTypeReference<List<PlayerWithProperty>> responseType = new ParameterizedTypeReference<List<PlayerWithProperty>>() {};
		ResponseEntity<List<PlayerWithProperty>> players = this.restTemplate.exchange("/scores?order=SMTH", HttpMethod.GET, null, responseType);
		assertEquals(HttpStatus.OK, players.getStatusCode());
		assertNotNull(players.getBody());
		assertEquals(2, players.getBody().size());
		assertEquals("Wesley", players.getBody().get(0).getName());
		assertEquals(1405, players.getBody().get(0).getRank());
		assertEquals(1405, players.getBody().get(0).getPropertyValue(), 0.0001);
		assertEquals("Melodie", players.getBody().get(1).getName());
		assertEquals(1395, players.getBody().get(1).getRank());
		assertEquals(1395, players.getBody().get(1).getPropertyValue(), 0.0001);
	}

	@Test
	public void getPlayerDetails() {
		ResponseEntity<PlayerDetails> firstPlayerDetails = this.restTemplate.getForEntity("/players/1", PlayerDetails.class);
		assertEquals(HttpStatus.OK, firstPlayerDetails.getStatusCode());
		assertNotNull(firstPlayerDetails.getBody());
		assertEquals("Wesley", firstPlayerDetails.getBody().getName());
		assertEquals(3, firstPlayerDetails.getBody().getBeat().get(2L).getMatches());
		assertEquals(2, firstPlayerDetails.getBody().getLostFrom().get(2L).getMatches());

		ResponseEntity<PlayerDetails> secondPlayerDetails = this.restTemplate.getForEntity("/players/2", PlayerDetails.class);
		assertEquals(HttpStatus.OK, secondPlayerDetails.getStatusCode());
		assertNotNull(secondPlayerDetails.getBody());
		assertEquals("Melodie", secondPlayerDetails.getBody().getName());
		assertEquals(2, secondPlayerDetails.getBody().getBeat().get(1L).getMatches());
		assertEquals(3, secondPlayerDetails.getBody().getLostFrom().get(1L).getMatches());
	}

	@Test
	public void testPlayerCreation() {
		PlayerRegistration newPlayer = new PlayerRegistration("Frans");
		ResponseEntity<Player> persisted = this.restTemplate.postForEntity("/players", newPlayer, Player.class);
		assertEquals(HttpStatus.CREATED, persisted.getStatusCode());
		assertEquals("Frans", persisted.getBody().getName());
		assertEquals(1400, persisted.getBody().getRank());
		assertEquals(0, persisted.getBody().getGamesPlayed());
		assertEquals(0, persisted.getBody().getWins());
		assertEquals(0, persisted.getBody().getLosses());
		// cleanup
		this.playerRepository.delete(persisted.getBody());
	}

}
