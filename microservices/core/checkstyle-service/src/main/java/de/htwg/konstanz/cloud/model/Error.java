package de.htwg.konstanz.cloud.model;

public class Error {
    private final String sSeverity;

    private final String sMessage;

    private final String sSource;

    private final int nColumn;

    private final int nErrorAtLine;

    public Error(int nErrorAtLine, int nColumn, String sSeverity, String sMessage, String sSource) {
        this.nErrorAtLine = nErrorAtLine;
        this.nColumn = nColumn;
        this.sSeverity = sSeverity;
        this.sMessage = sMessage;
        this.sSource = sSource;
    }

    public String getSource() {
        return sSource;
    }

    public int getErrorAtLine() {
        return nErrorAtLine;
    }

    public String getSeverity() {
        return sSeverity;
    }

    public String getMessage() {
        return sMessage;
    }

    public int getColumn() {
        return nColumn;
    }
}
