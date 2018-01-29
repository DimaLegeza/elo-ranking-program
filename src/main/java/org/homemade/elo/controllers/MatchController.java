package org.homemade.elo.controllers;

import io.swagger.annotations.ApiOperation;
import org.homemade.elo.entities.Match;
import org.homemade.elo.services.MatchRegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MatchController {
	private static final Logger LOGGER = LoggerFactory.getLogger(MatchController.class);

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
