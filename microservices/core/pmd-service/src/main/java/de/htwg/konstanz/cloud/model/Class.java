package de.htwg.konstanz.cloud.model;

import java.util.ArrayList;
import java.util.List;

public class Class {
    private final String sFullPath;

    private final List<Error> lError = new ArrayList<>();

    private final String sExerciseName;

    private int nCriticalCount;

    private int nErrorCount;

    private int nWarningCount;

    private int nIgnoreCount;

    public Class(String sFullPath, String sExerciseName) {
        this.sFullPath = sFullPath;
        this.sExerciseName = sExerciseName;
        this.nCriticalCount = 0;
        this.nErrorCount = 0;
        this.nWarningCount = 0;
        this.nIgnoreCount = 0;
    }

    public void incErrorType(int nPriority) {
        /* Count every Error Type we have found in the XML */
        if (nPriority == 4) {
            incCriticalCount();
        } else if (nPriority == 3) {
            incErrorCount();
        } else if (nPriority == 2) {
            incWarningCount();
        } else if(nPriority == 1) {
            incIgnoreCount();
        }
    }

    public String getFullPath() {
        return sFullPath;
    }

    public List<Error> getErrorList() {
        return lError;
    }

    public String getExerciseName() {
        return sExerciseName;
    }

    private void incErrorCount() {
        this.nErrorCount++;
    }

    private void incWarningCount() {
        this.nWarningCount++;
    }

    private void incIgnoreCount() {
        this.nIgnoreCount++;
    }

    private void incCriticalCount() {
        this.nCriticalCount++;
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

    public int getCriticalCount() {
        return this.nCriticalCount;
    }
}
