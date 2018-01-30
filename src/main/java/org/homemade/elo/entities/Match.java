package org.homemade.elo.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "elo_match")
public class Match {
    @Id
    @GeneratedValue
    private Long id;

    private long winnerId;
    private long looserId;

    public Match(String winnerId, String looserId) {
        this(Long.parseLong(winnerId), Long.parseLong(looserId));
    }

    public Match(long winnerId, long looserId) {
        this.winnerId = winnerId;
        this.looserId = looserId;
    }

}
