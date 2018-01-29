package org.homemade.elo.services;

import org.homemade.elo.entities.Match;
import org.homemade.elo.entities.Player;
import org.homemade.elo.repo.MatchRepository;
import org.homemade.elo.repo.PlayerRepository;
import org.homemade.elo.util.FileReaderUtil;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class ResourcesInitializingServiceTest {
	private PlayerRepository playerRepo;
	private MatchRepository matchRepo;
	private MatchRegistrationService matchRegistrationService;

	@Before
	public void setUp() throws IOException {
		this.playerRepo = mock(PlayerRepository.class);
		this.matchRepo = mock(MatchRepository.class);
		this.matchRegistrationService = mock(MatchRegistrationService.class);
		new ResourcesInitializingService(new FileReaderUtil(),
				this.playerRepo,
				this.matchRegistrationService);
	}

	@Test
	public void testSuccessfullSetup() {
		verify(this.playerRepo, times(2)).save(any(Player.class));
		verify(this.matchRegistrationService, times(5)).registerMatch(any(Match.class));
	}

	@Test(expected = NumberFormatException.class)
	public void testFailedSetup() {
		new ResourcesInitializingService(new FileReaderUtil(),
				this.playerRepo,
				this.matchRegistrationService,
				"wrong_names.tsv",
				"matches.tsv");
	}

}
