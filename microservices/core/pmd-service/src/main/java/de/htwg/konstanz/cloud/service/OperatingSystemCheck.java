package de.htwg.konstanz.cloud.service;

import java.io.File;

public class OperatingSystemCheck {

    private String getOsName() {
        String sOS = "";

        if("".equals(sOS)) {
            sOS = System.getProperty("os.name");
        }

        return sOS;
    }

    public String getOperatingSystemSeparator() {
        String sOperatingSystemSeparator = "";

        if(isWindows()) {
            sOperatingSystemSeparator  = File.separatorChar + "" + File.separatorChar;
        }
        else if(isLinux()) {
            sOperatingSystemSeparator = File.separatorChar + "";
        }

        return sOperatingSystemSeparator;
    }

    public boolean isWindows() {
        return getOsName().startsWith("Windows");
    }

    public boolean isLinux() {
        return getOsName().startsWith("Linux");
    }
}
