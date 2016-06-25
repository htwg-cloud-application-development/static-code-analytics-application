package de.htwg.konstanz.cloud.util;

import java.io.File;

/*  To provide independent data accesses this class checks the used operating system
    and offers the file system file separator for all other classes
 */
public class OperatingSystemCheck {

    /**
     * returns the operating system name with the help of the System.getProperty("os.name")
     * @return - Operating System Name
     */
    private String getOsName() {
        String sOs = "";

        if("".equals(sOs)) {
            sOs = System.getProperty("os.name");
        }

        return sOs;
    }

    /**
     * build the actually needed file separator for windows or linux system
     * @return - Operating System File Separator
     */
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

    /**
     * checks if the operating system is windows
     * @return - Flag which indicates yes/no
     */
    public boolean isWindows() {
        return getOsName().startsWith("Windows");
    }

    /**
     * checks if the operating system is linux
     * @return - Flag which indicates yes/no
     */
    public boolean isLinux() {
        return getOsName().startsWith("Linux");
    }
}
