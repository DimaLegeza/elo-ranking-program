package org.homemade.elo.services;

import org.homemade.elo.entities.Match;
import org.homemade.elo.entities.Player;
import org.homemade.elo.repo.PlayerRepository;
import org.homemade.elo.util.FileReaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;

@Service
public class ResourcesInitializingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResourcesInitializingService.class);

    @Autowired
    public ResourcesInitializingService(
            FileReaderUtil readerUtil,
            PlayerRepository playerRepo,
            MatchRegistrationService matchRegistrationService) {
        this(readerUtil, playerRepo, matchRegistrationService, "names.tsv", "matches.tsv");
    }

    public ResourcesInitializingService(FileReaderUtil readerUtil,
                                        PlayerRepository playerRepo,
                                        MatchRegistrationService matchRegistrationService,
                                        String playerNamesFile,
                                        String matchesFileName) {
        BufferedReader namesReader = readerUtil.asReader(playerNamesFile);
        BufferedReader matchesReader = readerUtil.asReader(matchesFileName);
        this.readNames(namesReader, playerRepo);
        this.readMatches(matchesReader, matchRegistrationService);
    }

    private void readNames(BufferedReader reader, PlayerRepository playerRepo) {
        try {
            String lineContent = reader.readLine();
            while (lineContent != null) {
                final Player player = new Player(lineContent.split("\t")[1]);
                playerRepo.save(player);
                lineContent = reader.readLine();
            }
        } catch (IOException ex) {
            LOGGER.info("Exception encountered while players processing", ex);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                LOGGER.info("Failed to close input reader");
                throw new RuntimeException(ex);
            }
        }
    }

    private void readMatches(BufferedReader reader, MatchRegistrationService matchRegistrationService) {
        try {
            String lineContent = reader.readLine();
            while (lineContent != null) {
                final Match match = new Match(lineContent.split("\t")[0], lineContent.split("\t")[1]);
                matchRegistrationService.registerMatch(new Match(match.getWinnerId() + 1, match.getLooserId() + 1));
                lineContent = reader.readLine();
            }
        } catch (IOException ex) {
            LOGGER.info("Exception encountered while matches processing", ex);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                LOGGER.info("Failed to close input reader");
                throw new RuntimeException(ex);
            }
        }
    }

}
