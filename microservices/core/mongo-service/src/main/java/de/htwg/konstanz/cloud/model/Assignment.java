package de.htwg.konstanz.cloud.model;

import java.util.Collection;
import java.util.LinkedHashSet;

public class Assignment {

	Collection<File> assignments = new LinkedHashSet<File>();

	public Collection<File> getAssignments() {
		return assignments;
	}

	public void setAssignments(Collection<File> assignments) {
		this.assignments = assignments;
	}
}
