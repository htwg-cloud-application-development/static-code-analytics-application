package de.htwg.konstanz.cloud.service;

import de.htwg.konstanz.cloud.model.SeverityCounter;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class Util {
    private static final Logger LOG = LoggerFactory.getLogger(Util.class);

    private final OperatingSystemCheck oOperatingSystemCheck = new OperatingSystemCheck();

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

    boolean isParsable(String input) {
        boolean bParsable = true;

        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            bParsable = false;
        }

        return bParsable;
    }

    String getNonEmptyElement(Element eElement, String sAttributeName) {
        if (!eElement.getAttribute(sAttributeName).isEmpty()) {
            return eElement.getAttribute(sAttributeName);
        }
        return "";
    }

    void incErrorType(SeverityCounter oSeverityCounter, int nPriority) {
        /* Count every Error Type we have found in the XML */
        if (nPriority == 1) {
            oSeverityCounter.incIgnoreCount();
        } else if (nPriority == 2) {
            oSeverityCounter.incWarningCount();
        } else if (nPriority == 3) {
            oSeverityCounter.incErrorCount();
        }
    }

    int getParsableElement(Element eElement, String sAttribute) {
        if (isParsable(eElement.getAttribute(sAttribute))) {
            return Integer.parseInt(eElement.getAttribute(sAttribute));
        }
        return 0;
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

    File createDirectory(String sDirectoy) {
        File dir = new File(sDirectoy);
        if (!dir.exists())
            dir.mkdir();
        return dir;
    }

    void checkLocalPmd() throws IOException {
        Zip oZip = new Zip();
        final String sPmdDir = "pmd-bin-5.4.2.zip";
        final String sDownloadPmd = "https://github.com/pmd/pmd/releases/download/pmd_releases%2F5.4.2/pmd-bin-5.4.2.zip";

        File oFile = new File(sPmdDir);
        ReadableByteChannel oReadableByteChannel;
        FileOutputStream oFileOutput;
        URL oUrl;

        if (oFile.exists()) {
            LOG.info("Pmd Directory already exists!");
        } else {
            LOG.info("Pmd Directory does not exists, Starting download");
            oUrl = new URL(sDownloadPmd);
            oReadableByteChannel = Channels.newChannel(oUrl.openStream());
            oFileOutput = new FileOutputStream(sPmdDir);
            oFileOutput.getChannel().transferFrom(oReadableByteChannel, 0, Long.MAX_VALUE);

            oZip.unzipFile(sPmdDir);
        }
    }
}
