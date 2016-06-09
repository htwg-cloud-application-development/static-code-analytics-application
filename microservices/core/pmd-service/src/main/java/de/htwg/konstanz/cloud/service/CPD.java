package de.htwg.konstanz.cloud.service;

import de.htwg.konstanz.cloud.model.Class;
import de.htwg.konstanz.cloud.model.Duplication;
import de.htwg.konstanz.cloud.model.Error;
import de.htwg.konstanz.cloud.model.SeverityCounter;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
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
import org.xml.sax.SAXException;

import javax.swing.text.BadLocationException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.*;

public class CPD {

    private List<Duplication> lDuplications = new ArrayList<Duplication>();
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

        checkLocalCPD();

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
            oJson = (runCPD(generateCPDServiceData(oSvn.downloadSVNRepo(sRepoUrl)), sRepoUrl,oSeverityCounter,lStartTime));
        }

        return oJson;
    }

    private String generateCPDServiceData(String localDirectory) {
        /* Generate Data for CheckstyleService */
        File mainDir;

        /* Check if local /src-dir exists */
        if (new File(localDirectory + "/src").exists()) {
            mainDir = new File(localDirectory + "/src");
        } else {
            mainDir = new File(localDirectory);
        }

        /* List all files for CPDService */
        if (mainDir.exists()) {
            return mainDir.getPath();
        }
        return null;
    }

    public boolean checkLocalCPD() throws MalformedURLException, IOException, FileNotFoundException
    {
        boolean bSuccess = false;
        final String sCPDDir = "pmd-bin-5.4.2.zip";
        final String sDownloadCPD = "https://github.com/pmd/pmd/releases/download/pmd_releases%2F5.4.2/pmd-bin-5.4.2.zip";

        File oFile = new File(sCPDDir);
        ReadableByteChannel oReadableByteChannel = null;
        FileOutputStream oFileOutput = null;
        URL oURL = null;

        if (oFile.exists())
        {
            System.out.println("CPD Directory already exists!");
            bSuccess = true;
        } else
        {
            System.out.println("CPD Directory doesnt exists, Starting download");
            oURL = new URL(sDownloadCPD);
            oReadableByteChannel = Channels.newChannel(oURL.openStream());
            oFileOutput = new FileOutputStream(sCPDDir);
            oFileOutput.getChannel().transferFrom(oReadableByteChannel, 0,Long.MAX_VALUE);
            bSuccess = true;

            unzipFile(sCPDDir);
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

    private static final Logger LOG = LoggerFactory.getLogger(CPD.class);

    public JSONObject runCPD(String sMainPath, String gitRepository, SeverityCounter oSeverityCounter, long lStartTime) throws ParserConfigurationException, SAXException, IOException {
        final String sRuleSetPath = "java-basic,java-design,java-codesize";
        String sStartScript = "";
        JSONObject oJson = null;

        if (oOsCheck.isWindows() == true) {
            sStartScript = "pmd-bin-5.4.2\\bin\\CPD.bat";
        } else if (oOsCheck.isLinux() == true) {
            sStartScript = "pmd-bin-5.4.2/bin/run.sh";
        }

        String sCPDCommand = sStartScript + " --minimum-token --files " + sMainPath + " --skip-lexicial-errors --format xml > "  + sMainPath + ".xml";
        LOG.info(sCPDCommand);

        try {
            Runtime runtime = Runtime.getRuntime();
            Process proc = runtime.exec(sCPDCommand);

            try {
                proc.waitFor();
            } catch (InterruptedException e) {
            }
        } catch (IOException ex) {
        }

			/* Checkstyle Informationen eintragen */
        storeCPDInformation(sMainPath + ".xml");

        if (lDuplications != null) {
			/* Schoene einheitliche JSON erstellen */
            oJson = buildJSON(sMainPath, lStartTime);
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

    public void storeCPDInformation(String sXmlPath) throws ParserConfigurationException, SAXException, IOException {
        File oFileXML = new File(sXmlPath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(oFileXML);
        doc.getDocumentElement().normalize();

        NodeList nList = doc.getElementsByTagName("dupliaction");

        for (int nNodePos = 0; nNodePos < nList.getLength(); nNodePos++) {

            Node nNode = nList.item(nNodePos);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eNodeElement = (Element) nNode;

				/* Default Values */
                int nLinesCount = 0;
                int nTokens = 0;
                List<String> lInvolvedData = new ArrayList<String>();
                String sCodeFragment ="";


                //Duplication Infos
                if (isParsable(eNodeElement.getAttribute("lines"))) {
                    nLinesCount = Integer.parseInt(eNodeElement.getAttribute("lines"));
                }
                if (isParsable(eNodeElement.getAttribute("tokens"))) {
                    nTokens = Integer.parseInt(eNodeElement.getAttribute("tokens"));
                }

                //CheckFileNodes
                NodeList nNodeFiles = eNodeElement.getElementsByTagName("file");
                for (int nNodeFilePos = 0; nNodeFilePos < nNodeFiles.getLength(); nNodeFilePos++) {
                    Node nNodeFile = nNodeFiles.item(nNodeFilePos);
                    if (nNodeFile.getNodeType() == Node.ELEMENT_NODE) {
                        Element eNodeFileElement = (Element) nNodeFile;
                        if (isParsable(eNodeFileElement.getAttribute("path"))) {
                            lInvolvedData.add(eNodeElement.getAttribute("path").toString());
                        }
                    }
                }

                //CheckCodefragment
                NodeList nNodeCodeFragment = eNodeElement.getElementsByTagName("codefragment");
                if(nNodeCodeFragment!=null){
                    sCodeFragment = nNodeCodeFragment.item(0).getFirstChild().toString();
                }

                //Create Duplication
                lDuplications.add(Duplication.getDupliactionInstance(nLinesCount,nTokens,lInvolvedData,sCodeFragment));
            }
        }
    }

    public JSONObject buildJSON(String sMainDir, long lStartTime) {
        JSONObject oJsonRoot = new JSONObject();

		/* add general information to the JSON object */
        oJsonRoot.put("duplicationCursPath", sMainDir);

		/* all Classes */
        for (Duplication oDuplaction : lDuplications) {
            JSONObject oJsonDuplication = new JSONObject();

			/* Duplication Infos*/
            oJsonDuplication.put("duplicatedLines", oDuplaction.getsDupilcatedLine());
            oJsonDuplication.put("tokens", oDuplaction.getsTokens());

            //New Json Array with involved Paths
            JSONArray lJsonFilePaths = new JSONArray();
            for(String sDuplicationFilePath : oDuplaction.getlInvolvedData()){
                lJsonFilePaths.put(new JSONObject().put("filePath", sDuplicationFilePath));
            }
            oJsonDuplication.put("filePaths", lJsonFilePaths);
            oJsonDuplication.put("codefragment", oDuplaction.getsDuplicatedCode());
            oJsonRoot.put("duplications", oJsonDuplication);
        }

        long lEndTime   = System.currentTimeMillis();
        long lTotalTime = (lEndTime - lStartTime);

        oJsonRoot.put("totalExpendedTime", lTotalTime);
        oJsonRoot.put("assignments", "Copy Paste Check for " +sMainDir);

        return oJsonRoot;
    }
}