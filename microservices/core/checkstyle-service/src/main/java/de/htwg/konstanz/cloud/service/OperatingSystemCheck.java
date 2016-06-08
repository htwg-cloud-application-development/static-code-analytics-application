package de.htwg.konstanz.cloud.service;

import java.io.File;

public class OperatingSystemCheck {

    private String getOsName()
    {
        String sOS = "";

        if(sOS.equals(""))
        {
            sOS = System.getProperty("os.name");
        }

        return sOS;
    }

    public String getOperatingSystemSeparator()
    {
        String sOperatingSystemSeparator = "";

        if(isWindows())
        {
            sOperatingSystemSeparator  = File.separatorChar + "" + File.separatorChar;
        }
        else if(isLinux())
        {
            sOperatingSystemSeparator = File.separatorChar + "";
        }

        return sOperatingSystemSeparator;
    }

    private boolean isWindows()
    {
        return getOsName().startsWith("Windows");
    }

    private boolean isLinux()
    {
        return getOsName().startsWith("Linux");
    }
}
