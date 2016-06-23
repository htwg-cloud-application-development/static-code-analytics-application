package de.htwg.konstanz.cloud.model;

public class Error {
    private final Line oLine;

    private final Column oColumn;

    private final int nPriority;

    private final String sRule;

    private final String sClassName;

    private String sPackage;

    private final String sRuleset;

    private final String sMessage;

    public Error(Line oLine, Column oColumn, int nPriority, String sRule, String sClassName,
                                            String sPackage, String sRuleset, String sMessage) {
        this.oLine = oLine;
        this.oColumn = oColumn;
        this.nPriority = nPriority;
        this.sRule = sRule;
        this.sClassName = sClassName;
        this.sPackage = sPackage;
        this.sRuleset = sRuleset;
        this.sMessage = sMessage;
    }

    public int getLineBegin() {
        return this.oLine.getLineBegin();
    }

    public int getLineEnd() {
        return this.oLine.getLineEnd();
    }

    public int getColumnBegin() {
        return this.oColumn.getColumnBegin();
    }

    public int getColumnEnd() {
        return this.oColumn.getColumnEnd();
    }

    public int getPriority() {
        return nPriority;
    }

    public String getRule() {
        return sRule;
    }

    public String getClassName() {
        return sClassName;
    }

    public String getPackage() {
        return sPackage;
    }

    public String getRuleset() {
        return sRuleset;
    }

    public String getMessage() {
        return sMessage;
    }

    public void setPackage(String sPackage) {
        this.sPackage = sPackage;
    }
}