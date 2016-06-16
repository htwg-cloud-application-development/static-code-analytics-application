package de.htwg.konstanz.cloud.service;

import java.io.File;

class OperatingSystemCheck {

    private String getOsName() {
        String sOs = "";

        if("".equals(sOs)) {
            sOs = System.getProperty("os.name");
        }

        return sOs;
    }

    String getOperatingSystemSeparator() {
        String sOperatingSystemSeparator = "";

        if(isWindows()) {
            sOperatingSystemSeparator  = File.separatorChar + "" + File.separatorChar;
        }
        else if(isLinux()) {
            sOperatingSystemSeparator = File.separatorChar + "";
        }

        return sOperatingSystemSeparator;
    }

    boolean isWindows() {
        return getOsName().startsWith("Windows");
    }

    boolean isLinux() {
        return getOsName().startsWith("Linux");
    }
}
