package de.htwg.konstanz.cloud.util;

import java.io.File;

public class OperatingSystemCheck {

    private String getOsName() {
        String sOs = "";

        if("".equals(sOs)) {
            sOs = System.getProperty("os.name");
        }

        return sOs;
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
