package org.homemade.elo.util;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.Assert.*;

public class OutputStreamProviderTest {

	@Test
	public void testWriteToTempFile() throws IOException {
		File temp = createTempFile();
		OutputStreamProvider provider = new OutputStreamProvider();
		String resetStream = provider.resetStream(temp.getCanonicalPath());
		assertEquals(temp.getCanonicalPath(), resetStream);

		provider.println("Some bla").flush();
		String res = "Some bla" + System.lineSeparator();
		BufferedReader reader = new BufferedReader(new FileReader(temp));
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			sb.append(line).append(System.lineSeparator());
		}
		assertEquals(res, sb.toString());
		assertFalse(provider.isSystem());
	}

	@Test
	public void defaultConfiguration() {
		OutputStreamProvider provider = new OutputStreamProvider();
		assertTrue(provider.isSystem());
	}

	@Test
	public void resetToSystem() throws IOException {
		File temp = createTempFile();
		OutputStreamProvider provider = new OutputStreamProvider();
		String resetStream = provider.resetStream(temp.getCanonicalPath());
		assertEquals(temp.getCanonicalPath(), resetStream);
		assertFalse(provider.isSystem());

		String systemStream = provider.resetStream("");
		assertEquals("System.out", systemStream);
		assertTrue(provider.isSystem());
	}

	@Test
	public void resetWithNull() throws IOException {
		File temp = createTempFile();
		OutputStreamProvider provider = new OutputStreamProvider();
		String resetStream = provider.resetStream(temp.getCanonicalPath());
		assertEquals(temp.getCanonicalPath(), resetStream);
		assertFalse(provider.isSystem());

		String systemStream = provider.resetStream(null);
		assertEquals("System.out", systemStream);
		assertTrue(provider.isSystem());
	}

	@Test
	public void resetWithNoEffect() throws IOException {
		OutputStreamProvider provider = new OutputStreamProvider();
		String resetStream = provider.resetStream("http://google.com");
		assertTrue(provider.isSystem());
		assertEquals("No effect", resetStream);
	}

	private File createTempFile() throws IOException {
		File temp = File.createTempFile("tempfile", ".tmp");
		temp.deleteOnExit();
		return temp;
	}
}
