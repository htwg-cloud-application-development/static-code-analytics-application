package de.htwg.konstanz.cloud.service;

import de.htwg.konstanz.cloud.model.Class;
import de.htwg.konstanz.cloud.model.Error;
import de.htwg.konstanz.cloud.model.SeverityCounter;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.errors.GitAPIException;
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
import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;

class Checkstyle {
    private static final Logger LOG = LoggerFactory.getLogger(Checkstyle.class);

    private final OperatingSystemCheck oOperatingSystemCheck = new OperatingSystemCheck();

    private final List<Class> lFormattedClassList = new ArrayList<>();

    private final Util oUtil = new Util();

    private File oRepoDir;

    private final Git1 oGit = new Git1();

    private final Svn1 oSvn = new Svn1();

    private static final String SVN_IP_C = "141.37.122.26";

    String startIt(String gitRepository) throws IOException, ParserConfigurationException,
                                                SAXException, GitAPIException, BadLocationException {
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

        sResult = oUtil.checkJsonResult(oJsonResult);

        return sResult;
    }

    private JSONObject determination(String sRepoUrl, SeverityCounter oSeverityCounter, long lStartTime) throws
            IOException, BadLocationException, GitAPIException, ParserConfigurationException, SAXException {
        JSONObject oJson = null;
        String sLocalDir;
        StringBuilder oStringBuilder = new StringBuilder();

        LOG.info("Repository URL: " + sRepoUrl);
        checkLocalCheckstyle();

        /* Svn1 Checkout */
        if (sRepoUrl.contains(SVN_IP_C)) {
            /* URL needs to start with HTTP:// */
            if (!sRepoUrl.startsWith("http://")) {
                oStringBuilder.append("http://").append(sRepoUrl);
            }
            /* remove the last / */
            if (sRepoUrl.endsWith("/")) {
                oStringBuilder.append(sRepoUrl.substring(0, sRepoUrl.length() - 1));
            }

            LOG.info("Svn1");
            sLocalDir = oSvn.downloadSvnRepo(oStringBuilder.toString());
            oJson = (checkStyle(generateCheckStyleServiceData(oStringBuilder.toString()), sRepoUrl, oSeverityCounter, lStartTime));
            oRepoDir = new File(sLocalDir);
        }
        /* Git1 Checkout */
        else if (sRepoUrl.contains("github.com")) {
            LOG.info("Git1");
            sLocalDir = oGit.downloadGITRepo(sRepoUrl);
            oJson = (checkStyle(generateCheckStyleServiceData(sLocalDir), sRepoUrl, oSeverityCounter, lStartTime));
            oRepoDir = new File(sLocalDir);
        } else {
            LOG.info("Repository URL has no valid Svn1/Git1 attributes. (" + sRepoUrl + ")");
        }

        return oJson;
    }

    private List<List<String>> generateCheckStyleServiceData(String localDirectory) {
        /* Generate Data for CheckstyleService */
        ArrayList<List<String>>  list = new ArrayList<>();
        File mainDir;
        LOG.info("Local Directory: " + localDirectory);

        /* Structure according to the specifications  */
        mainDir = oUtil.checkLocalSrcDir(localDirectory);

        /* List all files for CheckstyleService */
        if (mainDir.exists()) {
            File[] files = mainDir.listFiles();

            if(files != null) {
                for (File file : files) {

                    File[] filesSub = new File(file.getPath()).listFiles();
                    List<String> pathsSub = new ArrayList<>();

                    if (filesSub != null) {
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
        if(list.isEmpty()){
            LOG.info("unregular repository");
            List<String> javaFiles=new ArrayList<>();
            list.add(walk(localDirectory,javaFiles));
        }

        return list;
    }

    private List<String> walk(String path, List<String> javaFiles) {
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
                    }
                }
            }
        }
        return javaFiles;
    }

    private void checkLocalCheckstyle() throws IOException {
        final String sCheckstyleJar = "checkstyle-6.17-all.jar";
        final String sDownloadCheckStyleJar = "http://downloads.sourceforge.net/project/checkstyle/checkstyle/6.17/checkstyle-6.17-all.jar?r=https%3A%2F%2Fsourceforge.net%2Fp%2Fcheckstyle%2Factivity%2F%3Fpage%3D0%26limit%3D100&ts=1463416596&use_mirror=vorboss";
        File oFile = new File(sCheckstyleJar);
        ReadableByteChannel oReadableByteChannel;
        FileOutputStream oFileOutput;
        URL oURL;

        if (oFile.exists()) {
            LOG.info("Checkstyle .jar already exists! (" + oFile.toString() + ")");
        } else {
            LOG.info("Checkstyle .jar does not exist, starting download");
            oURL = new URL(sDownloadCheckStyleJar);
            oReadableByteChannel = Channels.newChannel(oURL.openStream());
            oFileOutput = new FileOutputStream(sCheckstyleJar);
            oFileOutput.getChannel().transferFrom(oReadableByteChannel, 0, Long.MAX_VALUE);
        }
    }

    private JSONObject checkStyle(List<List<String>> lRepoList, String gitRepository, SeverityCounter oSeverityCounter,
                                  long lStartTime) throws ParserConfigurationException, SAXException, IOException {
        final String sCheckStylePath = "checkstyle-6.17-all.jar";
        final String sRuleSetPath = "google_checks_modified.xml";
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

            oUtil.execCommand(sCheckStyleCommand);

			/* store Checkstyle Informationen in the global List */
            storeCheckstyleInformation(sFullPath + ".xml", nClassPos, oSeverityCounter);
        }

        /* generate JSON File */
        oJson = buildJson(gitRepository, oSeverityCounter, lStartTime);

        return oJson;
    }

    private void formatList(List<List<String>> lRepoList) {

        for (List<String> RepoListInList : lRepoList) {
            Class oClass;

            for (String sRepo : RepoListInList) {
                String[] sFullPathSplit_a = sRepo.split(oOperatingSystemCheck.getOperatingSystemSeparator());

                String sTmpClassName = sFullPathSplit_a[sFullPathSplit_a.length - 1];
                String sTmpExerciseName = sFullPathSplit_a[2];

                oClass = new Class(sTmpClassName, sRepo, sTmpExerciseName);

                lFormattedClassList.add(oClass);
            }
        }
    }

    private void storeCheckstyleInformation(String sXmlPath, int nClassPos, SeverityCounter oSeverityCounter)
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
            readAndSaveAttributes(nClassPos, oSeverityCounter, nList, nNodePos);
        }
    }

    private void readAndSaveAttributes(int nClassPos, SeverityCounter oSeverityCounter, NodeList nList, int nNodePos) {
        Node nNode = nList.item(nNodePos);

        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
            Element eElement = (Element) nNode;

            int nLine = oUtil.getParsableElement(eElement, "line");
            int nColumn = oUtil.getParsableElement(eElement, "column");

            String sSeverity = oUtil.getNonEmptyElement(eElement, "severity");
            oUtil.incErrorType(oSeverityCounter, sSeverity);

            String sMessage = oUtil.getNonEmptyElement(eElement, "message");
            String sSource = oUtil.getNonEmptyElement(eElement, "source");

            Error oError = new Error(nLine, nColumn, sSeverity, sMessage, sSource);

            lFormattedClassList.get(nClassPos).getErrorList().add(oError);
        }
    }

    private JSONObject buildJson(String sRepo, SeverityCounter oSeverityCounter, long lStartTime) {
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
        LOG.info("Number of Errors: " + oSeverityCounter.getErrorCount());
        oJsonRoot.put("numberOfWarnings", oSeverityCounter.getWarningCount());
        LOG.info("Number of Warnings: " + oSeverityCounter.getWarningCount());
        oJsonRoot.put("numberOfIgnores", oSeverityCounter.getIgnoreCount());
        LOG.info("Number of Ignores: " + oSeverityCounter.getIgnoreCount());

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
                JSONArray lJsonErrors = new JSONArray();
                JSONObject oJsonClass = new JSONObject();

				/* all Errors */
                for (Error oError : lTmpErrorList) {
                    JSONObject oJsonError = new JSONObject();

                    oJsonError.put("line", Integer.toString(oError.getErrorAtLine()));
                    oJsonError.put("column", Integer.toString(oError.getColumn()));
                    oJsonError.put("severity", oError.getSeverity());
                    oJsonError.put("message", oError.getMessage());
                    oJsonError.put("source", oError.getSource());

                    lJsonErrors.put(oJsonError);
                }

                if(lJsonErrors.length() > 0) {
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
        LOG.info("Total expended time: " + lTotalTime);
        oJsonRoot.put("assignments", lJsonExercises);

        return oJsonRoot;
    }
}