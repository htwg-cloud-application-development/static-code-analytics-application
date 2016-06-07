package de.htwg.konstanz.cloud.service;

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

    public boolean isWindows()
    {
        return getOsName().startsWith("Windows");
    }

    public boolean isLinux()
    {
        return getOsName().startsWith("Linux");
    }
}
