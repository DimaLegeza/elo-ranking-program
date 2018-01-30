package org.homemade.elo.controllers;

import org.homemade.elo.entities.Match;
import org.homemade.elo.services.MatchRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
public class MatchController {
	@Autowired
	private MatchRegistrationService matchRegistrationService;

	@PostMapping("match")
	@ApiOperation(value = "Registers match for two players", notes = "All stats for player provided will be recalculated e.g. rank, score etc")
	public ResponseEntity<Match> registerMatch(@RequestBody final Match match) {
		Match res = this.matchRegistrationService.registerMatch(match);
		return ResponseEntity
			.status(res != null ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST)
			.body(res);
	}
}
