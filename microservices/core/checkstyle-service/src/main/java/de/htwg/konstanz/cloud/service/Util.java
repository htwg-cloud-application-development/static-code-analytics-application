package de.htwg.konstanz.cloud.service;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

import java.io.File;
import java.io.IOException;

class Util {
    private static final Logger LOG = LoggerFactory.getLogger(Util.class);

    private final OperatingSystemCheck oOperatingSystemCheck = new OperatingSystemCheck();

    private boolean isParsable(String input) {
        boolean bParsable = true;

        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            bParsable = false;
        }

        return bParsable;
    }

    String removeUnnecessaryPathParts(String sFilePath) {
        String[] sFilePathSplitArray = sFilePath.split(oOperatingSystemCheck.getOperatingSystemSeparator());
        String sShortenPath = "";
        for(int nPathPos = 2; nPathPos < sFilePathSplitArray.length; nPathPos++) {
            if(nPathPos+1 == sFilePathSplitArray.length) {
                        /* last Part of the Path */
                sShortenPath += sFilePathSplitArray[nPathPos];
            }
            else {
                sShortenPath += sFilePathSplitArray[nPathPos] + "\\";
            }
        }

        return sShortenPath;
    }

    void execCommand(String sPmdCommand) {
        try {
            Runtime runtime = Runtime.getRuntime();
            Process proc = runtime.exec(sPmdCommand);

            try {
                proc.waitFor();
            } catch (InterruptedException e) {
                //TODO
            }
        } catch (IOException ex) {
            //TODO
        }
    }

    File checkLocalSrcDir(String sLocalDirectory) {
        File mainDir;/* Check if local /src-dir exists */

        if (new File(sLocalDirectory + "/src").exists()) {
            mainDir = new File(sLocalDirectory + "/src");
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
        if (isParsable(eElement.getAttribute(sAttribute))) {
            return Integer.parseInt(eElement.getAttribute(sAttribute));
        }
        return 0;
    }

    String getNonEmptyElement(Element eElement, String sAttributeName) {
        if (!eElement.getAttribute(sAttributeName).isEmpty()) {
            return eElement.getAttribute(sAttributeName);
        }
        return "";
    }

}
