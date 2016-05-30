package de.htwg.konstanz.cloud.model;

public class Error
{
	private String sSeverity;
	private String sMessage;
	private String sSource;
	private int nColumn;
	private int nErrorAtLine;
	
	public Error(int nErrorAtLine, int nColumn, String sSeverity, String sMessage, String sSource)
	{
		this.nErrorAtLine = nErrorAtLine;
		this.nColumn = nColumn;
		this.sSeverity = sSeverity;
		this.sMessage = sMessage;
		this.sSource = sSource;
	}
	
	public String getSource()
	{
		return sSource;
	}

	public void setSource(String sSource)
	{
		this.sSource = sSource;
	}

	public int getErrorAtLine()
	{
		return nErrorAtLine;
	}

	public void setErrorAtLine(int nErrorAtLine)
	{
		this.nErrorAtLine = nErrorAtLine;
	}

	public String getSeverity()
	{
		return sSeverity;
	}

	public void setSeverity(String sSeverity)
	{
		this.sSeverity = sSeverity;
	}

	public String getMessage()
	{
		return sMessage;
	}

	public void setMessage(String sMessage)
	{
		this.sMessage = sMessage;
	}

	public int getColumn()
	{
		return nColumn;
	}

	public void setColumn(int nColumn)
	{
		this.nColumn = nColumn;
	}
}
