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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

public class CheckGitRep {

    private List<Class> lFormattedClassList = new ArrayList<Class>();
    private File oRepoDir;
    private String sOS = null;

    public String startIt(String gitRepository) throws IOException, ParserConfigurationException, SAXException, InvalidRemoteException, TransportException, GitAPIException, MalformedURLException, NullPointerException
    {
        long lStartTime = System.currentTimeMillis();
        JSONObject oJsonResult = null;
        SeverityCounter oSeverityCounter = new SeverityCounter();

        checkLocalPMD();
        List<List<String>> lRepoList = downloadRepoAndGetPath(gitRepository);
        oJsonResult = runPMD(lRepoList, gitRepository, oSeverityCounter, lStartTime);
        if (oRepoDir != null)
        {
            FileUtils.deleteDirectory(oRepoDir);
        }

        return oJsonResult.toString();
    }

    public List<List<String>> downloadRepoAndGetPath(String gitRepo) throws InvalidRemoteException, TransportException, GitAPIException, MalformedURLException, NullPointerException {
        List<List<String>> list = new ArrayList<List<String>>();
        String directoryName = gitRepo.substring(gitRepo.lastIndexOf("/"), gitRepo.length() - 1).replace(".", "_");
        String localDirectory = "repositories/" + directoryName + "_" + System.currentTimeMillis() + "/";
        oRepoDir = new File(localDirectory);
        Git git = null;

        if(isValidRepository(new URIish(new URL(gitRepo))))
        {
            git = Git.cloneRepository()
                    .setURI(gitRepo)
                    .setDirectory(new File(localDirectory)).call();
        }

        File mainDir;
        if(new File(localDirectory +"/src").exists()){
            mainDir = new File(localDirectory + "/src");
        }else{
            mainDir = new File(localDirectory);
        }


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
        /* Closing Object that we can delete the whole directory later */
        git.getRepository().close();

        return list;
    }

    boolean isValidRepository(URIish repoUri) {
        if (repoUri.isRemote()) {
            return isValidRemoteRepository(repoUri);
        } else {
            return isValidLocalRepository(repoUri);
        }
    }

    boolean isValidLocalRepository(URIish repoUri) {
        boolean result;
        try {
            result = new FileRepository(repoUri.getPath()).getObjectDatabase().exists();
        } catch (IOException e) {
            result = false;
        }
        return result;
    }

    boolean isValidRemoteRepository(URIish repoUri) {
        boolean result;

        if (repoUri.getScheme().toLowerCase().startsWith("http") ) {
            String path = repoUri.getPath();
            URIish checkUri = repoUri.setPath(path);

            InputStream ins = null;
            try {
                URLConnection conn = new URL(checkUri.toString()).openConnection();

                conn.setReadTimeout(1000);
                ins = conn.getInputStream();
                result = true;
            } catch (FileNotFoundException e) {
                System.out.println("File not Foud");
                result=false;
            } catch (IOException e) {
                System.out.println("IOException");
                result = false;
                e.printStackTrace();
            } finally {
                try {
                    ins.close();
                }
                catch (Exception e)
                { /* ignore */ }
            }

        } else if (repoUri.getScheme().toLowerCase().startsWith("ssh") ) {

            RemoteSession ssh = null;
            Process exec = null;

            try {
                ssh = SshSessionFactory.getInstance().getSession(repoUri, null, FS.detect(), 1000);
                exec = ssh.exec("cd " + repoUri.getPath() + "; git rev-parse --git-dir", 1000);

                Integer exitValue = null;
                do {
                    try {
                        exitValue = exec.exitValue();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } while (exitValue == null);

                result = exitValue == 0;

            } catch (Exception e) {
                result = false;

            } finally {
                try { exec.destroy(); } catch (Exception e) { /* ignore */ }
                try { ssh.disconnect(); } catch (Exception e) { /* ignore */ }
            }

        } else {
            // TODO need to implement tests for other schemas
            result = true;
        }
        return result;
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

        if(isWindows() == true)
        {
            sStartScript = "pmd-bin-5.4.2\\bin\\pmd.bat";
        }
        else if(isLinux() == true)
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

    public String getOsName()
    {
        if(sOS == null)
        {
            sOS = System.getProperty("os.name");
        }

        return sOS;
    }
    private boolean isWindows()
    {
        return getOsName().startsWith("Windows");
    }

    private boolean isLinux()
    {
        return getOsName().startsWith("Linux");
    }

    public void formatList(List<List<String>> lRepoList) {
        for (List<String> aLRepoList : lRepoList) {
            Class oClass = null;
            String sOperatingSystem = "";

            for (int nClassPos = 0; nClassPos < aLRepoList.size(); nClassPos++)
            {
                if(isWindows() == true)
                {
                    sOperatingSystem  = File.separatorChar + "" +File.separatorChar;
                }
                else if(isLinux())
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