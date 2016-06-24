package de.htwg.konstanz.cloud.util;

import java.io.File;

public class OperatingSystemCheck {

    private String getOsName() {
        String sOperatingSystem = "";

        if ("".equals(sOperatingSystem)) {
            sOperatingSystem = System.getProperty("os.name");
        }

        return sOperatingSystem;
    }

    public String getOperatingSystemSeparator() {
        String sOperatingSystemSeparator = "";

        if (isWindows()) {
            sOperatingSystemSeparator = File.separatorChar + "" + File.separatorChar;
        } else if (isLinux()) {
            sOperatingSystemSeparator = File.separatorChar + "";
        }

        return sOperatingSystemSeparator;
    }

    private boolean isWindows() {
        return getOsName().startsWith("Windows");
    }

    private boolean isLinux() {
        return getOsName().startsWith("Linux");
    }
}
