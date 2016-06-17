package de.htwg.konstanz.cloud.service;

import de.htwg.konstanz.cloud.model.Duplication;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.errors.GitAPIException;
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
import java.util.ArrayList;
import java.util.List;

@Component
public class Cpd {
    private static final Logger LOG = LoggerFactory.getLogger(Cpd.class);

    private final List<Duplication> lDuplications = new ArrayList<>();

    private File oRepoDir;

    private final Util oUtil = new Util();

    private final Git oGit = new Git();

    private final Svn oSvn = new Svn();

    @Value("${app.config.svn.ip}")
    private String SVN_IP_C;

    String startIt(List<String> gitRepository) throws IOException, ParserConfigurationException,
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

    private JSONObject determination(List<String> sRepoUrl, long lStartTime) throws IOException, BadLocationException,
            GitAPIException, ParserConfigurationException, SAXException {
        JSONObject oJson = null;
        String sLocalDir;
        List<String> lRepoDirs = new ArrayList<>();
        StringBuilder oStringBuilder = new StringBuilder();

        LOG.info("Repository URL: " + sRepoUrl);
        oUtil.checkLocalPmd();
        oRepoDir = oUtil.createDirectory("repositories");

        /*Download SVN or Git Repos*/
        for(String sRepo : sRepoUrl) {
            /* Svn */
            if (sRepo.contains(SVN_IP_C)) {
            /* URL needs to start with HTTP:// */
                if (!sRepo.startsWith("http://")) {
                    oStringBuilder.append("http://").append(sRepo);
                }
            /* remove the last / */
                if (sRepo.endsWith("/")) {
                    oStringBuilder.append(sRepo.substring(0, sRepo.length() - 1));
                }

                sLocalDir = oSvn.downloadSvnRepo(oStringBuilder.toString());
                lRepoDirs.add(sLocalDir);
            }

            /* Git */
            else if (sRepo.contains("github.com")) {
                LOG.info("Git");
                sLocalDir = oGit.downloadGITRepo(sRepo);
                lRepoDirs.add(sLocalDir);
            } else {
                LOG.info("Repository URL has no valid Svn/Git attributes. (" + sRepoUrl + ")");
            }
        }

        /*Run CPD*/
        if(!lRepoDirs.isEmpty())
            oJson = (runCpd(lStartTime));

        return oJson;
    }

    private JSONObject runCpd(long lStartTime) throws ParserConfigurationException,
            SAXException, IOException {
        OperatingSystemCheck oOperatingSystemCheck = new OperatingSystemCheck();
        final String sOutputFileName = "Duplications.xml";
        String sStartScript = "";
        String sFilesDirs = "";
        String sMainPath = "repositories/repositories-cpd";
        JSONObject oJson = null;

        if (oOperatingSystemCheck.isWindows()) {
            sStartScript = "pmd-bin-5.4.2\\bin\\cpd.bat";
        } else if (oOperatingSystemCheck.isLinux()) {
            sStartScript = "pmd-bin-5.4.2/bin/run.sh cpd";
        }

        /* Check if Dir exists and if needed create new*/
        oUtil.createDirectory(sMainPath);

        String sCpdCommand = sStartScript + " --minimum-tokens 75 --files " + oRepoDir.getAbsolutePath() + " --skip-lexical-errors "
                + "--format xml > " + sMainPath + "/CpdCheck_" + sOutputFileName;
        LOG.info("Cpd execution path: " + sCpdCommand);

        oUtil.execCommand(sCpdCommand);

        /* Checkstyle Informationen eintragen */
        storeCpdInformation(sMainPath + "/CpdCheck_" + sOutputFileName);

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

        Node oMainNode = oDocument.getFirstChild();
        Element eMainElement = (Element) oMainNode;

        NodeList oNodeList = eMainElement.getElementsByTagName("duplication");
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
                    LOG.info(eNodeElement.getAttribute("lines"));
                }
                if (oUtil.isParsable(eNodeElement.getAttribute("tokens"))) {
                    nTokens = Integer.parseInt(eNodeElement.getAttribute("tokens"));
                    LOG.info(eNodeElement.getAttribute("tokens"));
                }

                //CheckFileNodes
                NodeList nNodeFiles = eNodeElement.getElementsByTagName("file");
                for (int nNodeFilePos = 0; nNodeFilePos < nNodeFiles.getLength(); nNodeFilePos++) {
                    Node nNodeFile = nNodeFiles.item(nNodeFilePos);
                    Element eNodeFileElement = (Element) nNodeFile;
                    if(oUtil.checkIfDifferentReops(lInvolvedData, String.valueOf(eNodeFileElement.getAttribute("path")).substring(String.valueOf(eNodeFileElement.getAttribute("path")).indexOf("repositories") + ("repositories").length() + 1)))
                        lInvolvedData.add(String.valueOf(eNodeFileElement.getAttribute("path")).substring(String.valueOf(eNodeFileElement.getAttribute("path")).indexOf("repositories") + ("repositories").length() + 1));
                }

                //CheckCodefragment
                NodeList nNodeCodeFragment = eNodeElement.getElementsByTagName("codefragment");
                if (nNodeCodeFragment != null) {
                    sCodeFragment = nNodeCodeFragment.item(0).getTextContent();
                }

                //Create Duplication
                if(lInvolvedData.size() > 1)
                    lDuplications.add(Duplication.getDupliactionInstance(nLinesCount, nTokens, lInvolvedData, sCodeFragment));
            }
        }
    }

    private JSONObject buildJson(String sMainDir, long lStartTime) {
        JSONObject oJsonRoot = new JSONObject();

		/* add general information to the JSON object */
        oJsonRoot.put("duplicationCursPath", sMainDir);

        LOG.info(String.valueOf(lDuplications.size()));
		/* all Classes */
        JSONArray lJsonDuplicatiions = new JSONArray();
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
            lJsonDuplicatiions.put(new  JSONObject().put("duplication", oJsonDuplication));
        }
        oJsonRoot.put("duplications", lJsonDuplicatiions);

        long lEndTime = System.currentTimeMillis();
        long lTotalTime = (lEndTime - lStartTime);

        oJsonRoot.put("totalExpendedTime", lTotalTime);
        oJsonRoot.put("assignments", "Copy Paste Check for " + sMainDir);

        LOG.debug("Code Cuplication Analysis check for: " + sMainDir);
        return oJsonRoot;
    }
}