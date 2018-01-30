package org.homemade.elo.services;

import org.homemade.elo.entities.Player;
import org.homemade.elo.entities.dto.Forecast;
import org.homemade.elo.repo.PlayerRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ForecastingServiceTest {

    private PlayerRepository playerRepo;
    private ForecastingService fixture;

    @Before
    public void setUp() {
        this.playerRepo = mock(PlayerRepository.class);

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

        when(this.playerRepo.findAll()).thenReturn(players);
        this.fixture = new ForecastingService(playerRepo);
    }

    @Test
    public void testForecast() {
        Forecast forecast = this.fixture.forecastMatches();
        assertEquals(2, forecast.getChampionshipClasses().size());
    }
}
