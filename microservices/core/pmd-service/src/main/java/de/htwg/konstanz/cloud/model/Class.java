package de.htwg.konstanz.cloud.model;

import java.util.ArrayList;
import java.util.List;

public class Class {
    private String sClassName;

    private String sFullPath;

    private List<Error> lError = new ArrayList<>();

    private String sExerciseName;

    public Class(String sClassName, String sFullPath, String sExcerciseName) {
        this.sClassName = sClassName;
        this.sFullPath = sFullPath;
        this.sExerciseName = sExcerciseName;
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

}
