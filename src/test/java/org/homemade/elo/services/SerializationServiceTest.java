package org.homemade.elo.services;

import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.homemade.elo.entities.dto.FormattedOut;
import org.homemade.elo.entities.dto.PlayerWithProperty;
import org.homemade.elo.enums.Order;
import org.homemade.elo.util.OutputStreamProvider;
import org.junit.Before;
import org.junit.Test;

public class SerializationServiceTest {
	private FormattedOut entity;
	private OutputStreamProvider streamProvider;
	private SerializationService fixture;

	@Before
	public void setUp() {
		this.streamProvider = mock(OutputStreamProvider.class);
		when(streamProvider.println(anyString())).thenReturn(streamProvider);
		this.entity = new PlayerWithProperty("Kate", 1400, 0.6, 8, Order.SCORE);
		this.fixture = new SerializationService(this.streamProvider);
	}

	@Test
	public void testDefaultSerialization() {
		this.fixture.publish(this.entity);
		verify(this.streamProvider, times(1)).println(eq("Kate    : Score - 0.600 (rank - 1400)"));
		verify(this.streamProvider, times(1)).flush();
	}

	@Test
	public void changeSerializationMode() {
		assertFalse(this.fixture.setPlain(false));
	}

	@Test
	public void testJsonSerialization() {
		this.fixture.setPlain(false);
		this.fixture.publish(this.entity);
		verify(this.streamProvider, times(1)).println(eq("{\"name\":\"Kate\",\"rank\":1400,\"propertyValue\":0.6}"));
		verify(this.streamProvider, times(1)).flush();
	}

}
