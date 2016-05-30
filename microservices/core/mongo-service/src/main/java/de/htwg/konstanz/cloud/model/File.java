package de.htwg.konstanz.cloud.model;

import lombok.Data;
import java.util.Collection;
import java.util.LinkedHashSet;

@Data
public class File {

	private String filepath;
	private Collection<Error>  errors = new LinkedHashSet<Error>();
	}
