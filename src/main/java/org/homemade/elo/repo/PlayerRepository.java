package org.homemade.elo.repo;

import org.homemade.elo.entities.Player;
import org.springframework.data.repository.CrudRepository;

public interface PlayerRepository extends CrudRepository<Player, Long> {
}
