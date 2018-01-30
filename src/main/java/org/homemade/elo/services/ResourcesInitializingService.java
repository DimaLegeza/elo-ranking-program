package org.homemade.elo.services;

import java.io.BufferedReader;
import java.io.IOException;

import org.homemade.elo.entities.Match;
import org.homemade.elo.entities.Player;
import org.homemade.elo.repo.MatchRepository;
import org.homemade.elo.repo.PlayerRepository;
import org.homemade.elo.util.FileReaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourcesInitializingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResourcesInitializingService.class);

    @Autowired
    public ResourcesInitializingService(
            FileReaderUtil readerUtil,
            PlayerRepository playerRepo,
            MatchRepository matchRepo,
            MatchRegistrationService matchRegistrationService) {
        this(readerUtil, playerRepo, matchRepo, matchRegistrationService, "names.tsv", "matches.tsv");
    }

    public ResourcesInitializingService(FileReaderUtil readerUtil,
                                        PlayerRepository playerRepo,
                                        MatchRepository matchRepo,
                                        MatchRegistrationService matchRegistrationService,
                                        String playerNamesFile,
                                        String matchesFileName) {
        try {
            BufferedReader namesReader = readerUtil.asReader(playerNamesFile);
            BufferedReader matchesReader = readerUtil.asReader(matchesFileName);
            this.readNames(namesReader, playerRepo);
            this.readMatches(matchesReader, matchRegistrationService);
        } catch (Throwable e){
            // whatever happens incorrectly - we treat both files as corrupted
            LOGGER.error("Error encountered while pre-loading initial data. Reverting changes...");
            playerRepo.deleteAll();
            matchRepo.deleteAll();
        }
    }

    private void readNames(BufferedReader reader, PlayerRepository playerRepo) throws IOException {
        try {
            String lineContent = reader.readLine();
            while (lineContent != null) {
                final Player player = new Player(lineContent.split("\t")[1]);
                playerRepo.save(player);
                lineContent = reader.readLine();
            }
        } finally {
            try {
                reader.close();
            } catch (IOException ignored) {}
        }
    }

    private void readMatches(BufferedReader reader, MatchRegistrationService matchRegistrationService) throws IOException {
        try {
            String lineContent = reader.readLine();
            while (lineContent != null) {
                final Match match = new Match(lineContent.split("\t")[0], lineContent.split("\t")[1]);
                matchRegistrationService.registerMatch(new Match(match.getWinnerId() + 1, match.getLooserId() + 1));
                lineContent = reader.readLine();
            }
        } finally {
            try {
                reader.close();
            } catch (IOException ignored) {}
        }
    }

}
