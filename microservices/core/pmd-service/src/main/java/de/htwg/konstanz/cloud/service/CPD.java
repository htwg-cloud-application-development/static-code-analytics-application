package de.htwg.konstanz.cloud.service;

import de.htwg.konstanz.cloud.model.Duplication;
import de.htwg.konstanz.cloud.model.SeverityCounter;
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
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.*;

public class CPD {

    private static final Logger LOG = LoggerFactory.getLogger(CPD.class);
    private List<Duplication> lDuplications = new ArrayList<>();
    private File oRepoDir;
    private SVN oSvn = new SVN();

    public String startIt(String gitRepository) throws IOException, ParserConfigurationException, SAXException, BadLocationException, InvalidRemoteException, TransportException, GitAPIException, MalformedURLException, NullPointerException {
        long lStartTime = System.currentTimeMillis();
        JSONObject oJsonResult = null;
        String sResult = "";
        SeverityCounter oSeverityCounter = new SeverityCounter();

        oJsonResult = determination(gitRepository, lStartTime);
        if (oRepoDir != null)
        {
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

    private JSONObject determination(String sRepoUrl, long lStartTime) throws IOException, BadLocationException, InvalidRemoteException, TransportException, GitAPIException, ParserConfigurationException, SAXException {
        JSONObject oJson = null;
        String sLocalDir;

        LOG.info("Repository URL: " + sRepoUrl);
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

            LOG.info("CPD");
            sLocalDir = oSvn.downloadSVNRepo(sRepoUrl);
            oJson = (runCPD(sLocalDir, lStartTime));
            oRepoDir = new File(sLocalDir);
        }

        return oJson;
    }

    private String generateCPDData(String localDirectory) {
        File mainDir;

        /* Check if local /src-dir exists */
        if (new File(localDirectory + "/src").exists()) {
            mainDir = new File(localDirectory + "/src");
            LOG.info("Local SRC directory found");
        } else {
            mainDir = new File(localDirectory);
            LOG.info("There was no local SRC directory");
        }

        /* List all files for CPDService */
        if (mainDir.exists()) {
            return mainDir.getPath();
        }
        return null;
    }

    private void checkLocalCPD() throws MalformedURLException, IOException, FileNotFoundException {
        ZIP oZIP = new ZIP();
        boolean bSuccess = false;
        final String sCPDDir = "pmd-bin-5.4.2.zip";
        final String sDownloadCPD = "https://github.com/pmd/pmd/releases/download/pmd_releases%2F5.4.2/pmd-bin-5.4.2.zip";

        File oFile = new File(sCPDDir);
        ReadableByteChannel oReadableByteChannel = null;
        FileOutputStream oFileOutput = null;
        URL oURL = null;

        if (oFile.exists())
        {
            LOG.info("CDP Directory already exists!");
        } else
        {
            LOG.info("CDP Directory does not exists, Starting download");
            oURL = new URL(sDownloadCPD);
            oReadableByteChannel = Channels.newChannel(oURL.openStream());
            oFileOutput = new FileOutputStream(sCPDDir);
            oFileOutput.getChannel().transferFrom(oReadableByteChannel, 0,Long.MAX_VALUE);

            oZIP.unzipFile(sCPDDir);
        }
    }

    private JSONObject runCPD(String sMainPath, long lStartTime) throws ParserConfigurationException, SAXException, IOException {
        OperatingSystemCheck oOperatingSystemCheck = new OperatingSystemCheck();
        final String sRuleSetPath = "java-basic,java-design,java-codesize";
        String sStartScript = "";
        JSONObject oJson = null;

        if (oOperatingSystemCheck.isWindows() == true) {
            sStartScript = "pmd-bin-5.4.2\\bin\\cpd.bat";
        } else if (oOperatingSystemCheck.isLinux() == true) {
            sStartScript = "pmd-bin-5.4.2/bin/run.sh";
        }

        String sCPDCommand = sStartScript + " --minimum-tokens 75 --files " + sMainPath + " --skip-lexical-errors --format xml > "  + sMainPath + "Duplications.xml";
        LOG.info("CPD execution path: " + sCPDCommand);

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
        storeCPDInformation(sMainPath + "Duplications.xml");

        if (lDuplications != null) {
			/* Schoene einheitliche JSON erstellen */
            oJson = buildJSON(sMainPath, lStartTime);
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

    private void storeCPDInformation(String sXmlPath) throws ParserConfigurationException, SAXException, IOException {
        InputStream oInputStream= new FileInputStream(sXmlPath);
        Reader oReader = new InputStreamReader(oInputStream,"UTF-8");
        InputSource oInputSource = new InputSource(oReader);
        oInputSource.setEncoding("UTF-8");

        DocumentBuilderFactory oDbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder oDBuilder = oDbFactory.newDocumentBuilder();
        Document oDocument = oDBuilder.parse(oInputSource);

        NodeList oNodeList = oDocument.getElementsByTagName("dupliaction");

        for (int nNodePos = 0; nNodePos < oNodeList.getLength(); nNodePos++) {

            Node oNode = oNodeList.item(nNodePos);

            if (oNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eNodeElement = (Element) oNode;

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

    private JSONObject buildJSON(String sMainDir, long lStartTime) {
        JSONObject oJsonRoot = new JSONObject();

		/* add general information to the JSON object */
        oJsonRoot.put("duplicationCursPath", sMainDir);

		/* all Classes */
        for (Duplication oDuplaction : lDuplications) {
            JSONObject oJsonDuplication = new JSONObject();

			/* Duplication Infos*/
            oJsonDuplication.put("duplicatedLines", oDuplaction.getsDuplicatedLine());
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