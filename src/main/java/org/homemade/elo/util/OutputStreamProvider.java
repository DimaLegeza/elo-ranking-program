package org.homemade.elo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.regex.Pattern;

@Component
public class OutputStreamProvider {
	private static final Logger LOGGER = LoggerFactory.getLogger(OutputStreamProvider.class);
	private Pattern FILE_SYSTEM_REGEX = Pattern.compile("([a-zA-Z]:)?(/[a-zA-Z0-9_.-]+)+/?");
	private boolean system = true;
	private PrintWriter writer;

	public OutputStreamProvider() {
		this.recreateWriter(System.out);
	}

	public String resetStream(String destination) {
		if (destination == null || destination.length() == 0) {
			this.closePrevious();
			this.recreateWriter(System.out);
			this.system = true;
			return "System.out";
		} else if (this.FILE_SYSTEM_REGEX.matcher(destination.replace("\\", "/")).matches()) {
			this.closePrevious();
			try {
				File fileDestination = new File(destination.replace("\\", "/"));
				fileDestination.getParentFile().mkdirs();
				fileDestination.createNewFile(); //will do nothing if file already exists
				this.recreateWriter(new FileOutputStream(fileDestination, true));
				this.system = false;
				return destination.replace("\\", "/");
			} catch (Exception e) {
				LOGGER.error("Errors while working with file", e);
			}
		}
		return "No effect";
	}

	public void flush() {
		this.writer.flush();
	}

	public boolean isSystem() {
		return this.system;
	}

	public OutputStreamProvider println(String line) {
		this.writer.println(line);
		return this;
	}

	private void recreateWriter(OutputStream stream) {
		this.writer = new PrintWriter(stream);
	}

	private void closePrevious() {
		if (this.writer != null && !this.system) {
			this.writer.close();
		}
	}

}
