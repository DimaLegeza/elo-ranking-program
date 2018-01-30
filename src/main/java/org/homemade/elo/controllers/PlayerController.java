package org.homemade.elo.controllers;

import static org.homemade.elo.enums.Order.RANK;

import java.util.List;

import org.homemade.elo.entities.dto.PlayerDetails;
import org.homemade.elo.entities.dto.PlayerWithProperty;
import org.homemade.elo.enums.Order;
import org.homemade.elo.services.PlayerService;
import org.homemade.elo.services.SerializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

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

}
