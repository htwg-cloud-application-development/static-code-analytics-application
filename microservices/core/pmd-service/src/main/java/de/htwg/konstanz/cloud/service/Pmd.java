package de.htwg.konstanz.cloud.service;

import de.htwg.konstanz.cloud.model.Class;
import de.htwg.konstanz.cloud.model.Error;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
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

@Component
public class Pmd {

    @Autowired
    Util util;

    private static final Logger LOG = LoggerFactory.getLogger(Pmd.class);

    private List<Class> lFormattedClassList;

    private final OperatingSystemCheck oOperatingSystemCheck = new OperatingSystemCheck();

    private File oRepoDir;

    private final Git oGit = new Git();

    private final Svn oSvn = new Svn();

    @Value("${app.config.svn.ip}")
    private String SVN_IP_C;

    @Value("${app.config.pmd.ruleset}")
    private String ruleSetPath;

    String startIt(String gitRepository) throws IOException, ParserConfigurationException, SAXException,
            BadLocationException, GitAPIException, NullPointerException {
        lFormattedClassList = new ArrayList<>();
        long lStartTime = System.currentTimeMillis();
        JSONObject oJsonResult;
        String sResult;

        oJsonResult = determination(gitRepository, lStartTime);

        if (oRepoDir == null) {
            LOG.info("Error: Local Directory is null!");
        } else {
            FileUtils.deleteDirectory(oRepoDir);
        }

        sResult = util.checkJsonResult(oJsonResult);

        return sResult;
    }

    private JSONObject determination(String sRepoUrl, long lStartTime)
            throws IOException, BadLocationException, GitAPIException, ParserConfigurationException, SAXException {
        JSONObject oJson = null;
        String sLocalDir;
        String[] sLocalDirArray;
        StringBuilder oStringBuilder = new StringBuilder();

        LOG.info("Repository URL: " + sRepoUrl);
        util.checkLocalPmd();
        oRepoDir = util.createDirectory("repositories");

        /* Svn Checkout */
        if (sRepoUrl.contains(SVN_IP_C)) {
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
            oJson = (runPmd(generatePmdServiceData(sLocalDir), sRepoUrl, lStartTime, ""));
            oRepoDir = new File(sLocalDir);
        }
        /* Git Checkout */
        else if (sRepoUrl.contains("github.com")) {
            LOG.info("Git");
            sLocalDirArray = oGit.downloadGitRepo(sRepoUrl);
            oJson = (runPmd(generatePmdServiceData(sLocalDirArray[0]), sRepoUrl, lStartTime, sLocalDirArray[1]));
            oRepoDir = new File(sLocalDirArray[0]);
        } else {
            LOG.info("Repository URL has no valid Svn/Git attributes. (" + sRepoUrl + ")");
        }

        return oJson;
    }

    private List<List<String>> generatePmdServiceData(String sLocalDirectory) throws FileNotFoundException {
        /* Generate Data for CheckstyleService */
        List<List<String>> list = new ArrayList<>();
        File mainDir;
        LOG.info("Local Directory: " + sLocalDirectory);
        //Check if Src-Dir exists
        mainDir = util.checkLocalSrcDir(sLocalDirectory);

        /* List all files for CheckstyleService */

            if (mainDir.exists()) {
                File[] files = mainDir.listFiles();

                if (files != null && mainDir!=null) {

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

        /* Other Structure Workaround */
        if (list.isEmpty()) {
            //Unregular Repo
            LOG.info("unregular repository");
            List<String> javaFiles = new ArrayList<>();
            list.add(walk(sLocalDirectory, javaFiles));
        }

        return list;
    }

    private List<String> walk(String path, List<String> javaFiles) throws FileNotFoundException {
        //Crawler
        File root = new File(path);
        File[] list = root.listFiles();

        if (list != null) {
            for (File tmpFile : list) {
                if (tmpFile.isDirectory()) {
                    //Ignore Git-Dir
                    if (!tmpFile.getPath().contains(".git")) {
                        //rekursiv crawle
                        walk(tmpFile.getPath(), javaFiles);
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
        JSONObject oJson = buildJson(gitRepository, lStartTime, sLastUpdateTime);
        /* JSON an Database weitersenden */

        return oJson;
    }

    private void formatList(List<List<String>> lRepoList) {
        OperatingSystemCheck oOperatingSystemCheck = new OperatingSystemCheck();

        for (List<String> lRepoListInList : lRepoList) {
            Class oClass;

            for (String sRepo : lRepoListInList) {
                String[] sFullPathSplitArray = sRepo.split(oOperatingSystemCheck.getOperatingSystemSeparator());

                String sTmpClassName = sFullPathSplitArray[sFullPathSplitArray.length - 1];
                String sTmpExerciseName = sFullPathSplitArray[2];

                oClass = new Class(sTmpClassName, sRepo, sTmpExerciseName);

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
            readAndSaveAttributes(nClassPos, nList, nNodePos);
        }
    }

    private void readAndSaveAttributes(int nClassPos, NodeList nList, int nNodePos) {
        Node nNode = nList.item(nNodePos);

        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
            Element eElement = (Element) nNode;

            String sMessage = eElement.getFirstChild().getTextContent();

            int nLineBegin = util.getParsableElement(eElement, "beginline");
            int nLineEnd = util.getParsableElement(eElement, "endline");
            int nColumnBegin = util.getParsableElement(eElement, "begincolumn");
            int nColumnEnd = util.getParsableElement(eElement, "endcolumn");
            int nPriority = util.getParsableElement(eElement, "priority");

            lFormattedClassList.get(nClassPos).incErrorType(nPriority);

            String sClassName = util.getNonEmptyElement(eElement, "class");
            String sRule = util.getNonEmptyElement(eElement, "rule");
            String sRuleset = util.getNonEmptyElement(eElement, "ruleset");
            String sPackage = util.getNonEmptyElement(eElement, "package");

            Error oError = new Error(nLineBegin, nLineEnd, nColumnBegin, nColumnEnd, nPriority,
                    sRule, sClassName, sPackage, sRuleset, sMessage);

            lFormattedClassList.get(nClassPos).getErrorList().add(oError);
        }
    }

    private JSONObject buildJson(String sRepo, long lStartTime, String sLastRepoUpdateTime) {
        List<Error> lTmpErrorList;
        JSONObject oJsonRoot = new JSONObject();
        JSONObject oJsonExercise = new JSONObject();
        JSONArray lJsonClasses = new JSONArray();
        JSONArray lJsonExercises = new JSONArray();
        String sTmpExcerciseName = "";
        boolean bExcerciseChange = false;
        boolean bLastRun = false;
        boolean bExcerciseNeverChanged = true;
        int nTmpErrorCount = 0;
        int nTmpWarningCount = 0;
        int nTmpIgnoreCounter = 0;

		/* add general information to the JSON object */
        oJsonRoot.put("repository", sRepo);

        /* get severities of the whole project */
        for (Class oFormattedClassList : lFormattedClassList) {
            nTmpErrorCount += oFormattedClassList.getErrorCount();
            nTmpWarningCount += oFormattedClassList.getWarningCount();
            nTmpIgnoreCounter += oFormattedClassList.getIgnoreCount();
        }
        oJsonRoot.put("numberOfErrors", nTmpErrorCount);
        oJsonRoot.put("numberOfWarnings", nTmpWarningCount);
        oJsonRoot.put("numberOfIgnores", nTmpIgnoreCounter);

        oJsonRoot.put("lastRepoUpdateTime", sLastRepoUpdateTime);
        LOG.info("Last Update Time: " + sLastRepoUpdateTime);

		/* all Classes */
        for (int nClassPos = 0; nClassPos < lFormattedClassList.size(); nClassPos++) {
            if (bExcerciseChange) {
                nClassPos--;
                bExcerciseChange = false;
            }

            String sExcerciseName = lFormattedClassList.get(nClassPos).getsExcerciseName();

			/* first run the TmpName is empty */
            if (sExcerciseName.equals(sTmpExcerciseName) || "".equals(sTmpExcerciseName)) {
                lTmpErrorList = lFormattedClassList.get(nClassPos).getErrorList();

                if (!lTmpErrorList.isEmpty()) {
                    JSONArray lJsonErrors = new JSONArray();
                    JSONObject oJsonClass = new JSONObject();

				    /* all Errors */
                    for (Error aLTmpErrorList : lTmpErrorList) {
                        JSONObject oJsonError = new JSONObject();

                        oJsonError.put("lineBegin", Integer.toString(aLTmpErrorList.getLineBegin()));
                        oJsonError.put("lineEnd", Integer.toString(aLTmpErrorList.getLineEnd()));
                        oJsonError.put("columnBegin", Integer.toString(aLTmpErrorList.getColumnBegin()));
                        oJsonError.put("columnEnd", Integer.toString(aLTmpErrorList.getColumnEnd()));
                        oJsonError.put("priority", Integer.toString(aLTmpErrorList.getPriority()));
                        oJsonError.put("rule", aLTmpErrorList.getRule());
                        oJsonError.put("class", aLTmpErrorList.getClassName());
                        oJsonError.put("package", aLTmpErrorList.getPackage());
                        oJsonError.put("ruleset", aLTmpErrorList.getRuleset());
                        oJsonError.put("message", aLTmpErrorList.getMessage());

                        lJsonErrors.put(oJsonError);
                    }

                    if (lJsonErrors.length() > 0) {
                        sTmpExcerciseName = sExcerciseName;

                        String sFilePath = util.removeUnnecessaryPathParts(lFormattedClassList.get(nClassPos).getFullPath());
                        oJsonClass.put("filepath", sFilePath);
                        oJsonClass.put("errors", lJsonErrors);
                        setPriorityCounters(oJsonClass, nClassPos);

                        lJsonClasses.put(oJsonClass);
                    }
                }

                /* last run if different exercises were found */
                if (bLastRun) {
                    oJsonExercise.put(sTmpExcerciseName, lJsonClasses);
                    lJsonExercises.put(oJsonExercise);
                }
                /* last run if there was just one exercise */
                if ((nClassPos + 1) == lFormattedClassList.size() && bExcerciseNeverChanged) {
                    oJsonExercise.put(sTmpExcerciseName, lJsonClasses);
                    lJsonExercises.put(oJsonExercise);
                }
            }
            /* swap for a different exercise */
            else {
                if (lFormattedClassList.get(nClassPos).getErrorList().size() > 0) {
                    oJsonExercise.put(sTmpExcerciseName, lJsonClasses);
                    lJsonExercises.put(oJsonExercise);
                    oJsonExercise = new JSONObject();
                    lJsonClasses = new JSONArray();
                    sTmpExcerciseName = lFormattedClassList.get(nClassPos).getsExcerciseName();
                    bExcerciseChange = true;
                    bExcerciseNeverChanged = false;

				    /* decrement the position to get the last class from the list */
                    if ((nClassPos + 1) == lFormattedClassList.size()) {
                        nClassPos--;
                        bExcerciseChange = false;
                        bLastRun = true;
                    }
                }
            }
        }

        long lEndTime = System.currentTimeMillis();
        long lTotalTime = (lEndTime - lStartTime);

        oJsonRoot.put("totalExpendedTime", lTotalTime);
        oJsonRoot.put("assignments", lJsonExercises);

        LOG.debug("Pmd Static Analysis check for Repo: " + sRepo);
        return oJsonRoot;
    }

    private void setPriorityCounters(JSONObject oJsonClass, int nClassPos) {
        oJsonClass.put("numberOfErros", lFormattedClassList.get(nClassPos).getErrorCount());
        oJsonClass.put("numberOfWarnings", lFormattedClassList.get(nClassPos).getWarningCount());
        oJsonClass.put("numberOfIgnores", lFormattedClassList.get(nClassPos).getIgnoreCount());
    }
}
