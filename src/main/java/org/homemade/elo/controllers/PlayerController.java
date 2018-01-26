package org.homemade.elo.controllers;

import static org.homemade.elo.enums.Order.RANK;

import java.util.List;

import org.homemade.elo.enums.Order;
import org.homemade.elo.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @GetMapping("/health-check")
    public String health() {
        return "OK";
    }

    @GetMapping("/players")
    @ApiOperation(value = "Get players ordered by rank", notes = "Scores each player based on the games played")
    public List<String> getPlayers() {
        return this.playerService.getPlayers(RANK);
    }

    @GetMapping("/scores")
    @ApiOperation(value = "Order players", notes = "Generates a list of players sorted by score, their ranking (position in the list) or their number of wins and losses.")
    public List<String> getNames(@RequestParam(defaultValue = "RANK") Order order) {
        return this.playerService.getPlayers(order);

    }

    @GetMapping("/players/{id}")
    @ApiOperation(value = "Get player details", notes = "Generate a report for each person, showing with whom they played and how they fared.")
    public String getPlayerDetails(@PathVariable final int id) {
        return this.playerService.getPlayerDetails(id);
    }

}
