package org.homemade.elo.repo;

import java.util.List;

import org.homemade.elo.entities.Match;
import org.springframework.data.repository.CrudRepository;

public interface MatchRepository extends CrudRepository<Match, Long> {
    List<Match> findByWinnerId(long id);

    List<Match> findByLooserId(long id);
}
