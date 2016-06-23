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
import java.util.ArrayList;
import java.util.List;

public class Pmd {

    Util util = new Util();

    private static final Logger LOG = LoggerFactory.getLogger(Pmd.class);

    private List<Class> lFormattedClassList;

    private final OperatingSystemCheck oOperatingSystemCheck = new OperatingSystemCheck();

    private File oRepoDir;

    private final Git oGit = new Git();

    private final Svn oSvn = new Svn();

    private final OwnJson oOwnJson = new OwnJson();

    private final String svnServerIp;
    private final String ruleSetPath;

    public Pmd(String svnServerIp, String ruleSetPath) {
        this.svnServerIp = svnServerIp;
        this.ruleSetPath = ruleSetPath;
    }


    String startIt(String gitRepository) throws IOException, ParserConfigurationException, SAXException,
            BadLocationException, GitAPIException, NullPointerException {
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

        sResult = util.checkJsonResult(oJsonResult);

        return sResult;
    }

    private JSONObject determineVersionControlSystem(String sRepoUrl, long lStartTime)
            throws IOException, BadLocationException, GitAPIException, ParserConfigurationException, SAXException {
        JSONObject oJson = null;
        String sLocalDir;
        String[] sLocalDirArray;
        StringBuilder oStringBuilder = new StringBuilder();

        LOG.info("Repository URL: " + sRepoUrl);
        util.checkLocalPmd();
        oRepoDir = util.createDirectory("repositories");

        /* Svn Checkout */
        if (sRepoUrl.contains(svnServerIp)) {
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
            oJson = runPmd(generatePmdData(sLocalDir), sRepoUrl, lStartTime, "");
            oRepoDir = new File(sLocalDir);
        }
        /* Git Checkout */
        else if (sRepoUrl.contains("github.com")) {
            LOG.info("Git");
            sLocalDirArray = oGit.downloadGitRepo(sRepoUrl);
            oJson = runPmd(generatePmdData(sLocalDirArray[0]), sRepoUrl, lStartTime, sLocalDirArray[1]);
            oRepoDir = new File(sLocalDirArray[0]);
        } else {
            LOG.info("Repository URL has no valid Svn/Git attributes. (" + sRepoUrl + ")");
        }

        return oJson;
    }

    private List<List<String>> generatePmdData(String sLocalDirectory) throws FileNotFoundException {
        /* Generate Data for CheckstyleService */
        List<List<String>> list = new ArrayList<>();
        File mainDir;
        LOG.info("Local Directory: " + sLocalDirectory);
        //Check if Src-Dir exists
        mainDir = util.checkLocalSrcDir(sLocalDirectory);

        /* List all files for CheckstyleService */
        if (mainDir.exists()) {
            File[] files = mainDir.listFiles();

            if (files != null && files.length<20) {

                for (File file : mainDir.listFiles()) {
                    if (file != null) {
                        //Head-Dirs
                        File[] filesSub = new File(file.getPath()).listFiles();
                        List<String> pathsSub = new ArrayList<>();

                        if (filesSub != null) {
                            for (File aFilesSub : filesSub) {
                                //Java-Files
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
        }

        checkUnregularRepository(sLocalDirectory, list);

        return list;
    }

    private void checkUnregularRepository(String sLocalDirectory, List<List<String>> list) throws FileNotFoundException {
        /* Other Structure Workaround */
        if (list.isEmpty()) {
            //Unregular Repo
            LOG.info("unregular repository");
            List<String> javaFiles = new ArrayList<>();
            list.add(util.getAllJavaFiles(sLocalDirectory, javaFiles));
        }
    }

    private JSONObject runPmd(List<List<String>> lRepoList, String gitRepository, long lStartTime, String sLastUpdateTime)
            throws ParserConfigurationException, SAXException, IOException {

        String sStartScript = "";

        if (oOperatingSystemCheck.isWindows()) {
            sStartScript = "pmd-bin-5.4.2\\bin\\pmd.bat";
        } else if (oOperatingSystemCheck.isLinux()) {
            sStartScript = "pmd-bin-5.4.2/bin/run.sh pmd";
        }

		/* Listeninhalt kuerzen, um JSON vorbereiten */
        formatList(lRepoList);

        for (int nClassPos = 0; nClassPos < lFormattedClassList.size(); nClassPos++) {
            String sFullPath = lFormattedClassList.get(nClassPos).getFullPath();

            if (sFullPath.endsWith(".java")) {
                sFullPath = sFullPath.substring(0, sFullPath.length() - 5);
            }

            String sPmdCommand = sStartScript + " -d " + sFullPath + ".java -f xml -failOnViolation false "
                    + "-encoding UTF-8 -rulesets " + ruleSetPath + " -r " + sFullPath + ".xml";
            LOG.info("Pmd execution path: " + sPmdCommand);

            util.execCommand(sPmdCommand);

			/* Checkstyle Informationen eintragen */
            storePmdInformation(sFullPath + ".xml", nClassPos);
        }

        /* Schoene einheitliche JSON erstellen */
        return oOwnJson.buildJson(gitRepository, lStartTime, sLastUpdateTime, lFormattedClassList);
    }

    private void formatList(List<List<String>> lRepoList) {
        OperatingSystemCheck oOperatingSystemCheck = new OperatingSystemCheck();

        for (List<String> lRepoListInList : lRepoList) {
            Class oClass;

            for (String sRepo : lRepoListInList) {
                String[] sFullPathSplitArray = sRepo.split(oOperatingSystemCheck.getOperatingSystemSeparator());

                String sTmpExerciseName = sFullPathSplitArray[2];
                oClass = new Class(sRepo, sTmpExerciseName);

                lFormattedClassList.add(oClass);
            }
        }
    }

    private void storePmdInformation(String sXmlPath, int nClassPos) throws
            ParserConfigurationException, SAXException, IOException {
        InputStream inputStream = new FileInputStream(sXmlPath);
        Reader reader = new InputStreamReader(inputStream, "UTF-8");
        InputSource is = new InputSource(reader);
        is.setEncoding("UTF-8");

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(is);

        NodeList nList = doc.getElementsByTagName("violation");

        for (int nNodePos = 0; nNodePos < nList.getLength(); nNodePos++) {
            oOwnJson.readAndSaveAttributes(nClassPos, nList, nNodePos, lFormattedClassList);
        }
    }
}
