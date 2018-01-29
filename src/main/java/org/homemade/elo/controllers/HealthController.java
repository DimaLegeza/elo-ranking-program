package org.homemade.elo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

	@GetMapping("/health-check")
	public String health() {
		return "Running";
	}

}
