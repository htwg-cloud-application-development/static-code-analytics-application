package de.htwg.konstanz.cloud.model;

public class Error {
    private final int nLineBegin;

    private final int nLineEnd;

    private final int nColumnBegin;

    private final int nColumnEnd;

    private final int nPriority;

    private String sRule = "";

    private String sClassName = "";

    private String sPackage = "";

    private String sRuleset = "";

    private String sMessage = "";

    public Error(int nLineBegin, int nLineEnd, int nColumnBegin, int nColumnEnd, int nPriority, String sRule,
                                        String sClassName, String sPackage, String sRuleset, String sMessage) {
        this.nLineBegin = nLineBegin;
        this.nLineEnd = nLineEnd;
        this.nColumnBegin = nColumnBegin;
        this.nColumnEnd = nColumnEnd;
        this.nPriority = nPriority;
        this.sRule = sRule;
        this.sClassName = sClassName;
        this.sPackage = sPackage;
        this.sRuleset = sRuleset;
        this.sMessage = sMessage;
    }

    public int getLineBegin() {
        return nLineBegin;
    }

    public int getLineEnd() {
        return nLineEnd;
    }

    public int getColumnBegin() {
        return nColumnBegin;
    }

    public int getColumnEnd() {
        return nColumnEnd;
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