package org.homemade.elo.controllers;

import org.homemade.elo.dto.Match;
import org.homemade.elo.dto.Name;
import org.homemade.elo.services.ResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    @Autowired
    private ResourcesService resourcesService;

    @GetMapping("/test-rest")
    public String health() {
        return "Apparently should work";
    }

    @GetMapping("/names")
    public List<Name> getNames() {
        return this.resourcesService.getNames();
    }

    @GetMapping("/matches")
    public List<Match> getMatches() {
        return this.resourcesService.getMatches();
    }

}
