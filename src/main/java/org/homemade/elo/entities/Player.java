package org.homemade.elo.entities;

import lombok.Getter;

@Getter
public class Player {
    private int id;
    private String name;
    private int rank;
    private int gamesPlayed;
    private int wins;
    private int losses;

    public Player(int id, String name) {
        this.id = id;
        this.name = name;
        // default rank
        this.rank = 1400;
    }

    public Player setRank(final int rank) {
        this.rank = rank;
        return this;
    }

    public Player incrementGames(boolean winner) {
        this.gamesPlayed++;
        if (winner) {
            this.wins++;
        } else {
            this.losses++;
        }
        return this;
    }

    public void reset() {
        this.gamesPlayed = 0;
        this.wins = 0;
        this.losses = 0;
        this.rank = 1400;
    }

}
