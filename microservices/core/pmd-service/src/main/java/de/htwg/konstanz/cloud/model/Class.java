package de.htwg.konstanz.cloud.model;

import java.util.ArrayList;
import java.util.List;

public class Class {
    private final String sClassName;

    private final String sFullPath;

    private final List<Error> lError = new ArrayList<>();

    private final String sExerciseName;

    private int nErrorCount;

    private int nWarningCount;

    private int nIgnoreCount;

    public Class(String sClassName, String sFullPath, String sExcerciseName) {
        this.sClassName = sClassName;
        this.sFullPath = sFullPath;
        this.sExerciseName = sExcerciseName;
        this.nErrorCount = 0;
        this.nWarningCount = 0;
        this.nIgnoreCount = 0;
    }

    public void incErrorType(int nPriority) {
        /* Count every Error Type we have found in the XML */
        if (nPriority == 3) {
            incErrorCount();
        } else if (nPriority == 2) {
            incWarningCount();
        } else if (nPriority == 1) {
            incIgnoreCount();
        }
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

    private void incErrorCount() {
        this.nErrorCount++;
    }

    private void incWarningCount() {
        this.nWarningCount++;
    }

    private void incIgnoreCount() {
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
