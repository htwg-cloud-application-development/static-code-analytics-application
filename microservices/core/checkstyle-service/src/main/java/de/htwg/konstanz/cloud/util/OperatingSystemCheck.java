package de.htwg.konstanz.cloud.util;

import java.io.File;

/*  To provide independent data accesses this class checks the used operating system
    and offers the file system file separator for all other classes
 */
public class OperatingSystemCheck {

    // return the operating system name with the help of the System.getProperty("os.name")
    private String getOsName() {
        String sOperatingSystem = "";

        if ("".equals(sOperatingSystem)) {
            sOperatingSystem = System.getProperty("os.name");
        }

        return sOperatingSystem;
    }

    // build the actually needed file separator for windows or linux system
    public String getOperatingSystemSeparator() {
        String sOperatingSystemSeparator = "";

        if (isWindows()) {
            // Windows File Separator: \\
            sOperatingSystemSeparator = File.separatorChar + "" + File.separatorChar;
        } else if (isLinux()) {
            // Unix File Separator: /
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
