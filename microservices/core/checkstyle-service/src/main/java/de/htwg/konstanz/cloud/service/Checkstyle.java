package de.htwg.konstanz.cloud.service;

import de.htwg.konstanz.cloud.model.Class;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.swing.text.BadLocationException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;

class Checkstyle {
    private static final Logger LOG = LoggerFactory.getLogger(Checkstyle.class);

    private final OperatingSystemCheck oOperatingSystemCheck = new OperatingSystemCheck();

    private List<Class> lFormattedClassList;

    private final Util oUtil = new Util();

    private File oRepoDir;

    private final OwnJson oOwnJson = new OwnJson();

    private final Git oGit = new Git();

    private final Svn oSvn = new Svn();

    private final String svn_ip_c;

    private final String sRuleSetPath;

    public Checkstyle(String svn_ip_c, String sRuleSetPath) {
        this.svn_ip_c = svn_ip_c;
        this.sRuleSetPath = sRuleSetPath;
    }

    String startIt(String gitRepository) throws IOException, ParserConfigurationException,
            SAXException, GitAPIException, BadLocationException {
        lFormattedClassList = new ArrayList<>();
        long lStartTime = System.currentTimeMillis();
        JSONObject oJsonResult;
        String sResult;

        oJsonResult = determineVersionControlSystem(gitRepository, lStartTime);
        if (oRepoDir == null) {
            LOG.info("Error: Local Directory is null!");
        } else {
            FileUtils.deleteDirectory(oRepoDir);
        }

        sResult = oUtil.checkJsonResult(oJsonResult);

        return sResult;
    }

    private JSONObject determineVersionControlSystem(String sRepoUrl, long lStartTime) throws
            IOException, BadLocationException, GitAPIException, ParserConfigurationException, SAXException {
        JSONObject oJson = null;
        String sLocalDir;
        String[] sLocalDirArray;
        StringBuilder oStringBuilder = new StringBuilder();

        LOG.info("Repository URL: " + sRepoUrl);
        checkLocalCheckstyle();

        /* Svn Checkout */
        if (sRepoUrl.contains(this.svn_ip_c)) {
            /* URL needs to start with HTTP:// */
            if (!sRepoUrl.startsWith("http://")) {
                oStringBuilder.append("http://");
            }
            /* remove the last / */
            if (sRepoUrl.endsWith("/")) {
                oStringBuilder.append(sRepoUrl.substring(0, sRepoUrl.length() - 1));
            } else {
                oStringBuilder.append(sRepoUrl);
            }

            LOG.info("Svn");
            sLocalDir = oSvn.downloadSvnRepo(oStringBuilder.toString());
            /* Last Update Time SVN empty */
            oJson = checkStyle(generateCheckStyleData(sLocalDir), sRepoUrl, lStartTime, "");
            oRepoDir = new File(sLocalDir);
        }
        /* Git Checkout */
        else if (sRepoUrl.contains("github.com")) {
            LOG.info("Git");
            sLocalDirArray = oGit.downloadGitRepo(sRepoUrl);
            oJson = checkStyle(generateCheckStyleData(sLocalDirArray[0]), sRepoUrl, lStartTime, sLocalDirArray[1]);
            oRepoDir = new File(sLocalDirArray[0]);
        } else {
            LOG.info("Repository URL has no valid Svn/Git attributes. (" + sRepoUrl + ")");
        }

        return oJson;
    }

    private List<List<String>> generateCheckStyleData(String localDirectory) {
        /* Generate Data for CheckstyleService */
        ArrayList<List<String>> list = new ArrayList<>();
        File mainDir;
        LOG.info("Local Directory: " + localDirectory);

        /* Structure according to the specifications  */
        mainDir = oUtil.checkLocalSrcDir(localDirectory);

        /* List all files for CheckstyleService */
        if (mainDir.exists()) {
            File[] files = mainDir.listFiles();
            //fill list with relevant Data
            if (files != null) {
                for (File file : files) {
                    //Head-Dir
                    File[] filesSub = new File(file.getPath()).listFiles();
                    List<String> pathsSub = new ArrayList<>();

                    if (filesSub != null) {
                        //Class-Files
                        for (File aFilesSub : filesSub) {
                            if (aFilesSub.getPath().endsWith(".java")) {
                                pathsSub.add(aFilesSub.getPath());
                            }
                        }
                    }
                    if (!pathsSub.isEmpty()) {
                        list.add(pathsSub);
                    }
                }
            }
        }
        /* Other Structure Workaround */
        if (list.isEmpty()) {
            //Unregular Repo
            LOG.info("unregular repository");
            List<String> javaFiles = new ArrayList<>();
            list.add(oUtil.getAllJavaFiles(localDirectory, javaFiles));
        }

        return list;
    }

    private void checkLocalCheckstyle() throws IOException {
        final String sCheckstyleJar = "checkstyle-6.17-all.jar";
        final String sDownloadCheckStyleJar = "http://downloads.sourceforge.net/project/checkstyle/checkstyle/6.17/checkstyle-6.17-all.jar?r=https%3A%2F%2Fsourceforge.net%2Fp%2Fcheckstyle%2Factivity%2F%3Fpage%3D0%26limit%3D100&ts=1463416596&use_mirror=vorboss";
        File oFile = new File(sCheckstyleJar);
        ReadableByteChannel oReadableByteChannel;
        FileOutputStream oFileOutput;
        URL oUrl;

        if (oFile.exists()) {
            LOG.info("Checkstyle .jar already exists! (" + oFile.toString() + ")");
        } else {
            LOG.info("Checkstyle .jar does not exist, starting download");
            oUrl = new URL(sDownloadCheckStyleJar);
            oReadableByteChannel = Channels.newChannel(oUrl.openStream());
            oFileOutput = new FileOutputStream(sCheckstyleJar);
            oFileOutput.getChannel().transferFrom(oReadableByteChannel, 0, Long.MAX_VALUE);
        }
    }

    private JSONObject checkStyle(List<List<String>> lRepoList, String gitRepository, long lStartTime, String sLastUpdateTime)
            throws ParserConfigurationException, SAXException, IOException {
        final String sCheckStylePath = "checkstyle-6.17-all.jar";
        JSONObject oJson;

		/* Listeninhalt kuerzen, um JSON vorbereiten */
        formatList(lRepoList);

        for (int nClassPos = 0; nClassPos < lFormattedClassList.size(); nClassPos++) {
            String sFullPath = lFormattedClassList.get(nClassPos).getFullPath();

            if (sFullPath.endsWith(".java")) {
                sFullPath = sFullPath.substring(0, sFullPath.length() - 5);
            }

            String sCheckStyleCommand = "java -jar " + sCheckStylePath + " -c " + sRuleSetPath + " " + sFullPath
                    + ".java -f xml -o " + sFullPath + ".xml";
            LOG.info("Checkstyle execution path: " + sCheckStyleCommand);

            int nReturnCode = oUtil.execCommand(sCheckStyleCommand);
            LOG.info("Process Return Code: " + nReturnCode);

            switch(nReturnCode) {
                case 0: {
                    /* store Checkstyle Informationen in the global List */
                    storeCheckstyleInformation(sFullPath + ".xml", nClassPos);
                }
                case -2: {
                    //TODO: Fehler case
                }
            }
        }

        /* generate JSON File */
        oJson = oOwnJson.buildJson(gitRepository, lStartTime, sLastUpdateTime, lFormattedClassList);

        return oJson;
    }

    private void formatList(List<List<String>> lRepoList) {
        for (List<String> sRepoListInList : lRepoList) {
            Class oClass;
            for (String sRepo : sRepoListInList) {
                String[] sFullPathSplitArray = sRepo.split(oOperatingSystemCheck.getOperatingSystemSeparator());
                String sTmpExerciseName = sFullPathSplitArray[2];

                oClass = new Class(sRepo, sTmpExerciseName);

                lFormattedClassList.add(oClass);
            }
        }
    }

    private void storeCheckstyleInformation(String sXmlPath, int nClassPos)
            throws ParserConfigurationException, SAXException, IOException {
        InputStream inputStream = new FileInputStream(sXmlPath);
        Reader reader = new InputStreamReader(inputStream, "UTF-8");
        InputSource is = new InputSource(reader);
        is.setEncoding("UTF-8");

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(is);

        NodeList nList = doc.getElementsByTagName("error");

        for (int nNodePos = 0; nNodePos < nList.getLength(); nNodePos++) {
            oOwnJson.readAndSaveAttributes(nClassPos, nList, nNodePos, lFormattedClassList);
        }
    }
}