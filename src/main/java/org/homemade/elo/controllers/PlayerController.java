package org.homemade.elo.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.homemade.elo.entities.Player;
import org.homemade.elo.entities.dto.PlayerDetails;
import org.homemade.elo.entities.dto.PlayerRegistration;
import org.homemade.elo.entities.dto.PlayerWithProperty;
import org.homemade.elo.enums.Order;
import org.homemade.elo.services.PlayerService;
import org.homemade.elo.services.SerializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.homemade.elo.enums.Order.RANK;

@RestController
public class PlayerController {
    @Autowired
    private PlayerService playerService;
    @Autowired
    private SerializationService serializationService;

    @GetMapping("/players")
    @ApiOperation(value = "Get players ordered by rank", notes = "Scores each player based on the games played")
    public List<PlayerWithProperty> getPlayers() {
        List<PlayerWithProperty> res = this.playerService.getPlayers(RANK);
        res.forEach(this.serializationService::publish);
        this.serializationService.publishLineEnd();
        return res;
    }

    @GetMapping("/scores")
    @ApiOperation(value = "Order players", notes = "Generates a list of players sorted by score, their ranking (position in the list) or their number of wins and losses.")
    public List<PlayerWithProperty> getNames(@ApiParam(value = "[RANK, SCORE, WINS, LOSSES]")
                                             @RequestParam(value = "order", defaultValue = "RANK") String orderString) {
        List<PlayerWithProperty> res = this.playerService.getPlayers(Order.fromString(orderString));
        res.forEach(this.serializationService::publish);
        this.serializationService.publishLineEnd();
        return res;
    }

    @GetMapping("/players/{id}")
    @ApiOperation(value = "Get player details", notes = "Generate a report for each person, showing with whom they played and how they fared.")
    public PlayerDetails getPlayerDetails(@PathVariable final long id) {
        final PlayerDetails details = this.playerService.getPlayerDetails(id);
        this.serializationService.publish(details);
        this.serializationService.publishLineEnd();
        return details;
    }

    @PostMapping("/players")
    @ApiOperation(value = "Create player", notes = "Uses default setup: score is pre-set to 1400, number of games is 0")
    public ResponseEntity<Player> create(@RequestBody PlayerRegistration player) {
        Player newPlayer = this.playerService.create(player);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(newPlayer);
    }

}
