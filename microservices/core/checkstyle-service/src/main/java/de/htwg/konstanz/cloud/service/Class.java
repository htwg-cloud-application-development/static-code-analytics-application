package de.htwg.konstanz.cloud.service;

import java.util.ArrayList;
import java.util.List;

public class Class 
{
	String sClassName;
	String sFullPath;
	List<Error> lError = new ArrayList<Error>();
	
	public Class(String sClassName, String sFullPath)
	{
		this.sClassName = sClassName;
		this.sFullPath = sFullPath;
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
}
