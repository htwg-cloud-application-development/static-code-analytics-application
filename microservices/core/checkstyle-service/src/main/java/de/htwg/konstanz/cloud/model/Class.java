package de.htwg.konstanz.cloud.model;

import java.util.ArrayList;
import java.util.List;

public class Class {
	private final String sClassName;

	private final String sFullPath;

	private final List<Error> lError = new ArrayList<>();

	private final String sExerciseName;

	public Class(String sClassName, String sFullPath, String sExcerciseName) {
		this.sClassName = sClassName;
		this.sFullPath = sFullPath;
		this.sExerciseName = sExcerciseName;
	}

	public String getFullPath() {
		return sFullPath;
	}

	public List<Error> getErrorList() {
		return lError;
	}

	public String getsExcerciseName() {
		return sExerciseName;
	}
}
