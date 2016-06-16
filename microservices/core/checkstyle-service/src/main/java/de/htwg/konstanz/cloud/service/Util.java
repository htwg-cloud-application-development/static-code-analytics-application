package de.htwg.konstanz.cloud.service;

import de.htwg.konstanz.cloud.model.SeverityCounter;
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

    String removeUnnecessaryPathParts(String sFilePath)
    {
        String[] sFilePathSplit_a = sFilePath.split(oOperatingSystemCheck.getOperatingSystemSeparator());
        String sShortenPath = "";
        for(int nPathPos = 2; nPathPos < sFilePathSplit_a.length; nPathPos++) {
            if(nPathPos+1 == sFilePathSplit_a.length) {
                        /* last Part of the Path */
                sShortenPath += sFilePathSplit_a[nPathPos];
            }
            else {
                sShortenPath += sFilePathSplit_a[nPathPos] + "\\";
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

    void incErrorType(SeverityCounter oSeverityCounter, String sSeverity) {
        /* Count every Error Type we have found in the XML */
        if (sSeverity.toLowerCase().equals("error")) {
            oSeverityCounter.incErrorCount();
        } else if (sSeverity.toLowerCase().equals("warning")) {
            oSeverityCounter.incWarningCount();
        } else if (sSeverity.toLowerCase().equals("ignore")) {
            oSeverityCounter.incIgnoreCount();
        }
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
