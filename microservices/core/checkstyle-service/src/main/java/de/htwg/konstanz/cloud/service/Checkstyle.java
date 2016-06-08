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
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.swing.text.BadLocationException;

public class Checkstyle {

    private static final Logger LOG = LoggerFactory.getLogger(Checkstyle.class);
    private List<Class> lFormattedClassList = new ArrayList<Class>();
    private File oRepoDir;
    private GIT oGit = new GIT();
    private SVN oSvn = new SVN();

    public String startIt(String gitRepository) throws IOException, ParserConfigurationException, SAXException, InvalidRemoteException, TransportException, GitAPIException, MalformedURLException, BadLocationException {
        long lStartTime = System.currentTimeMillis();
        JSONObject oJsonResult = null;
        String sResult = "";
        SeverityCounter oSeverityCounter = new SeverityCounter();

        oJsonResult = determination(gitRepository,oSeverityCounter,lStartTime );
        if (oRepoDir != null)
        {
            FileUtils.deleteDirectory(oRepoDir);
        }

        if(null == oJsonResult)
        {
            sResult = "Invalid Repository";
            LOG.info("Error: received invalid repository and JSON file");
        }
        else
        {
            sResult = oJsonResult.toString();
            LOG.info("Valid JSON result");
        }

        return sResult;
    }

    private JSONObject determination(String sRepoUrl, SeverityCounter oSeverityCounter, long lStartTime) throws IOException, BadLocationException, InvalidRemoteException, TransportException, GitAPIException, ParserConfigurationException, SAXException {
        JSONObject oJson = null;
        LOG.info("Repository URL: " + sRepoUrl);

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
            oJson = (checkStyle(generateCheckStyleServiceData(oSvn.downloadSVNRepo(sRepoUrl)), sRepoUrl,oSeverityCounter,lStartTime));
        }

        /* GIT */
        if(sRepoUrl.contains("github.com")){
            checkLocalCheckstyle();
            oJson = (checkStyle(generateCheckStyleServiceData(oGit.downloadGITRepo(sRepoUrl)), sRepoUrl,oSeverityCounter,lStartTime));
        }

        return oJson;
    }

    private List<List<String>> generateCheckStyleServiceData(String localDirectory) {
        /* Generate Data for CheckstyleService */
        List<List<String>> list = new ArrayList<List<String>>();
        File mainDir;
        LOG.info("Local Directory: " + localDirectory);

        /* Check if local /src-dir exists */
        if (new File(localDirectory + "/src").exists()) {
            mainDir = new File(localDirectory + "/src");
            LOG.info("Local SRC directory found");
        } else {
            mainDir = new File(localDirectory);
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

    private void checkLocalCheckstyle() throws IOException {
        final String sCheckstyleJar = "checkstyle-6.17-all.jar";
        final String sDownloadCheckStyleJar = "http://downloads.sourceforge.net/project/checkstyle/checkstyle/6.17/checkstyle-6.17-all.jar?r=https%3A%2F%2Fsourceforge.net%2Fp%2Fcheckstyle%2Factivity%2F%3Fpage%3D0%26limit%3D100&ts=1463416596&use_mirror=vorboss";

        File oFile = new File(sCheckstyleJar);
        ReadableByteChannel oReadableByteChannel = null;
        FileOutputStream oFileOutput = null;
        URL oURL = null;

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

    private JSONObject checkStyle(List<List<String>> lRepoList, String gitRepository, SeverityCounter oSeverityCounter, long lStartTime) throws ParserConfigurationException, SAXException, IOException {
        final String sCheckStylePath = "checkstyle-6.17-all.jar";
        final String sRuleSetPath = "google_checks_modified.xml";
        JSONObject oJson = null;

		/* Listeninhalt kuerzen, um JSON vorbereiten */
        formatList(lRepoList);

        for (int nClassPos = 0; nClassPos < lFormattedClassList.size(); nClassPos++) {
            String sFullPath = lFormattedClassList.get(nClassPos).getFullPath();

            if (sFullPath.endsWith(".java")) {
                sFullPath = sFullPath.substring(0, sFullPath.length() - 5);
            }

            String sCheckStyleCommand = "java -jar " + sCheckStylePath + " -c " + sRuleSetPath + " " + sFullPath + ".java -f xml -o " + sFullPath + ".xml";
            LOG.info("Checkstyle execution path: " + sCheckStyleCommand);

            try {
                Runtime runtime = Runtime.getRuntime();
                Process proc = runtime.exec(sCheckStyleCommand);

                try {
                    proc.waitFor();
                } catch (InterruptedException e) {
                    // TODO
                }
            } catch (IOException ex) {
                // TODO
            }

			/* store Checkstyle Informationen in the global List */
            storeCheckstyleInformation(sFullPath + ".xml", nClassPos, oSeverityCounter);
        }

        if (null != lFormattedClassList) {
			/* generate JSON File */
            oJson = buildJSON(gitRepository, oSeverityCounter, lStartTime);
        }

        return oJson;
    }

    private void storeCheckstyleInformation(String sXmlPath, int nClassPos, SeverityCounter oSeverityCounter) throws ParserConfigurationException, SAXException, IOException {
        InputStream inputStream= new FileInputStream(sXmlPath);
        Reader reader = new InputStreamReader(inputStream,"UTF-8");
        InputSource is = new InputSource(reader);
        is.setEncoding("UTF-8");

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(is);

        NodeList nList = doc.getElementsByTagName("error");

        for (int nNodePos = 0; nNodePos < nList.getLength(); nNodePos++) {

            Node nNode = nList.item(nNodePos);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
				
				/* Default Values */
                int nLine = 0;
                int nColumn = 0;
                String sSeverity = "";
                String sMessage = "";
                String sSource = "";

                if (isParsable(eElement.getAttribute("line"))) {
                    nLine = Integer.parseInt(eElement.getAttribute("line"));
                }
                if (isParsable(eElement.getAttribute("column"))) {
                    nColumn = Integer.parseInt(eElement.getAttribute("column"));
                }
                if (!eElement.getAttribute("severity").isEmpty()) {
                    sSeverity = eElement.getAttribute("severity");
                    
                    /* Count every Error Type we have found in the XML */
                    if(sSeverity.toLowerCase().equals("error"))
                    {
                        oSeverityCounter.incErrorCount();
                    }
                    else if(sSeverity.toLowerCase().equals("warning"))
                    {
                        oSeverityCounter.incWarningCount();
                    }
                    else if(sSeverity.toLowerCase().equals("ignore"))
                    {
                        oSeverityCounter.incIgnoreCount();
                    }
                }
                if (!eElement.getAttribute("message").isEmpty()) {
                    sMessage = eElement.getAttribute("message");
                }
                if (!eElement.getAttribute("source").isEmpty()) {
                    sSource = eElement.getAttribute("source");
                }

                Error oError = new Error(nLine, nColumn, sSeverity, sMessage, sSource);

                lFormattedClassList.get(nClassPos).getErrorList().add(oError);
            }
        }
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
        OperatingSystemCheck oOsCheck = new OperatingSystemCheck();

        for (List<String> aLRepoList : lRepoList) {
            Class oClass = null;
            String sOperatingSystemSeparator = "";

            for (int nClassPos = 0; nClassPos < aLRepoList.size(); nClassPos++)
            {
                if(oOsCheck.isWindows() == true)
                {
                    sOperatingSystemSeparator  = File.separatorChar + "" + File.separatorChar;
                }
                else if(oOsCheck.isLinux())
                {
                    sOperatingSystemSeparator = File.separatorChar + "";
                }
                String[] sFullPathSplit_a = aLRepoList.get(nClassPos).split(sOperatingSystemSeparator );

                String sFullPath = aLRepoList.get(nClassPos);

                String sTmpClassName = sFullPathSplit_a[sFullPathSplit_a.length - 1];
                String sTmpExerciseName = sFullPathSplit_a[2];

                oClass = new Class(sTmpClassName, sFullPath, sTmpExerciseName);

                lFormattedClassList.add(oClass);
            }
        }
    }

    private JSONObject buildJSON(String sRepo, SeverityCounter oSeverityCounter, long lStartTime) {
        List<Error> lTmpErrorList = new ArrayList<Error>();
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
        LOG.info("Number of Errors: " + oSeverityCounter.getErrorCount());
        oJsonRoot.put("numberOfWarnings", oSeverityCounter.getWarningCount());
        LOG.info("Number of Warnings: " + oSeverityCounter.getWarningCount());
        oJsonRoot.put("numberOfIgnores", oSeverityCounter.getIgnoreCount());
        LOG.info("Number of Ignores: " + oSeverityCounter.getIgnoreCount());
        //oJsonRoot.put("groupID", nGroupID);
        //oJsonRoot.put("name", sName);
		
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
                for (Error aLTmpErrorList : lTmpErrorList) {
                    JSONObject oJsonError = new JSONObject();

                    oJsonError.put("line", Integer
                            .toString(aLTmpErrorList.getErrorAtLine()));
                    oJsonError.put("column",
                            Integer.toString(aLTmpErrorList.getColumn()));
                    oJsonError.put("severity",
                            aLTmpErrorList.getSeverity());
                    oJsonError.put("message",
                            aLTmpErrorList.getMessage());
                    oJsonError.put("source", aLTmpErrorList.getSource());

                    lJsonErrors.put(oJsonError);
                }

                sTmpExcerciseName = sExcerciseName;

                oJsonClass.put("filepath", lFormattedClassList.get(nClassPos).getFullPath());
                oJsonClass.put("errors", lJsonErrors);
                lJsonClasses.put(oJsonClass);
				
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

        long lEndTime   = System.currentTimeMillis();
        long lTotalTime = (lEndTime - lStartTime);

        oJsonRoot.put("totalExpendedTime", lTotalTime);
        LOG.info("Total expended time: " + lTotalTime);
        oJsonRoot.put("assignments", lJsonExercises);

        return oJsonRoot;
    }
}