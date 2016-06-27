package de.htwg.konstanz.cloud.util;

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

    /**
     * checks if there is a local src directory
     * @param sLocalDirectory - local src directory that should be checked by this method
     * @return - the local directory as a File object
     */
    public File checkLocalSrcDir(String sLocalDirectory) {
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

    /**
     * find all java files in the local directory and put them into a big list
     * @param path - local directory were the method shall crawl for java files
     * @param javaFiles - existig javaFiles
     * @return - String list of all java files that were founded
     */
    public List<String> getAllJavaFiles(String path, List<String> javaFiles) {
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

    /**
     * execution of commands within the command line interface, java provides an own runtime for it
     * @param sPmdCommand command to execute as string
     * @return status code of execution
     */
    public int execCommand(String sPmdCommand) {
        int nReturnCode = 0;
        Process proc = null;

        try {
            Runtime runtime = Runtime.getRuntime();
            proc = runtime.exec(sPmdCommand);

            ReadStream s1 = new ReadStream("stdin", proc.getInputStream());
            ReadStream s2 = new ReadStream("stderr", proc.getErrorStream());

            s1.start();
            s2.start();

            nReturnCode = proc.waitFor();
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            if (proc != null) {
                proc.destroy();
            }
        }

        return nReturnCode;
    }

    /**
     * checks the json result to avoide null pointer exceptions
     * @param oJsonResult - generated json result
     * @return - valid or invalid string
     */
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

    /** trys to parse a String into an int
     * @param input - String that should be checked
     * @return - if its no possible the function returns an default value of zero
     */
    private int isParsable(String input) {
        int nValue;

        try {
            nValue = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            nValue = 0;
        }

        return nValue;
    }

    /**
     * checks if the string parameter is empty
     * @param eElement - xml element that will be checked
     * @param sAttributeName - Attribute of the xml element the function wants to evaluate
     * @return - returns an empty string or the attribute
     */
    String getNonEmptyElement(Element eElement, String sAttributeName) {
        if (!eElement.getAttribute(sAttributeName).isEmpty()) {
            return eElement.getAttribute(sAttributeName);
        }
        return "";
    }

    /**
     * parse a string into an int
     * @param eElement - xml element that will be checked
     * @param sAttribute - Attribute of the xml element the function wants to evaluate
     * @return - returns 0 (empty) or the attribute
     */
    public int getParsableElement(Element eElement, String sAttribute) {
        return isParsable(eElement.getAttribute(sAttribute));
    }

    /**
     * this method removes some unnecessary parts of the filepath
     * @param sFilePath - FilePath that should be minimized
     * @return - fixed FilePath
     */
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

    /**
     * this method creates a Directory
     * @param sDirectoy - DirPath that should be created
     * @return - created Directory
     */
    public File createDirectory(String sDirectoy) {
        File dir = new File(sDirectoy);

        if (!dir.exists()) {
            dir.mkdir();
        }

        return dir;
    }

    /**
     * this method checks the Local PMD
     * @return - created Directory
     * @throws IOException - throw for the handling in PMDService
     */
    public void checkLocalPmd() throws IOException {
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

    public ArrayList<String> getRepositoriesFromRequestBody(@RequestBody String data) throws JSONException {
        JSONObject object = new JSONObject(data);
        JSONArray array = object.getJSONArray("repositories");
        ArrayList<String> repositories = new ArrayList<>();
        int len = array.length();
        for (int i = 0; i < len; i++) {
            repositories.add(array.getJSONObject(i).getString("repository"));
        }
        return repositories;
    }

    public boolean checkIfDifferentReops(List<String> lFileList, String sCheckRepo) {
        String[] sSplitCheck = sCheckRepo.split(sFileSeparator);
        for (String sFileRepo : lFileList) {
            if (sFileRepo.contains(sSplitCheck[0])) {
                return false;
            }
        }
        return true;
    }
}
