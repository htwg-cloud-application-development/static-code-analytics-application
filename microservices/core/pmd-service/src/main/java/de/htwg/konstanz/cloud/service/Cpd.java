package de.htwg.konstanz.cloud.service;

import de.htwg.konstanz.cloud.model.Duplication;
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

public class Cpd {
    private static final Logger LOG = LoggerFactory.getLogger(Cpd.class);

    private final List<Duplication> lDuplications = new ArrayList<>();

    private File oRepoDir;

    private final Util oUtil = new Util();

    private final Svn oSvn = new Svn();

    private static final String SVN_IP_C = "141.37.122.26";

    String startIt(String gitRepository) throws IOException, ParserConfigurationException,
            SAXException, BadLocationException, GitAPIException, NullPointerException {
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

    private JSONObject determination(String sRepoUrl, long lStartTime) throws IOException, BadLocationException,
                                                        GitAPIException, ParserConfigurationException, SAXException {
        JSONObject oJson = null;
        String sLocalDir;
        StringBuilder oStringBuilder = new StringBuilder();

        LOG.info("Repository URL: " + sRepoUrl);
        checkLocalCpd();

        /* Svn */
        if (sRepoUrl.contains(SVN_IP_C)) {
            /* URL needs to start with HTTP:// */
            if (!sRepoUrl.startsWith("http://")) {
                oStringBuilder.append("http://").append(sRepoUrl);
            }
            /* remove the last / */
            if (sRepoUrl.endsWith("/")) {
                oStringBuilder.append(sRepoUrl.substring(0, sRepoUrl.length() - 1));
            }

            LOG.info("Cpd");
            sLocalDir = oSvn.downloadSvnRepo(oStringBuilder.toString());
            oJson = (runCpd(sLocalDir, lStartTime));
            oRepoDir = new File(sLocalDir);
        }

        return oJson;
    }

    /* NEVER USED?
    private String generateCPDData(String localDirectory) {
        File mainDir;

        mainDir = oUtil.checkLocalSrcDir(localDirectory);

        if (mainDir.exists()) {
            return mainDir.getPath();
        }

        return null;
    }
    */

    private void checkLocalCpd() throws IOException {
        Zip oZip = new Zip();
        final String sCpdDir = "pmd-bin-5.4.2.zip";
        final String sDownloadCpd = "https://github.com/pmd/pmd/releases/download/pmd_releases%2F5.4.2/pmd-bin-5.4.2.zip";

        File oFile = new File(sCpdDir);
        ReadableByteChannel oReadableByteChannel;
        FileOutputStream oFileOutput;
        URL oUrl;

        if (oFile.exists()) {
            LOG.info("CDP Directory already exists!");
        } else {
            LOG.info("CDP Directory does not exists, Starting download");
            oUrl = new URL(sDownloadCpd);
            oReadableByteChannel = Channels.newChannel(oUrl.openStream());
            oFileOutput = new FileOutputStream(sCpdDir);
            oFileOutput.getChannel().transferFrom(oReadableByteChannel, 0, Long.MAX_VALUE);

            oZip.unzipFile(sCpdDir);
        }
    }

    private JSONObject runCpd(String sMainPath, long lStartTime) throws ParserConfigurationException,
                                                                        SAXException, IOException {
        OperatingSystemCheck oOperatingSystemCheck = new OperatingSystemCheck();
        final String sOutputFileName = "Duplications.xml";
        String sStartScript = "";
        JSONObject oJson = null;

        if (oOperatingSystemCheck.isWindows()) {
            sStartScript = "pmd-bin-5.4.2\\bin\\cpd.bat";
        } else if (oOperatingSystemCheck.isLinux()) {
            sStartScript = "pmd-bin-5.4.2/bin/run.sh cpd";
        }

        String sCpdCommand = sStartScript + " --minimum-tokens 75 --files " + sMainPath + " --skip-lexical-errors "
                                    + "--format xml > " + sMainPath + sOutputFileName;
        LOG.info("Cpd execution path: " + sCpdCommand);

        oUtil.execCommand(sCpdCommand);

        /* Checkstyle Informationen eintragen */
        storeCpdInformation(sMainPath + sOutputFileName);

        if (lDuplications != null) {
            /* Schoene einheitliche JSON erstellen */
            oJson = buildJson(sMainPath, lStartTime);
            /* JSON an Database weitersenden */
        }

        return oJson;
    }

    private void storeCpdInformation(String sXmlPath) throws ParserConfigurationException, SAXException, IOException {
        InputStream oInputStream = new FileInputStream(sXmlPath);
        Reader oReader = new InputStreamReader(oInputStream, "UTF-8");
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
                List<String> lInvolvedData = new ArrayList<>();
                String sCodeFragment = "";


                //Duplication Infos
                if (oUtil.isParsable(eNodeElement.getAttribute("lines"))) {
                    nLinesCount = Integer.parseInt(eNodeElement.getAttribute("lines"));
                }
                if (oUtil.isParsable(eNodeElement.getAttribute("tokens"))) {
                    nTokens = Integer.parseInt(eNodeElement.getAttribute("tokens"));
                }

                //CheckFileNodes
                NodeList nNodeFiles = eNodeElement.getElementsByTagName("file");
                for (int nNodeFilePos = 0; nNodeFilePos < nNodeFiles.getLength(); nNodeFilePos++) {
                    Node nNodeFile = nNodeFiles.item(nNodeFilePos);
                    if (nNodeFile.getNodeType() == Node.ELEMENT_NODE) {
                        Element eNodeFileElement = (Element) nNodeFile;
                        if (oUtil.isParsable(eNodeFileElement.getAttribute("path"))) {
                            lInvolvedData.add(eNodeElement.getAttribute("path").toString());
                        }
                    }
                }

                //CheckCodefragment
                NodeList nNodeCodeFragment = eNodeElement.getElementsByTagName("codefragment");
                if (nNodeCodeFragment != null) {
                    sCodeFragment = nNodeCodeFragment.item(0).getFirstChild().toString();
                }

                //Create Duplication
                lDuplications.add(Duplication.getDupliactionInstance(nLinesCount, nTokens, lInvolvedData, sCodeFragment));
            }
        }
    }

    private JSONObject buildJson(String sMainDir, long lStartTime) {
        JSONObject oJsonRoot = new JSONObject();

		/* add general information to the JSON object */
        oJsonRoot.put("duplicationCursPath", sMainDir);

		/* all Classes */
        for (Duplication oDuplaction : lDuplications) {
            JSONObject oJsonDuplication = new JSONObject();

			/* Duplication Infos*/
            oJsonDuplication.put("duplicatedLines", oDuplaction.getDuplicatedLine());
            oJsonDuplication.put("tokens", oDuplaction.getTokens());

            //New Json Array with involved Paths
            JSONArray lJsonFilePaths = new JSONArray();
            for (String sDuplicationFilePath : oDuplaction.getInvolvedData()) {
                lJsonFilePaths.put(new JSONObject().put("filePath", sDuplicationFilePath));
            }
            oJsonDuplication.put("filePaths", lJsonFilePaths);
            oJsonDuplication.put("codefragment", oDuplaction.getDuplicatedCode());
            oJsonRoot.put("duplications", oJsonDuplication);
        }

        long lEndTime = System.currentTimeMillis();
        long lTotalTime = (lEndTime - lStartTime);

        oJsonRoot.put("totalExpendedTime", lTotalTime);
        oJsonRoot.put("assignments", "Copy Paste Check for " + sMainDir);

        LOG.debug("Code Cuplication Analysis check for: " + sMainDir);
        return oJsonRoot;
    }
}