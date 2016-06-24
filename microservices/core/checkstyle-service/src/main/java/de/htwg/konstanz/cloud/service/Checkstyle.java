package de.htwg.konstanz.cloud.service;

import de.htwg.konstanz.cloud.model.Class;
import de.htwg.konstanz.cloud.model.Error;
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

    private List<Class> lFormattedClassList;

    private final Util oUtil = new Util();

    private File oRepoDir;

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

        oJsonResult = determination(gitRepository, lStartTime);
        if (oRepoDir == null) {
            LOG.info("Error: Local Directory is null!");
        } else {
            FileUtils.deleteDirectory(oRepoDir);
        }

        sResult = oUtil.checkJsonResult(oJsonResult);

        return sResult;
    }

    private JSONObject determination(String sRepoUrl, long lStartTime) throws
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
            oJson = (checkStyle(generateCheckStyleServiceData(sLocalDir), sRepoUrl, lStartTime, ""));
            oRepoDir = new File(sLocalDir);
        }
        /* Git Checkout */
        else if (sRepoUrl.contains("github.com")) {
            LOG.info("Git");
            sLocalDirArray = oGit.downloadGitRepo(sRepoUrl);
            oJson = (checkStyle(generateCheckStyleServiceData(sLocalDirArray[0]), sRepoUrl, lStartTime, sLocalDirArray[1]));
            oRepoDir = new File(sLocalDirArray[0]);
        } else {
            LOG.info("Repository URL has no valid Svn/Git attributes. (" + sRepoUrl + ")");
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
            //fill list with relevant Data
            if(files != null) {
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
        if(list.isEmpty()){
            //Unregular Repo
            LOG.info("unregular repository");
            List<String> javaFiles=new ArrayList<>();
            list.add(walk(localDirectory,javaFiles));
        }

        return list;
    }


    private List<String> walk(String path, List<String> javaFiles) {
        //crawl Method to detect .java Files
        File root = new File(path);
        File[] list = root.listFiles();

        if (list != null) {
            for (File f : list) {
                if (f.isDirectory()) {
                    //ignore git folder (Speedreasons)
                    if (!f.getPath().contains(".git")) {
                            //Crawling
                            walk(f.getPath(),javaFiles);
                    }
                } else {
                    //Add .java Files to List
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
        oJson = buildJson(gitRepository, lStartTime, sLastUpdateTime);

        return oJson;
    }

    private void formatList(List<List<String>> lRepoList) {
        for (List<String> sRepoListInList : lRepoList) {
            Class oClass;

            for (String sRepo : sRepoListInList) {
                String[] sFullPathSplitArray = sRepo.split(oOperatingSystemCheck.getOperatingSystemSeparator());

                String sTmpClassName = sFullPathSplitArray[sFullPathSplitArray.length - 1];
                String sTmpExerciseName = sFullPathSplitArray[2];

                oClass = new Class(sTmpClassName, sRepo, sTmpExerciseName);

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
            readAndSaveAttributes(nClassPos, nList, nNodePos);
        }
    }

    private void readAndSaveAttributes(int nClassPos, NodeList nList, int nNodePos) {
        Node nNode = nList.item(nNodePos);

        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
            Element eElement = (Element) nNode;

            int nLine = oUtil.getParsableElement(eElement, "line");
            int nColumn = oUtil.getParsableElement(eElement, "column");

            String sSeverity = oUtil.getNonEmptyElement(eElement, "severity");
            lFormattedClassList.get(nClassPos).incErrorType(sSeverity);

            String sMessage = oUtil.getNonEmptyElement(eElement, "message");
            String sSource = oUtil.getNonEmptyElement(eElement, "source");

            Error oError = new Error(nLine, nColumn, sSeverity, sMessage, sSource);

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
        LOG.info("Number of Errors: " + nTmpErrorCount);
        oJsonRoot.put("numberOfWarnings", nTmpWarningCount);
        LOG.info("Number of Warnings: " + nTmpWarningCount);
        oJsonRoot.put("numberOfIgnores", nTmpIgnoreCounter);
        LOG.info("Number of Ignores: " + nTmpIgnoreCounter);

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
                    setSeverityCounters(oJsonClass, nClassPos);

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

    private void setSeverityCounters(JSONObject oJsonClass, int nClassPos) {
        oJsonClass.put("numberOfErros", lFormattedClassList.get(nClassPos).getErrorCount());
        oJsonClass.put("numberOfWarnings", lFormattedClassList.get(nClassPos).getWarningCount());
        oJsonClass.put("numberOfIgnores", lFormattedClassList.get(nClassPos).getIgnoreCount());
    }
}