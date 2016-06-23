package de.htwg.konstanz.cloud.service;

import com.amazonaws.util.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.w3c.dom.Element;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;

@Component
public class Util {
    private static final Logger LOG = LoggerFactory.getLogger(Util.class);

    private final OperatingSystemCheck oOperatingSystemCheck = new OperatingSystemCheck();

    private final String sFileSeparator = oOperatingSystemCheck.getOperatingSystemSeparator();

    File checkLocalSrcDir(String sLocalDirectory) {
        File mainDir;/* Check if local /src-dir exists */

        if (new File(sLocalDirectory + sFileSeparator + "src").exists()) {
            mainDir = new File(sLocalDirectory + sFileSeparator + "src");
            LOG.info("Local SRC directory found");
        } else {
            mainDir = new File(sLocalDirectory);
            LOG.info("There was no local SRC directory");
        }

        return mainDir;
    }

    List<String> getAllJavaFiles(String path, List<String> javaFiles) throws FileNotFoundException {
        //Crawler
        File root = new File(path);
        File[] list = root.listFiles();

        if (list != null) {
            for (File tmpFile : list) {
                if (tmpFile.isDirectory()) {
                    //Ignore Git-Dir
                    if (!tmpFile.getPath().contains(".git")) {
                        //rekursiv crawle
                        getAllJavaFiles(tmpFile.getPath(), javaFiles);
                    }
                } else {
                    //add Class-Files
                    if (tmpFile.getPath().endsWith(".java")) {
                        javaFiles.add(tmpFile.getPath());
                    }
                }
            }
        }

        return javaFiles;
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

    private int isParsable(String input) {
        int nValue;

        try {
            nValue = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            nValue = 0;
        }

        return nValue;
    }

    String getNonEmptyElement(Element eElement, String sAttributeName) {
        if (!eElement.getAttribute(sAttributeName).isEmpty()) {
            return eElement.getAttribute(sAttributeName);
        }
        return "";
    }

    int getParsableElement(Element eElement, String sAttribute) {
        return isParsable(eElement.getAttribute(sAttribute));
    }

    String removeUnnecessaryPathParts(String sFilePath) {
        String[] sFilePathSplitArray = sFilePath.split(oOperatingSystemCheck.getOperatingSystemSeparator());
        String sShortenPath = "";
        for (int nPathPos = 2; nPathPos < sFilePathSplitArray.length; nPathPos++) {
            if (nPathPos + 1 == sFilePathSplitArray.length) {
                /* last Part of the Path */
                sShortenPath += sFilePathSplitArray[nPathPos];
            } else {
                sShortenPath += sFilePathSplitArray[nPathPos] + sFileSeparator;
            }
        }

        return sShortenPath;
    }

    File createDirectory(String sDirectoy) {
        File dir = new File(sDirectoy);

        if (!dir.exists()) {
            dir.mkdir();
        }

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

    ArrayList<String> getRepositoriesFromRequestBody(@RequestBody String data) throws JSONException {
        JSONObject object = new JSONObject(data);
        JSONArray array = object.getJSONArray("repositories");
        ArrayList<String> repositories = new ArrayList<>();
        int len = array.length();
        for (int i = 0; i < len; i++) {
            repositories.add(array.getJSONObject(i).getString("repository"));
        }
        return repositories;
    }

    boolean checkIfDifferentReops(List<String> lFileList, String sCheckRepo){
        String[] sSplitCheck = sCheckRepo.split(sFileSeparator);
        for(String sFileRepo : lFileList){
            if(sFileRepo.contains(sSplitCheck[0])) {
                return false;
            }
        }
        return true;
    }
}
