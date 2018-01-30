package org.homemade.elo.controllers;

import org.homemade.elo.entities.dto.SerializationDestination;
import org.homemade.elo.services.SerializationService;
import org.homemade.elo.util.OutputStreamProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SerializationController {
	@Autowired
	private SerializationService serializationService;
	@Autowired
	private OutputStreamProvider outputStreamProvider;

	@PostMapping("settings/{plain}")
	public boolean outputFormat(@PathVariable boolean plain) {
		return this.serializationService.setPlain(plain);
	}

	@PostMapping("settings")
	public String changeDestination(@RequestBody SerializationDestination destination) {
		return this.outputStreamProvider.resetStream(destination.getDestination());
	}
}
