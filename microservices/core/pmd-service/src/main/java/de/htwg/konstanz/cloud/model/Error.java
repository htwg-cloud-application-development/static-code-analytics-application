package de.htwg.konstanz.cloud.model;

public class Error
{
    private int nLineBegin;
    private int nLineEnd;
    private int nColumnBegin;
    private int nColumnEnd;
    private int nPriority;
    private String sRule = "";
    private String sClassName = "";
    private String sPackage = "";
    private String sRuleset = "";
    private String sMessage = "";

    public Error(int nLineBegin, int nLineEnd, int nColumnBegin, int nColumnEnd, int nPriority, String sRule, String sClassName, String sPackage, String sRuleset, String sMessage)
    {
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

    public void setLineBegin(int nLineBegin) {
        this.nLineBegin = nLineBegin;
    }

    public void setLineEnd(int nLineEnd) {
        this.nLineEnd = nLineEnd;
    }

    public void setColumnBegin(int nColumnBegin) {
        this.nColumnBegin = nColumnBegin;
    }

    public void setColumnEnd(int nColumnEnd) {
        this.nColumnEnd = nColumnEnd;
    }

    public void setPriority(int nPriority) {
        this.nPriority = nPriority;
    }

    public void setRule(String sRule) {
        this.sRule = sRule;
    }

    public void setClassName(String sClassName) {
        this.sClassName = sClassName;
    }

    public void setPackage(String sPackage) {
        this.sPackage = sPackage;
    }

    public void setRuleset(String sRuleset) {
        this.sRuleset = sRuleset;
    }

    public void setMessage(String sMessage) {
        this.sMessage = sMessage;
    }
}