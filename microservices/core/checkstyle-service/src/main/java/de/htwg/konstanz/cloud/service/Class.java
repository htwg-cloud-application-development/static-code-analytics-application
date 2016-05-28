package de.htwg.konstanz.cloud.service;

import java.util.ArrayList;
import java.util.List;

public class Class 
{
	private String sClassName;
	private String sFullPath;
	private List<Error> lError = new ArrayList<Error>();
	private String sExerciseName;
	
	public Class(String sClassName, String sFullPath, String sExcerciseName)
	{
		this.sClassName = sClassName;
		this.sFullPath = sFullPath;
		this.sExerciseName = sExcerciseName;
	}
	
	public String getFullPath()
	{
		return sFullPath;
	}

	public void setFullPath(String sFullPath)
	{
		this.sFullPath = sFullPath;
	}
	
	public List<Error> getErrorList()
	{
		return lError;
	}

	public void setError(List<Error> lError)
	{
		this.lError = lError;
	}

	public String getClassName() 
	{
		return sClassName;
	}

	public void setClassName(String sClassName) 
	{
		this.sClassName = sClassName;
	}
	
	public String getsExcerciseName()
	{
		return sExerciseName;
	}

	public void setsExcerciseName(String sExcerciseName)
	{
		this.sExerciseName = sExcerciseName;
	}
}
