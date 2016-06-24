package de.htwg.konstanz.cloud.util;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Util {
    private static final Logger LOG = LoggerFactory.getLogger(Util.class);

    private final OperatingSystemCheck oOperatingSystemCheck = new OperatingSystemCheck();

    //trys to parse a String into an int. if its no possible the function returns an default value of zero
    private int isParsable(String input) {
        int nValue;

        try {
            nValue = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            nValue = 0;
        }

        return nValue;
    }

    //this method removes some unnecesarry parts of the filepath
    String removeUnnecessaryPathParts(String sFilePath) {
        String[] sFilePathSplitArray = sFilePath.split(oOperatingSystemCheck.getOperatingSystemSeparator());
        String sShortenPath = "";
        for (int nPathPos = 2; nPathPos < sFilePathSplitArray.length; nPathPos++) {
            if (nPathPos + 1 == sFilePathSplitArray.length) {
                /* last Part of the Path */
                sShortenPath += sFilePathSplitArray[nPathPos];
            } else {
                sShortenPath += sFilePathSplitArray[nPathPos] + "\\";
            }
        }

        return sShortenPath;
    }

    //execution of commands within the command line interface, java provides an own runtime for it
    public int execCommand(String sPmdCommand) {
        //the return code of the process mades if possible to check if the execution was successful or not
        int nReturnCode = 0;

        try {
            Runtime runtime = Runtime.getRuntime();
            Process proc = runtime.exec(sPmdCommand);

            try {
                nReturnCode = proc.waitFor();
            } catch (InterruptedException e) {
                //TODO
            }
        } catch (IOException ex) {
            //TODO
        }

        return nReturnCode;
    }

    public List<String> getAllJavaFiles(String path, List<String> javaFiles) {
        //crawl Method to detect all .java Files in a local path
        File root = new File(path);
        File[] list = root.listFiles();
        if (list != null) {
            for (File f : list) {
                if (f.isDirectory()) {
                    //ignore git folder (Speedreasons)
                    if (!f.getPath().contains(".git")) {
                        //Crawling
                        getAllJavaFiles(f.getPath(),javaFiles);
                    }
                } else {
                    //Add .java Files to List
                    if (f.getPath().endsWith(".java")) {
                        javaFiles.add(f.getPath());
                    }
                }
            }
        }
        return javaFiles;
    }

    public File checkLocalSrcDir(String sLocalDirectory) {
        File mainDir;/* Check if local /src-dir exists */

        if (new File(sLocalDirectory + oOperatingSystemCheck.getOperatingSystemSeparator() + "src").exists()) {
            mainDir = new File(sLocalDirectory + oOperatingSystemCheck.getOperatingSystemSeparator() + "src");
            LOG.info("Local SRC directory found");
        } else {
            mainDir = new File(sLocalDirectory);
            LOG.info("There was no local SRC directory");
        }

        return mainDir;
    }

    //to catch nullpointer this method checks the json result
    public String checkJsonResult(JSONObject oJsonResult) {
        String sResult;
        if (null == oJsonResult) {
            sResult = "Invalid Repository";
            LOG.info("Error: received invalid repository and JSON file");
        } else {
            sResult = oJsonResult.toString();
            LOG.info("Valid JSON result");
        }
        return sResult;
    }

    //try to parse a string into an int
    int getParsableElement(Element eElement, String sAttribute) {
        return isParsable(eElement.getAttribute(sAttribute));
    }

    //check if the string parameter is empty (returns 0) or not (returns the attribute)
    String getNonEmptyElement(Element eElement, String sAttributeName) {
        if (!eElement.getAttribute(sAttributeName).isEmpty()) {
            return eElement.getAttribute(sAttributeName);
        }
        return "";
    }

}
