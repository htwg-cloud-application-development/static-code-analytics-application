package de.htwg.konstanz.cloud.service;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;
import de.htwg.konstanz.cloud.model.Class;
import de.htwg.konstanz.cloud.model.Error;
import de.htwg.konstanz.cloud.model.SeverityCounter;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.RemoteSession;
import org.eclipse.jgit.transport.SshSessionFactory;
import org.eclipse.jgit.util.FS;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.net.URLEncoder;
import javax.swing.text.BadLocationException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;


public class PMD {

    private List<Class> lFormattedClassList = new ArrayList<Class>();
    private File oRepoDir;
    private String sOS = null;
    OperatingSystemCheck oOsCheck = new OperatingSystemCheck();
    GIT oGit = new GIT();
    SVN oSvn = new SVN();

    public String startIt(String gitRepository) throws IOException, ParserConfigurationException, SAXException, BadLocationException, InvalidRemoteException, TransportException, GitAPIException, MalformedURLException, NullPointerException
    {
        long lStartTime = System.currentTimeMillis();
        JSONObject oJsonResult = null;
        String sResult = "";
        SeverityCounter oSeverityCounter = new SeverityCounter();

        oJsonResult = determination(gitRepository, oSeverityCounter, lStartTime);
        if (oRepoDir != null)
        {
            FileUtils.deleteDirectory(oRepoDir);
        }

        if(null == oJsonResult)
        {
            sResult = "Invalid Repository";
        }
        else
        {
            sResult = oJsonResult.toString();
        }

        return sResult;
    }

    private JSONObject determination(String sRepoUrl, SeverityCounter oSeverityCounter, long lStartTime) throws IOException, BadLocationException, InvalidRemoteException, TransportException, GitAPIException, ParserConfigurationException, SAXException {
        JSONObject oJson = null;

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
            oJson = (runPMD(generateCheckStyleServiceData(oSvn.downloadSVNRepo(sRepoUrl)), sRepoUrl,oSeverityCounter,lStartTime));
        }

        /* GIT */
        if(sRepoUrl.contains("github.com")){
            oJson = (runPMD(generateCheckStyleServiceData(oGit.downloadGITRepo(sRepoUrl)), sRepoUrl,oSeverityCounter,lStartTime));
        }

        return oJson;
    }

    private List<List<String>> generateCheckStyleServiceData(String localDirectory) {
        /* Generate Data for CheckstyleService */
        List<List<String>> list = new ArrayList<List<String>>();
        File mainDir;

        /* Check if local /src-dir exists */
        if (new File(localDirectory + "/src").exists()) {
            mainDir = new File(localDirectory + "/src");
        } else {
            mainDir = new File(localDirectory);
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

    public boolean checkLocalPMD() throws MalformedURLException, IOException, FileNotFoundException
    {
        boolean bSuccess = false;
        final String sPmdDir = "pmd-bin-5.4.2.zip";
        final String sDownloadPMD = "https://github.com/pmd/pmd/releases/download/pmd_releases%2F5.4.2/pmd-bin-5.4.2.zip";

        File oFile = new File(sPmdDir);
        ReadableByteChannel oReadableByteChannel = null;
        FileOutputStream oFileOutput = null;
        URL oURL = null;

        if (oFile.exists())
        {
            System.out.println("PMD Directory already exists!");
            bSuccess = true;
        } else
        {
            System.out.println("PMD Directory doesnt exists, Starting download");
            oURL = new URL(sDownloadPMD);
            oReadableByteChannel = Channels.newChannel(oURL.openStream());
            oFileOutput = new FileOutputStream(sPmdDir);
            oFileOutput.getChannel().transferFrom(oReadableByteChannel, 0,Long.MAX_VALUE);
            bSuccess = true;

            unzipFile(sPmdDir);
        }

        return bSuccess;
    }

    public void unzipFile(String sZipFile)
    {
        try
        {
            ZipFile oZipFile = new ZipFile(sZipFile);

            if (!oZipFile.isEncrypted())
            {
                oZipFile.extractAll(System.getProperty("user.dir"));
            }

        }
        catch (ZipException e)
        {
            e.printStackTrace();
        }
    }

    public JSONObject runPMD(List<List<String>> lRepoList, String gitRepository, SeverityCounter oSeverityCounter, long lStartTime) throws ParserConfigurationException, SAXException, IOException
    {
        final String sRuleSetPath = "java-basic,java-design,java-codesize";
        String sStartScript = "";
        JSONObject oJson = null;

        if(oOsCheck.isWindows() == true)
        {
            sStartScript = "pmd-bin-5.4.2\\bin\\pmd.bat";
        }
        else if(oOsCheck.isLinux() == true)
        {
            sStartScript = "pmd-bin-5.4.2/bin/run.sh";
        }

		/* Listeninhalt kuerzen, um JSON vorbereiten */
        formatList(lRepoList);

        for (int nClassPos = 0; nClassPos < lFormattedClassList.size(); nClassPos++)
        {
            String sFullPath = lFormattedClassList.get(nClassPos).getFullPath();

            if (sFullPath.endsWith(".java"))
            {
                sFullPath = sFullPath.substring(0, sFullPath.length() - 5);
            }

            String sPmdCommand = sStartScript + " -d " + sFullPath + ".java -f xml -rulesets " + sRuleSetPath + " -r " + sFullPath + ".xml";

            try
            {
                Runtime runtime = Runtime.getRuntime();
                Process proc = runtime.exec(sPmdCommand);

                try
                {
                    proc.waitFor();
                } catch (InterruptedException e)
                {
                }
            } catch (IOException ex)
            {
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

    public boolean isParsable(String input) {
        boolean bParsable = true;

        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            bParsable = false;
        }

        return bParsable;
    }

    public void formatList(List<List<String>> lRepoList) {
        for (List<String> aLRepoList : lRepoList) {
            Class oClass = null;
            String sOperatingSystem = "";

            for (int nClassPos = 0; nClassPos < aLRepoList.size(); nClassPos++)
            {
                if(oOsCheck.isWindows() == true)
                {
                    sOperatingSystem  = File.separatorChar + "" +File.separatorChar;
                }
                else if(oOsCheck.isLinux())
                {
                    sOperatingSystem = File.separatorChar + "";
                }
                String[] sFullPathSplit_a = aLRepoList.get(nClassPos).split(sOperatingSystem );

                String sFullPath = aLRepoList.get(nClassPos);

                String sTmpClassName = sFullPathSplit_a[sFullPathSplit_a.length - 1];
                String sTmpExerciseName = sFullPathSplit_a[2];

                oClass = new Class(sTmpClassName, sFullPath, sTmpExerciseName);

                lFormattedClassList.add(oClass);
            }
        }
        System.out.println("DEBUG");
    }

    public void storePmdInformation(String sXmlPath, int nClassPos, SeverityCounter oSeverityCounter) throws ParserConfigurationException, SAXException, IOException {
        File oFileXML = new File(sXmlPath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(oFileXML);
        doc.getDocumentElement().normalize();

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
                    if(nPriority == 1)
                    {
                        oSeverityCounter.incIgnoreCount();
                    }
                    else if(nPriority == 2)
                    {
                        oSeverityCounter.incWarningCount();
                    }
                    else if(nPriority == 3)
                    {
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

    public JSONObject buildJSON(String sRepo, SeverityCounter oSeverityCounter, long lStartTime) {
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
        oJsonRoot.put("numberOfWarnings", oSeverityCounter.getWarningCount());
        oJsonRoot.put("numberOfIgnores", oSeverityCounter.getIgnoreCount());
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

                if(lJsonErrors.length() > 0)
                {
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
        oJsonRoot.put("assignments", lJsonExercises);

        return oJsonRoot;
    }
}