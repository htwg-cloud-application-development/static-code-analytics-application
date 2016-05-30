package de.htwg.konstanz.cloud.model;

import lombok.Data;

@Data
public class Error {

	private String line;
	private String column;
	private String severity;
	private String message;
	private String source;
}
