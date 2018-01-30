package org.homemade.elo.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.homemade.elo.entities.dto.FormattedOut;
import org.homemade.elo.util.OutputStreamProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SerializationService {
	private static final Logger LOGGER = LoggerFactory.getLogger(SerializationService.class);
	private boolean plain = true;
	private ObjectMapper objectMapper = new ObjectMapper();
	private OutputStreamProvider streamProvider;

	@Autowired
	public SerializationService(OutputStreamProvider provider) {
		this.streamProvider = provider;
	}

	public void publish(FormattedOut out) {
		try {
			this.streamProvider.println(plain ? out.formatString() : this.objectMapper.writeValueAsString(out)).flush();
		} catch (JsonProcessingException e) {
			LOGGER.warn("Could not serialize as json", e);
		}
	}

	public void publishLineEnd() {
		this.streamProvider.println(System.lineSeparator()).flush();
	}

	public boolean setPlain(boolean newValue) {
		this.plain = newValue;
		return this.plain;
	}

}
