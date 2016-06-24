package de.htwg.konstanz.cloud.service;

import java.io.File;

class OperatingSystemCheck {

    private String getOsName() {
        String sOperatingSystem = "";

        if ("".equals(sOperatingSystem)) {
            sOperatingSystem = System.getProperty("os.name");
        }

        return sOperatingSystem;
    }

    String getOperatingSystemSeparator() {
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
