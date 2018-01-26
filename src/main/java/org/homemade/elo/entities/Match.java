package org.homemade.elo.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Match {
    private int winner;
    private int looser;

    public Match(String first, String second) {
        this(Integer.parseInt(first), Integer.parseInt(second));
    }

}
