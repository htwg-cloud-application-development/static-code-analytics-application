package de.htwg.konstanz.cloud.service;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;
import de.htwg.konstanz.cloud.model.Class;
import de.htwg.konstanz.cloud.model.Error;
import de.htwg.konstanz.cloud.model.SeverityCounter;
import org.apache.commons.configuration.SystemConfiguration;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;


public class PMD {

    private static final Logger LOG = LoggerFactory.getLogger(PMD.class);
    private List<Class> lFormattedClassList = new ArrayList<>();
    private File oRepoDir;
    private GIT oGit = new GIT();
    private SVN oSvn = new SVN();

    public String startIt(String gitRepository) throws IOException, ParserConfigurationException, SAXException, BadLocationException, InvalidRemoteException, TransportException, GitAPIException, MalformedURLException, NullPointerException
    {
        long lStartTime = System.currentTimeMillis();
        JSONObject oJsonResult;
        String sResult;
        SeverityCounter oSeverityCounter = new SeverityCounter();

        oJsonResult = determination(gitRepository, oSeverityCounter, lStartTime);

        if (oRepoDir != null) {
            FileUtils.deleteDirectory(oRepoDir);
        }
        else {
            LOG.info("Error: Local Directory is null!");
        }

        if(null == oJsonResult) {
            sResult = "Invalid Repository";
            LOG.info("Error: received invalid repository and JSON file");
        }
        else {
            sResult = oJsonResult.toString();
            LOG.info("Valid JSON result");
        }

        return sResult;
    }

    private JSONObject determination(String sRepoUrl, SeverityCounter oSeverityCounter, long lStartTime) throws IOException, BadLocationException, InvalidRemoteException, TransportException, GitAPIException, ParserConfigurationException, SAXException {
        JSONObject oJson = null;
        String sLocalDir;

        LOG.info("Repository URL: " + sRepoUrl);
        checkLocalPMD();

        /* SVN */
        if(sRepoUrl.contains("141.37.122.26")){
            /* URL needs to start with HTTP:// */
            if (!sRepoUrl.startsWith("http://")){
                sRepoUrl = "http://"+ sRepoUrl;
            }
            /* remove the last / */
            if (sRepoUrl.endsWith("/")){
                sRepoUrl = sRepoUrl.substring(0, sRepoUrl.length()-1);
            }

            LOG.info("SVN");
            sLocalDir = oSvn.downloadSVNRepo(sRepoUrl);
            oJson = (runPMD(generatePMDServiceData(sLocalDir), sRepoUrl,oSeverityCounter,lStartTime));
            oRepoDir = new File(sLocalDir);
        }
        /* GIT */
        else if(sRepoUrl.contains("github.com")){
            LOG.info("GIT");
            sLocalDir = oGit.downloadGITRepo(sRepoUrl);
            oJson = (runPMD(generatePMDServiceData(sLocalDir), sRepoUrl,oSeverityCounter,lStartTime));
            oRepoDir = new File(sLocalDir);
        }
        else {
            LOG.info("Repository URL has no valid SVN/GIT attributes. (" + sRepoUrl + ")");
        }

        return oJson;
    }

    private List<List<String>> generatePMDServiceData(String sLocalDirectory) {
        /* Generate Data for CheckstyleService */
        List<List<String>> list = new ArrayList<>();
        File mainDir;
        LOG.info("Local Directory: " + sLocalDirectory);

        /* Check if local /src-dir exists */
        if (new File(sLocalDirectory + "/src").exists()) {
            mainDir = new File(sLocalDirectory + "/src");
            LOG.info("Local SRC directory found");
        } else {
            mainDir = new File(sLocalDirectory);
            LOG.info("There was no local SRC directory");
        }

        /* List all files for CheckstyleService */
        if (mainDir.exists()) {
            File[] files = mainDir.listFiles();

            for (int i = 0; i < files.length; ++i) {

                File[] filesSub = new File(files[i].getPath()).listFiles();
                List<String> pathsSub = new ArrayList<String>();

                for (int j = 0; j < filesSub.length; ++j) {
                    if (filesSub[j].getPath().endsWith(".java")) {
                        pathsSub.add(filesSub[j].getPath());
                    }
                }

                list.add(pathsSub);
            }
        }

        return list;
    }

    private void checkLocalPMD() throws MalformedURLException, IOException, FileNotFoundException {
        ZIP oZIP = new ZIP();
        final String sPmdDir = "pmd-bin-5.4.2.zip";
        final String sDownloadPMD = "https://github.com/pmd/pmd/releases/download/pmd_releases%2F5.4.2/pmd-bin-5.4.2.zip";

        File oFile = new File(sPmdDir);
        ReadableByteChannel oReadableByteChannel = null;
        FileOutputStream oFileOutput = null;
        URL oURL = null;

        if (oFile.exists()) {
            LOG.info("PMD Directory already exists!");
        } else {
            LOG.info("PMD Directory does not exists, Starting download");
            oURL = new URL(sDownloadPMD);
            oReadableByteChannel = Channels.newChannel(oURL.openStream());
            oFileOutput = new FileOutputStream(sPmdDir);
            oFileOutput.getChannel().transferFrom(oReadableByteChannel, 0,Long.MAX_VALUE);

            oZIP.unzipFile(sPmdDir);
        }
    }

    private JSONObject runPMD(List<List<String>> lRepoList, String gitRepository, SeverityCounter oSeverityCounter, long lStartTime) throws ParserConfigurationException, SAXException, IOException {
        OperatingSystemCheck oOperatingSystemCheck = new OperatingSystemCheck();
        final String sRuleSetPath = "java-basic,java-design,java-codesize";
        String sStartScript = "";
        JSONObject oJson = null;

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

			/* Checkstyle Informationen eintragen */
            storePmdInformation(sFullPath + ".xml", nClassPos, oSeverityCounter);
        }

        if (lFormattedClassList != null)
        {
			/* Schoene einheitliche JSON erstellen */
            oJson = buildJSON(gitRepository, oSeverityCounter, lStartTime);
			/* JSON an Database weitersenden */
        }

        return oJson;
    }

    private boolean isParsable(String input) {
        boolean bParsable = true;

        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            bParsable = false;
        }

        return bParsable;
    }

    private void formatList(List<List<String>> lRepoList) {
        OperatingSystemCheck oOperatingSystemCheck = new OperatingSystemCheck();

        for (List<String> aLRepoList : lRepoList) {
            Class oClass = null;

            for (int nClassPos = 0; nClassPos < aLRepoList.size(); nClassPos++) {
                String[] sFullPathSplit_a = aLRepoList.get(nClassPos).split(oOperatingSystemCheck.getOperatingSystemSeparator());

                String sFullPath = aLRepoList.get(nClassPos);

                String sTmpClassName = sFullPathSplit_a[sFullPathSplit_a.length - 1];
                String sTmpExerciseName = sFullPathSplit_a[2];

                oClass = new Class(sTmpClassName, sFullPath, sTmpExerciseName);

                lFormattedClassList.add(oClass);
            }
        }
    }

    private void storePmdInformation(String sXmlPath, int nClassPos, SeverityCounter oSeverityCounter) throws ParserConfigurationException, SAXException, IOException {
        InputStream inputStream= new FileInputStream(sXmlPath);
        Reader reader = new InputStreamReader(inputStream,"UTF-8");
        InputSource is = new InputSource(reader);
        is.setEncoding("UTF-8");

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(is);

        NodeList nList = doc.getElementsByTagName("violation");

        for (int nNodePos = 0; nNodePos < nList.getLength(); nNodePos++) {

            Node nNode = nList.item(nNodePos);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;

				/* Default Values */
                int nLineBegin = 0;
                int nLineEnd = 0;
                int nColumnBegin = 0;
                int nColumnEnd = 0;
                int nPriority = 0;
                String sRule = "";
                String sClassName = "";
                String sPackage = "";
                String sRuleset = "";
                String sMessage = eElement.getFirstChild().getTextContent();

                if (isParsable(eElement.getAttribute("beginline"))) {
                    nLineBegin = Integer.parseInt(eElement.getAttribute("beginline"));
                }
                if (isParsable(eElement.getAttribute("endline"))) {
                    nLineEnd = Integer.parseInt(eElement.getAttribute("endline"));
                }
                if (isParsable(eElement.getAttribute("begincolumn"))) {
                    nColumnBegin = Integer.parseInt(eElement.getAttribute("begincolumn"));
                }
                if (isParsable(eElement.getAttribute("endcolumn"))) {
                    nColumnEnd = Integer.parseInt(eElement.getAttribute("endcolumn"));
                }
                if (isParsable(eElement.getAttribute("priority"))) {
                    nPriority = Integer.parseInt(eElement.getAttribute("priority"));

                    /* Count every Error Type we have found in the XML */
                    if(nPriority == 1) {
                        oSeverityCounter.incIgnoreCount();
                    }
                    else if(nPriority == 2) {
                        oSeverityCounter.incWarningCount();
                    }
                    else if(nPriority == 3) {
                        oSeverityCounter.incErrorCount();
                    }
                }
                if (!eElement.getAttribute("class").isEmpty()) {
                    sClassName = eElement.getAttribute("class");
                }
                if (!eElement.getAttribute("rule").isEmpty()) {
                    sRule = eElement.getAttribute("rule");
                }
                if (!eElement.getAttribute("ruleset").isEmpty()) {
                    sRuleset = eElement.getAttribute("ruleset");
                }
                if (!eElement.getAttribute("package").isEmpty()) {
                    sPackage = eElement.getAttribute("package");
                }

                Error oError = new Error(nLineBegin, nLineEnd, nColumnBegin, nColumnEnd, nPriority, sRule, sClassName, sPackage, sRuleset, sMessage);

                lFormattedClassList.get(nClassPos).getErrorList().add(oError);
            }
        }
    }

    private JSONObject buildJSON(String sRepo, SeverityCounter oSeverityCounter, long lStartTime) {
        List<Error> lTmpErrorList = new ArrayList<>();
        JSONObject oJsonRoot = new JSONObject();
        JSONObject oJsonExercise = new JSONObject();
        JSONArray lJsonClasses = new JSONArray();
        JSONArray lJsonExercises = new JSONArray();
        String sTmpExcerciseName = "";
        boolean bExcerciseChange = false;
        boolean bLastRun = false;
        boolean bExcerciseNeverChanged = true;

		/* add general information to the JSON object */
        oJsonRoot.put("repositoryUrl", sRepo);
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
            if (sExcerciseName.equals(sTmpExcerciseName) || sTmpExcerciseName.equals("")) {
                lTmpErrorList = lFormattedClassList.get(nClassPos).getErrorList();

                if(lTmpErrorList.size() > 0) {
                    JSONArray lJsonErrors = new JSONArray();
                    JSONObject oJsonClass = new JSONObject();

				    /* all Errors */
                    for (Error aLTmpErrorList : lTmpErrorList) {
                        JSONObject oJsonError = new JSONObject();

                        oJsonError.put("lineBegin", Integer.toString(aLTmpErrorList.getLineBegin()));
                        oJsonError.put("lineEnd",Integer.toString(aLTmpErrorList.getLineEnd()));
                        oJsonError.put("columnBegin", Integer.toString(aLTmpErrorList.getColumnBegin()));
                        oJsonError.put("columnEnd",Integer.toString(aLTmpErrorList.getColumnEnd()));
                        oJsonError.put("priority",Integer.toString(aLTmpErrorList.getPriority()));
                        oJsonError.put("rule",aLTmpErrorList.getRule());
                        oJsonError.put("class",aLTmpErrorList.getClassName());
                        oJsonError.put("package", aLTmpErrorList.getPackage());
                        oJsonError.put("ruleset",aLTmpErrorList.getRuleset());
                        oJsonError.put("message", aLTmpErrorList.getMessage());

                        lJsonErrors.put(oJsonError);
                    }

                    if(lJsonErrors.length() > 0) {
                        sTmpExcerciseName = sExcerciseName;

                        oJsonClass.put("filepath", lFormattedClassList.get(nClassPos).getFullPath());
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
                if(lFormattedClassList.get(nClassPos).getErrorList().size() > 0) {
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

        long lEndTime   = System.currentTimeMillis();
        long lTotalTime = (lEndTime - lStartTime);

        oJsonRoot.put("totalExpendedTime", lTotalTime);
        oJsonRoot.put("assignments", lJsonExercises);

        return oJsonRoot;
    }
}
