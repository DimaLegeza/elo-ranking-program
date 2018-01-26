package org.homemade.elo.services;

import org.homemade.elo.dto.Match;
import org.homemade.elo.dto.Name;
import org.homemade.elo.util.FileReaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ResourcesService {
    private List<Name> names = new ArrayList<>();
    private List<Match> matches = new ArrayList<>();

    @Autowired
    public ResourcesService(FileReaderUtil readerUtil) throws IOException {
        BufferedReader namesReader = readerUtil.asReader("names.tsv");
        BufferedReader matchesReader = readerUtil.asReader("matches.tsv");
        this.readNames(namesReader);
        this.readMatches(matchesReader);
    }

    public List<Name> getNames() {
        return names;
    }

    public List<Match> getMatches() {
        return matches;
    }

    private void readNames(BufferedReader reader) throws IOException {
        String lineContent = reader.readLine();
        while(lineContent != null) {
            final Name name = new Name(lineContent.split("\t")[0], lineContent.split("\t")[1]);
            this.names.add(name);
            lineContent = reader.readLine();
        }
        reader.close();
    }

    private void readMatches(BufferedReader reader) throws IOException {
        String lineContent = reader.readLine();
        while(lineContent != null) {
            final Match match = new Match(lineContent.split("\t")[0], lineContent.split("\t")[1]);
            this.matches.add(match);
            lineContent = reader.readLine();
        }
        reader.close();
    }

}
