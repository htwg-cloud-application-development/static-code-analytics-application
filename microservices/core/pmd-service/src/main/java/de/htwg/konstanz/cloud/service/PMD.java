package de.htwg.konstanz.cloud.service;

import de.htwg.konstanz.cloud.model.Class;
import de.htwg.konstanz.cloud.model.Error;
import de.htwg.konstanz.cloud.model.SeverityCounter;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;

public class PMD {

    private static final Logger LOG = LoggerFactory.getLogger(PMD.class);
    private final List<Class> lFormattedClassList = new ArrayList<>();
    private final OperatingSystemCheck oOperatingSystemCheck = new OperatingSystemCheck();
    private final Util oUtil = new Util();
    private File oRepoDir;
    private final GIT oGit = new GIT();
    private final SVN oSvn = new SVN();
    private static final String SVN_IP_C = "141.37.122.26";

    public String startIt(String gitRepository) throws IOException, ParserConfigurationException, SAXException, BadLocationException, InvalidRemoteException, TransportException, GitAPIException, NullPointerException {
        long lStartTime = System.currentTimeMillis();
        JSONObject oJsonResult;
        String sResult;
        SeverityCounter oSeverityCounter = new SeverityCounter();

        oJsonResult = determination(gitRepository, oSeverityCounter, lStartTime);

        if (oRepoDir == null) {
            LOG.info("Error: Local Directory is null!");
        } else {
            FileUtils.deleteDirectory(oRepoDir);
        }

        if (null == oJsonResult) {
            sResult = "Invalid Repository";
            LOG.info("Error: received invalid repository and JSON file");
        } else {
            sResult = oJsonResult.toString();
            LOG.info("Valid JSON result");
        }

        return sResult;
    }

    private JSONObject determination(String sRepoUrl, SeverityCounter oSeverityCounter, long lStartTime) throws IOException, BadLocationException, InvalidRemoteException, TransportException, GitAPIException, ParserConfigurationException, SAXException {
        JSONObject oJson = null;
        String sLocalDir;
        StringBuilder oStringBuilder = new StringBuilder();

        LOG.info("Repository URL: " + sRepoUrl);
        checkLocalPMD();

        /* SVN */
        if (sRepoUrl.contains(SVN_IP_C)) {
            /* URL needs to start with HTTP:// */
            if (!sRepoUrl.startsWith("http://")) {
                oStringBuilder.append("http://").append(sRepoUrl);
            }
            /* remove the last / */
            if (sRepoUrl.endsWith("/")) {
                oStringBuilder.append(sRepoUrl.substring(0, sRepoUrl.length() - 1));
            }

            LOG.info("SVN");
            sLocalDir = oSvn.downloadSVNRepo(oStringBuilder.toString());
            oJson = (runPMD(generatePMDServiceData(sLocalDir), oStringBuilder.toString(), oSeverityCounter, lStartTime));
            oRepoDir = new File(sLocalDir);
        }
        /* GIT */
        else if (sRepoUrl.contains("github.com")) {
            LOG.info("GIT");
            sLocalDir = oGit.downloadGITRepo(sRepoUrl);
            oJson = (runPMD(generatePMDServiceData(sLocalDir), sRepoUrl, oSeverityCounter, lStartTime));
            oRepoDir = new File(sLocalDir);
        } else {
            LOG.info("Repository URL has no valid SVN/GIT attributes. (" + sRepoUrl + ")");
        }

        return oJson;
    }

    private List<List<String>> generatePMDServiceData(String sLocalDirectory) throws FileNotFoundException {
        /* Generate Data for CheckstyleService */
        List<List<String>> list = new ArrayList<>();
        File mainDir;
        LOG.info("Local Directory: " + sLocalDirectory);

        mainDir = oUtil.checkLocalSrcDir(sLocalDirectory);

        /* List all files for CheckstyleService */
        if (mainDir.exists()) {
            File[] files = mainDir.listFiles();

            for (File file : files) {

                File[] filesSub = new File(file.getPath()).listFiles();
                List<String> pathsSub = new ArrayList<>();

                if(filesSub!=null) {
                    for (File aFilesSub : filesSub) {
                        if (aFilesSub.getPath().endsWith(".java")) {
                            pathsSub.add(aFilesSub.getPath());
                        }
                    }
                }

                if(!pathsSub.isEmpty()) {
                    list.add(pathsSub);
                }
            }
        }

        /* Other Structure Workaround */
        if(list.isEmpty()){
            LOG.info("divergent repository");
            List<String> javaFiles=new ArrayList<>();
            list.add(walk(sLocalDirectory,javaFiles));
        }

        return list;
    }

    private List<String> walk(String path, List<String> javaFiles) throws FileNotFoundException {
        File root = new File(path);
        File[] list = root.listFiles();
        
        if (list != null) {

            for (File f : list) {
                if (f.isDirectory()) {
                    if (!f.getPath().contains(".git")) {

                        walk(f.getPath(),javaFiles);
                    }
                } else {
                    if(f.getPath().endsWith(".java")) {
                        javaFiles.add(f.getPath());
                        LOG.info(f.getPath());
                    }
                }
            }
        }

        return javaFiles;
    }

    private void checkLocalPMD() throws MalformedURLException, IOException, FileNotFoundException {
        ZIP oZIP = new ZIP();
        final String sPmdDir = "pmd-bin-5.4.2.zip";
        final String sDownloadPMD = "https://github.com/pmd/pmd/releases/download/pmd_releases%2F5.4.2/pmd-bin-5.4.2.zip";

        File oFile = new File(sPmdDir);
        ReadableByteChannel oReadableByteChannel;
        FileOutputStream oFileOutput;
        URL oURL;

        if (oFile.exists()) {
            LOG.info("PMD Directory already exists!");
        } else {
            LOG.info("PMD Directory does not exists, Starting download");
            oURL = new URL(sDownloadPMD);
            oReadableByteChannel = Channels.newChannel(oURL.openStream());
            oFileOutput = new FileOutputStream(sPmdDir);
            oFileOutput.getChannel().transferFrom(oReadableByteChannel, 0, Long.MAX_VALUE);

            oZIP.unzipFile(sPmdDir);
        }
    }

    private JSONObject runPMD(List<List<String>> lRepoList, String gitRepository, SeverityCounter oSeverityCounter, long lStartTime) throws ParserConfigurationException, SAXException, IOException {
        final String sRuleSetPath = "java-basic,java-design,java-codesize";
        String sStartScript = "";
        JSONObject oJson;

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

            String sPmdCommand = sStartScript + " -d " + sFullPath + ".java -f xml -failOnViolation false -encoding UTF-8 -rulesets " + sRuleSetPath + " -r " + sFullPath + ".xml";
            LOG.info("PMD execution path: " + sPmdCommand);

            oUtil.execCommand(sPmdCommand);

			/* Checkstyle Informationen eintragen */
            storePmdInformation(sFullPath + ".xml", nClassPos, oSeverityCounter);
        }

        /* Schoene einheitliche JSON erstellen */
        oJson = buildJSON(gitRepository, oSeverityCounter, lStartTime);
        /* JSON an Database weitersenden */

        return oJson;
    }

    private void formatList(List<List<String>> lRepoList) {
        OperatingSystemCheck oOperatingSystemCheck = new OperatingSystemCheck();

        for (List<String> aLRepoList : lRepoList) {
            Class oClass;

            for (String anALRepoList : aLRepoList) {
                String[] sFullPathSplit_a = anALRepoList.split(oOperatingSystemCheck.getOperatingSystemSeparator());

                String sFullPath = anALRepoList;

                String sTmpClassName = sFullPathSplit_a[sFullPathSplit_a.length - 1];
                String sTmpExerciseName = sFullPathSplit_a[2];

                oClass = new Class(sTmpClassName, sFullPath, sTmpExerciseName);

                lFormattedClassList.add(oClass);
            }
        }
    }

    private void storePmdInformation(String sXmlPath, int nClassPos, SeverityCounter oSeverityCounter) throws ParserConfigurationException, SAXException, IOException {
        InputStream inputStream = new FileInputStream(sXmlPath);
        Reader reader = new InputStreamReader(inputStream, "UTF-8");
        InputSource is = new InputSource(reader);
        is.setEncoding("UTF-8");

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(is);

        NodeList nList = doc.getElementsByTagName("violation");

        for (int nNodePos = 0; nNodePos < nList.getLength(); nNodePos++) {
            readAndSaveAttributes(nClassPos, oSeverityCounter, nList, nNodePos);
        }
    }

    private void readAndSaveAttributes(int nClassPos, SeverityCounter oSeverityCounter, NodeList nList, int nNodePos) {
        Node nNode = nList.item(nNodePos);

        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
            Element eElement = (Element) nNode;

            String sMessage = eElement.getFirstChild().getTextContent();

            int nLineBegin = oUtil.getParsableElement(eElement, "beginline");
            int nLineEnd = oUtil.getParsableElement(eElement, "endline");
            int nColumnBegin = oUtil.getParsableElement(eElement, "begincolumn");
            int nColumnEnd = oUtil.getParsableElement(eElement, "endcolumn");
            int nPriority = oUtil.getParsableElement(eElement, "priority");

            oUtil.incErrorType(oSeverityCounter, nPriority);

            String sClassName = oUtil.getNonEmptyElement(eElement, "class");
            String sRule = oUtil.getNonEmptyElement(eElement, "rule");
            String sRuleset = oUtil.getNonEmptyElement(eElement, "ruleset");
            String sPackage = oUtil.getNonEmptyElement(eElement, "package");

            Error oError = new Error(nLineBegin, nLineEnd, nColumnBegin, nColumnEnd, nPriority, sRule, sClassName, sPackage, sRuleset, sMessage);

            lFormattedClassList.get(nClassPos).getErrorList().add(oError);
        }
    }

    private JSONObject buildJSON(String sRepo, SeverityCounter oSeverityCounter, long lStartTime) {
        List<Error> lTmpErrorList;
        JSONObject oJsonRoot = new JSONObject();
        JSONObject oJsonExercise = new JSONObject();
        JSONArray lJsonClasses = new JSONArray();
        JSONArray lJsonExercises = new JSONArray();
        String sTmpExcerciseName = "";
        boolean bExcerciseChange = false;
        boolean bLastRun = false;
        boolean bExcerciseNeverChanged = true;

		/* add general information to the JSON object */
        oJsonRoot.put("repository", sRepo);
        oJsonRoot.put("numberOfErrors", oSeverityCounter.getErrorCount());
        oJsonRoot.put("numberOfWarnings", oSeverityCounter.getWarningCount());
        oJsonRoot.put("numberOfIgnores", oSeverityCounter.getIgnoreCount());

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

                        String sFilePath = oUtil.removeUnnecessaryPathParts(lFormattedClassList.get(nClassPos).getFullPath());
                        oJsonClass.put("filepath", sFilePath);
                        oJsonClass.put("errors", lJsonErrors);
                        lJsonClasses.put(oJsonClass);
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

        LOG.debug("PMD Static Analysis check for Repo: " + sRepo);
        return oJsonRoot;
    }
}
