package de.htwg.konstanz.cloud.service;

import de.htwg.konstanz.cloud.model.Class;
import de.htwg.konstanz.cloud.util.*;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
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

public class Pmd {
    private static final Logger LOG = LoggerFactory.getLogger(Pmd.class);

    private final Util util = new Util();

    private List<Class> lFormattedClassList;

    private final OperatingSystemCheck oOperatingSystemCheck = new OperatingSystemCheck();

    private File oRepoDir;

    private final Git oGit = new Git();

    private final Svn oSvn = new Svn();

    private final OwnJson oOwnJson = new OwnJson();

    private final String svnServerIp;

    private final String ruleSetPath;

    /**
     * Constructor to initialize the Svn server ip and ruleset path of checkstyle.
     * They are stored in the config file
     * @param svnServerIp - SVN Server ip address
     * @param ruleSetPath - local path to the ruleset of pmd
     */
    public Pmd(String svnServerIp, String ruleSetPath) {
        this.svnServerIp = svnServerIp;
        this.ruleSetPath = ruleSetPath;
    }

    /**
     * entry point for an incoming post request
     * @param gitRepository - given git repository from post request
     * @return - problems that pmd has found in a json file
     * @throws IOException - throw for the handling in CheckstyleService
     * @throws ParserConfigurationException - throw for the handling in CheckstyleService
     * @throws SAXException - throw for the handling in CheckstyleService
     * @throws BadLocationException - throw for the handling in CheckstyleService
     * @throws GitAPIException - throw for the handling in CheckstyleService
     * @throws NullPointerException - throw for the handling in CheckstyleService
     */
    String startIt(String gitRepository) throws IOException, ParserConfigurationException, SAXException,
            BadLocationException, GitAPIException, NullPointerException {
        lFormattedClassList = new ArrayList<>();
        long lStartTime = System.currentTimeMillis();
        JSONObject oJsonResult;
        String sResult;

        //checks if the post contains a repository of git or svn
        oJsonResult = determineVersionControlSystem(gitRepository, lStartTime);

        if (oRepoDir == null) {
            LOG.info("Error: Local Directory is null!");
        } else {
            FileUtils.deleteDirectory(oRepoDir);
        }

        //tests the result to avoid a nullpointer exception
        sResult = util.checkJsonResult(oJsonResult);

        return sResult;
    }

    /**
     * checks if the given repository belongs to git or svn. After the checkout of the repository
     * this method executes the analysis of pmd
     * @param sRepoUrl - within POST request given repository url (svn or git)
     * @param lStartTime - start time of the service execution
     * @return  - generated JSON file with all problems pmd has found
     * @throws IOException - throw for the handling in CheckstyleService
     * @throws BadLocationException - throw for the handling in CheckstyleService
     * @throws GitAPIException - throw for the handling in CheckstyleService
     * @throws ParserConfigurationException - throw for the handling in CheckstyleService
     * @throws SAXException - throw for the handling in CheckstyleService
     */
    private JSONObject determineVersionControlSystem(String sRepoUrl, long lStartTime)
            throws IOException, BadLocationException, GitAPIException, ParserConfigurationException, SAXException {
        JSONObject oJson = null;
        String sLocalDir;
        String[] sLocalDirArray;
        StringBuilder oStringBuilder = new StringBuilder();

        LOG.info("Repository URL: " + sRepoUrl);
        //to run pmd, the binary files are needed. If they are not present locally, this method will download
        // and unzip it automatically
        util.checkLocalPmd();
        oRepoDir = util.createDirectory("repositories");

        /* Svn Checkout */
        if (sRepoUrl.contains(svnServerIp)) {
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
            //download the given svn repository and returns the locally path of it
            sLocalDir = oSvn.downloadSvnRepo(oStringBuilder.toString());
            /* Last Update Time (last parameter) of SVN is empty because it does not provide this information */
            // generate some parsable pmd data and run pmd for the locally stored svn repository
            oJson = runPmd(generatePmdData(sLocalDir), sRepoUrl, lStartTime, "");
            oRepoDir = new File(sLocalDir);
        }
        /* Git Checkout */
        else if (sRepoUrl.contains("github.com")) {
            LOG.info("Git");
            //download the given git repository and returns the locally path of it and the last updated time
            // [0] --> locally Path
            // [1] --> Last Update Time
            sLocalDirArray = oGit.downloadGitRepo(sRepoUrl);
            /* Last Update Time (last parameter) of the git repositry */
            // generate some parsable checkstyle data and run pmd for the locally stored git repository
            oJson = runPmd(generatePmdData(sLocalDirArray[0]), sRepoUrl, lStartTime, sLocalDirArray[1]);
            oRepoDir = new File(sLocalDirArray[0]);
        } else {
            LOG.info("Repository URL has no valid Svn/Git attributes. (" + sRepoUrl + ")");
        }

        return oJson;
    }

    /**
     * generate data for the pmd service
     * @param sLocalDirectory - Locale directory of the outchecked SVN or GIT repository
     * @return - A list with all directories and files
     */
    private List<List<String>> generatePmdData(String sLocalDirectory) throws FileNotFoundException {
        //crawl Method to detect all .java Files in the given local path
        List<List<String>> list = new ArrayList<>();
        File mainDir;
        LOG.info("Local Directory: " + sLocalDirectory);
        //Check if Src-Dir exists
        mainDir = util.checkLocalSrcDir(sLocalDirectory);

        /* List all files for CheckstyleService */
        if (mainDir.exists()) {
            File[] files = mainDir.listFiles();
            if (files != null) {
                for (File file : mainDir.listFiles()) {
                    if (file != null) {
                        //Head-Dirs
                        File[] filesSub = new File(file.getPath()).listFiles();
                        List<String> pathsSub = new ArrayList<>();

                        if (filesSub != null) {
                            for (File aFilesSub : filesSub) {
                                //Java-Files
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
        }

        checkUnregularRepository(sLocalDirectory, list);

        return list;
    }

    /**
     * If the directory structure of the given url is not like defined in the manual, this method
     * just gets all other java files without a correct assignment allocation
     * @param sLocalDirectory - local directory that should be checked
     * @param list - list that contains all founded java files
     */
    private void checkUnregularRepository(String sLocalDirectory, List<List<String>> list) {
        /* Other Structure Workaround */
        if (list.isEmpty()) {
            //Unregular Repo
            LOG.info("unregular repository");
            List<String> javaFiles = new ArrayList<>();
            list.add(util.getAllJavaFiles(sLocalDirectory, javaFiles));
        }
    }

    /**
     * executes pmd within the command line interface
     * @param lRepoList - List of all repositories that were stored locally
     * @param gitRepository - String that represents the analyzed repository (SVN or GIT)
     * @param lStartTime - Start time of the execution
     * @param sLastUpdateTime - Last update of the repository
     * @return - returns a JSON Object with all provided information of checkstyle
     * @throws ParserConfigurationException - Error while parsing xml document
     * @throws SAXException - Error within SAX XML Parser
     * @throws IOException - general io exception
     */
    private JSONObject runPmd(List<List<String>> lRepoList, String gitRepository, long lStartTime, String sLastUpdateTime)
            throws ParserConfigurationException, SAXException, IOException {

        String sStartScript = "";

        //check the actual operating system and build the path with the correct file separator
        if (oOperatingSystemCheck.isWindows()) {
            sStartScript = "pmd-bin-5.4.2\\bin\\pmd.bat";
        } else if (oOperatingSystemCheck.isLinux()) {
            sStartScript = "pmd-bin-5.4.2/bin/run.sh pmd";
        }

		/* reduce the content of the repository list for the json creation */
        formatList(lRepoList);

        for (int nClassPos = 0; nClassPos < lFormattedClassList.size(); nClassPos++) {
            String sFullPath = lFormattedClassList.get(nClassPos).getFullPath();

            if (sFullPath.endsWith(".java")) {
                sFullPath = sFullPath.substring(0, sFullPath.length() - 5);
            }

            //builds the execution command of pmd for the command line interace
            String sPmdCommand = sStartScript + " -d " + sFullPath + ".java -f xml -failOnViolation false "
                    + "-encoding UTF-8 -rulesets " + ruleSetPath + " -r " + sFullPath + ".xml";
            LOG.info("Pmd execution path: " + sPmdCommand);

            //execute it and validate the return code to get files which were not parsable by pmd
            int nReturnCode = util.execCommand(sPmdCommand);
            LOG.info("Process Return Code: " + nReturnCode);

			/* store pmd Informationen in the global List */
            storePmdInformation(sFullPath + ".xml", nClassPos);
        }

        /* generate a JSON File */
        return oOwnJson.buildJson(gitRepository, lStartTime, sLastUpdateTime, lFormattedClassList);
    }

    /**
     * removes unnecessary information
     * @param lRepoList - List with all given repositories that should be redurced
     */
    private void formatList(List<List<String>> lRepoList) {
        OperatingSystemCheck oOperatingSystemCheck = new OperatingSystemCheck();

        for (List<String> lRepoListInList : lRepoList) {
            Class oClass;
            for (String sRepo : lRepoListInList) {
                //splits the repository by the given file separator
                String[] sFullPathSplitArray = sRepo.split(oOperatingSystemCheck.getOperatingSystemSeparator());

                String sTmpExerciseName = sFullPathSplitArray[2];
                oClass = new Class(sRepo, sTmpExerciseName);

                lFormattedClassList.add(oClass);
            }
        }
    }

    /**
     * opens the stored xml file of pmd and iterates through all errors to read the attributes-value pairs
     * @param sXmlPath - xml file path the should be read
     * @param nClassPos - actual class position of the whole list --> to assign the founded errors correctly
     * @throws ParserConfigurationException - Error while parsing xml document
     * @throws SAXException - Error within SAX XML Parser
     * @throws IOException - general io exception
     */
    private void storePmdInformation(String sXmlPath, int nClassPos) throws
            ParserConfigurationException, SAXException, IOException {
        InputStream inputStream = new FileInputStream(sXmlPath);
        Reader reader = new InputStreamReader(inputStream, "UTF-8");
        InputSource is = new InputSource(reader);
        is.setEncoding("UTF-8");

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(is);

        NodeList nList = doc.getElementsByTagName("violation");

        for (int nNodePos = 0; nNodePos < nList.getLength(); nNodePos++) {
            oOwnJson.readAndSaveAttributes(nClassPos, nList, nNodePos, lFormattedClassList);
        }
    }
}
