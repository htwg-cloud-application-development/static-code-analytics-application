package de.htwg.konstanz.cloud.service;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

import java.io.File;
import java.io.IOException;
import java.util.List;

class Util {
    private static final Logger LOG = LoggerFactory.getLogger(Util.class);

    private final OperatingSystemCheck oOperatingSystemCheck = new OperatingSystemCheck();

    private int isParsable(String input) {
        int nValue;

        try {
            nValue = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            nValue = 0;
        }

        return nValue;
    }

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

    int execCommand(String sPmdCommand) {
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

    List<String> getAllJavaFiles(String path, List<String> javaFiles) {
        //crawl Method to detect .java Files
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

    File checkLocalSrcDir(String sLocalDirectory) {
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

    String checkJsonResult(JSONObject oJsonResult) {
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


    int getParsableElement(Element eElement, String sAttribute) {
        return isParsable(eElement.getAttribute(sAttribute));
    }

    String getNonEmptyElement(Element eElement, String sAttributeName) {
        if (!eElement.getAttribute(sAttributeName).isEmpty()) {
            return eElement.getAttribute(sAttributeName);
        }
        return "";
    }

}
