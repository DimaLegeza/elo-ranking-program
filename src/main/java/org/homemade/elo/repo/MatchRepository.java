package org.homemade.elo.repo;

import org.homemade.elo.entities.Match;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MatchRepository extends CrudRepository<Match, Long> {
    List<Match> findByWinnerId(long id);

    List<Match> findByLooserId(long id);
}
