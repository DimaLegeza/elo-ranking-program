package org.homemade.elo.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "elo_player")
public class Player {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int rank;
    private int gamesPlayed;
    private int wins;
    private int losses;

    public Player(String name) {
        this.name = name;
        // default rank
        this.rank = 1400;
    }

    public Player withRank(final int rank) {
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

    public Player withGamesPlayed(int count) {
        this.gamesPlayed = count;
        return this;
    }

    public Player withWins(int count) {
        this.wins = count;
        return this;
    }

    public Player withLosses(int count) {
        this.losses = count;
        return this;
    }

    public Player withId(long id) {
        this.id = id;
        return this;
    }

}
