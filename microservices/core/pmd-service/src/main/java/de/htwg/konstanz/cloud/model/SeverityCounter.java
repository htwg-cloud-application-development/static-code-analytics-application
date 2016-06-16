package de.htwg.konstanz.cloud.model;

public class SeverityCounter {
    private int nErrorCount;


    private int nWarningCount;

    private int nIgnoreCount;

    public SeverityCounter() {
        this.nErrorCount = 0;
        this.nWarningCount = 0;
        this.nIgnoreCount = 0;
    }

    public void incErrorCount() {
        this.nErrorCount++;
    }

    public void incWarningCount() {
        this.nWarningCount++;
    }

    public void incIgnoreCount() {
        this.nIgnoreCount++;
    }

    public int getErrorCount() {
        return this.nErrorCount;
    }

    public int getWarningCount() {
        return this.nWarningCount;
    }

    public int getIgnoreCount() {
        return this.nIgnoreCount;
    }
}
