package de.htwg.konstanz.cloud.model;

import java.util.ArrayList;
import java.util.List;

public class Class {
    private String sClassName;

    private String sFullPath;

    private List<Error> lError = new ArrayList<>();

    private String sExerciseName;

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

    public void incErrorType(int Priority) {
        /* Count every Error Type we have found in the XML */
        if (Priority == 3) {
            incErrorCount();
        } else if (Priority == 2) {
            incWarningCount();
        } else if (Priority == 1) {
            incIgnoreCount();
        }
    }

    public String getFullPath() {
        return sFullPath;
    }

    public List<Error> getErrorList() {
        return lError;
    }

    public String getClassName() {
        return sClassName;
    }

    public String getsExcerciseName() {
        return sExerciseName;
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
