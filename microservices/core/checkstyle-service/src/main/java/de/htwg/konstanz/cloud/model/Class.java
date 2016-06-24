package de.htwg.konstanz.cloud.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

//represents an analyzed java file
public class Class {
	private final String sFullPath;

    private final List<Error> lError = new ArrayList<>();

    private final String sExerciseName;

    private int nErrorCount;

    private int nWarningCount;

    private int nIgnoreCount;

	public Class(String sFullPath, String sExcerciseName) {
		this.sFullPath = sFullPath;
		this.sExerciseName = sExcerciseName;
		this.nErrorCount = 0;
		this.nWarningCount = 0;
		this.nIgnoreCount = 0;
	}

    public void incErrorType(String sSeverity) {
        /* Count every Error Type we have found in the XML */
        if (sSeverity.toLowerCase(Locale.getDefault()).equals("error")) {
            incErrorCount();
        } else if (sSeverity.toLowerCase(Locale.getDefault()).equals("warning")) {
            incWarningCount();
        } else if (sSeverity.toLowerCase(Locale.getDefault()).equals("ignore")) {
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
