package org.homemade.elo.controllers.e2e;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.homemade.elo.entities.dto.SerializationDestination;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SerializationControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void changeOutputType() {
		ResponseEntity<Boolean> updated = this.restTemplate.postForEntity("/settings/false", null, Boolean.class);
		assertEquals(false, updated.getBody());
	}

	@Test
	public void changeDestinationToFile() throws IOException {
		File temp = File.createTempFile("tempfile", ".tmp");
		temp.deleteOnExit();
		SerializationDestination destination = new SerializationDestination(temp.getCanonicalPath());
		ResponseEntity<String> updated = this.restTemplate.postForEntity("/settings", destination, String.class);
		assertEquals(temp.getCanonicalPath().replace("\\", "/"), updated.getBody());
	}

	@Test
	public void changeDestinationToFileAndBackToSystem() throws IOException {
		File temp = File.createTempFile("tempfile", ".tmp");
		temp.deleteOnExit();
		SerializationDestination destination = new SerializationDestination(temp.getCanonicalPath());
		ResponseEntity<String> updated = this.restTemplate.postForEntity("/settings", destination, String.class);
		assertEquals(temp.getCanonicalPath().replace("\\", "/"), updated.getBody());
		SerializationDestination defaultDestination = new SerializationDestination();
		ResponseEntity<String> backToDefault = this.restTemplate.postForEntity("/settings", defaultDestination, String.class);
		assertEquals("System.out", backToDefault.getBody());
	}

	@Test
	public void changeDestinationNoEffect() throws IOException {
		SerializationDestination destination = new SerializationDestination("http://google.com");
		ResponseEntity<String> updated = this.restTemplate.postForEntity("/settings", destination, String.class);
		assertEquals("No effect", updated.getBody());
	}
}
