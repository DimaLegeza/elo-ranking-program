package org.homemade.elo.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.homemade.elo.entities.Match;
import org.homemade.elo.entities.Player;
import org.homemade.elo.util.FileReaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourcesService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResourcesService.class);

    private Map<Integer, Player> players = new HashMap<>();
    private List<Match> matches = new ArrayList<>();

    @Autowired
    public ResourcesService(FileReaderUtil readerUtil) {
        this(readerUtil, "names.tsv", "matches.tsv");
    }

    public ResourcesService(FileReaderUtil readerUtil, String playerNamesFile, String matchesFileName) {
        BufferedReader namesReader = readerUtil.asReader(playerNamesFile);
        BufferedReader matchesReader = readerUtil.asReader(matchesFileName);
        this.readNames(namesReader);
        this.readMatches(matchesReader);
    }

    public Map<Integer, Player> getPlayers() {
        return players;
    }

    public List<Match> getMatches() {
        return matches;
    }

    private void readNames(BufferedReader reader) {
        try {
            String lineContent = reader.readLine();
            while (lineContent != null) {
                int id = Integer.parseInt(lineContent.split("\t")[0]);
                final Player player = new Player(id, lineContent.split("\t")[1]);
                this.players.put(id, player);
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

    private void readMatches(BufferedReader reader) {
        try {
            String lineContent = reader.readLine();
            while (lineContent != null) {
                final Match match = new Match(lineContent.split("\t")[0], lineContent.split("\t")[1]);
                this.matches.add(match);
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
