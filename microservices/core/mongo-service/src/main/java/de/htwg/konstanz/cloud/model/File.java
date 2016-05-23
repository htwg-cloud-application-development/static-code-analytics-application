package de.htwg.konstanz.cloud.model;

import java.util.Collection;
import java.util.LinkedHashSet;

public class File {

	private String filepath;
	private Collection<Error>  errors = new LinkedHashSet<Error>();
	
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public Collection<Error> getErrors() {
		return errors;
	}
	public void setErrors(Collection<Error> errors) {
		this.errors = errors;
	}	
}
