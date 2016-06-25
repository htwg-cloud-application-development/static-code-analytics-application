package de.htwg.konstanz.cloud.util;

import de.htwg.konstanz.cloud.model.*;
import de.htwg.konstanz.cloud.model.Class;
import de.htwg.konstanz.cloud.model.Error;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.List;

/*
    To provide easy access to generated data objects, this class builds a JSON file,
    which will be easy to transmit attribute-value pairs to other services, like
    the database service.
 */
public class OwnJson {
    private static final Logger LOG = LoggerFactory.getLogger(OwnJson.class);

    private final Util util = new Util();

    /**
     * parses all founded xml attributes and stores them into a list
     * @param nClassPos - actual class position of a list with all classes
     * @param nList - list of all xml nodes
     * @param nNodePos - actual Node position of the xml document
     * @param lClassList - list with all evaluated java classes
     */
    public void readAndSaveAttributes(int nClassPos, NodeList nList, int nNodePos, List<Class> lClassList) {
        //gets the actual position of the Node List
        Node nNode = nList.item(nNodePos);

        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
            Element eElement = (Element) nNode;

            String sMessage = eElement.getFirstChild().getTextContent();

            //check if the founded String line begin and line end is parsable to an integer
            // if not the default value of integer is zero (0)
            Line oLine = new Line(util.getParsableElement(eElement, "beginline"),
                                        util.getParsableElement(eElement, "endline"));
            Column oColumn = new Column(util.getParsableElement(eElement, "begincolumn"),
                                            util.getParsableElement(eElement, "endcolumn"));

            //checks if the string priority is empty or not. Default Value for empty Strings: ""
            //pmd provides different priority levels for all founded problems
            int nPriority = util.getParsableElement(eElement, "priority");
            //increment the problem belonging to the founded priority
            lClassList.get(nClassPos).incErrorType(nPriority);

            String sClassName = util.getNonEmptyElement(eElement, "class");
            String sRule = util.getNonEmptyElement(eElement, "rule");
            String sRuleset = util.getNonEmptyElement(eElement, "ruleset");
            String sPackage = util.getNonEmptyElement(eElement, "package");

            //all founded problems are stored in an data object Error
            Error oError = new Error(oLine, oColumn, nPriority, sRule, sClassName, sPackage, sRuleset, sMessage);
            //this error object are added to the belonging java class
            lClassList.get(nClassPos).getErrorList().add(oError);
        }
    }

    /**
     *  buildJson provides the main functionality to generate a customized JSON File
     *  following the structure of our JSON is shown:
     *
     *    "numberOfIgnores": 3,                              <-- some general json information
     *    "totalExpendedTime": 3706,
     *    "assignments": [                                   <-- Array for all exercises
     *       {
     *       "aufgabe1": [                                     <-- Example of an Exercise
     *           {
     *            "numberOfIgnores": 1,                         <-- some specific exercise information
     *            "filepath": "aufgabe1\\\\Array26.java",
     *            "numberOfWarnings": 0,
     *            "errors": [                                   <-- Array of all related Errors
     *               {
     *                "columnEnd": "1",                         <-- some specific error information related to the exercise
     *                "package": "aufgabe1",
     *                "lineBegin": "10",
     *                "ruleset": "Design",
     *                "rule": "ClassWithOnlyPrivateConstructorsShouldBeFinal",
     *                "columnBegin": "8",
     *                "priority": "1",
     *                "message": "\nA class which only has private constructors should be final\n",
     *                "lineEnd": "52",
     *                "class": ""
     *        ...
     *       "aufgabe2": [
     *      ...
     *   "numberOfWarnings": 0,                               <-- general information over the whole project
     *   "lastRepoUpdateTime": "",
     *   "repository": "http://141.37.122.26/svn/langweg/WIN-EPR/2016SS/MaxMustermann/src/",
     *   "numberOfErrors": 3
     *
     * @param sRepo - svn/git repository path
     * @param lStartTime - start time of the execution
     * @param sLastRepoUpdateTime - last time the svn/git repository was updated
     * @param lClassList - list with all evaluated java classes
     * @return - a json object consisting of all analyzed pmd problems
     */
    public JSONObject buildJson(String sRepo, long lStartTime, String sLastRepoUpdateTime, List<Class> lClassList) {
        OwnJsonProperties oOwnJsonProperties = new OwnJsonProperties();
        boolean bExerciseChange = false;
        boolean bLastRun = false;
        boolean bExerciseNeverChanged = true;

        /* add general information to the JSON object */
        oOwnJsonProperties.getOJsonRoot().put("repository", sRepo);

        getClassSeverities(lClassList, oOwnJsonProperties.getOJsonRoot());

        oOwnJsonProperties.getOJsonRoot().put("lastRepoUpdateTime", sLastRepoUpdateTime);
        LOG.info("Last Update Time: " + sLastRepoUpdateTime);

		/* all Classes */
        for (int nClassPos = 0; nClassPos < lClassList.size(); nClassPos++) {
            if (bExerciseChange) {
                nClassPos--;
                bExerciseChange = false;
            }

            String sExcerciseName = lClassList.get(nClassPos).getExerciseName();

			/* first run the TmpName is empty */
            if (sExcerciseName.equals(oOwnJsonProperties.getSTmpExerciseName())
                    || "".equals(oOwnJsonProperties.getSTmpExerciseName())) {
                storeJsonInformation(lClassList, oOwnJsonProperties, bLastRun, bExerciseNeverChanged,
                                                                            nClassPos, sExcerciseName);
            }
            /* swap for a different exercise */
            else {
                if (lClassList.get(nClassPos).getErrorList().size() > 0) {
                    oOwnJsonProperties.getOJsonExercise().put(oOwnJsonProperties.getSTmpExerciseName(),
                                                                    oOwnJsonProperties.getLJsonClasses());
                    oOwnJsonProperties.getLJsonExercises().put(oOwnJsonProperties.getOJsonExercise());
                    oOwnJsonProperties.setOJsonExercise(new JSONObject());
                    oOwnJsonProperties.setLJsonClasses(new JSONArray());
                    oOwnJsonProperties.setSTmpExerciseName(lClassList.get(nClassPos).getExerciseName());
                    bExerciseChange = true;
                    bExerciseNeverChanged = false;

				    /* decrement the position to get the last class from the list */
                    if (nClassPos + 1 == lClassList.size()) {
                        nClassPos--;
                        bExerciseChange = false;
                        bLastRun = true;
                    }
                }
            }
        }

        long lEndTime = System.currentTimeMillis();
        long lTotalTime = (lEndTime - lStartTime);

        oOwnJsonProperties.getOJsonRoot().put("totalExpendedTime", lTotalTime);
        oOwnJsonProperties.getOJsonRoot().put("assignments", oOwnJsonProperties.getLJsonExercises());

        LOG.debug("Pmd Static Analysis check for Repo: " + sRepo);
        return oOwnJsonProperties.getOJsonRoot();
    }

    /**
     * adds information of the pmd validation to the json file
     * @param lClassList - list with all evaluated java classes
     * @param ownJsonProperties - object with important json attributes
     * @param bLastRun - is the actual iterations the last one?
     * @param bExerciseNeverChanged - Flag which indicates, if there was only one exercise
     * @param nClassPos - actual position in the class list
     * @param sExerciseName - name of the actual considered class object
     */
    private void storeJsonInformation(List<Class> lClassList, OwnJsonProperties ownJsonProperties, boolean bLastRun,
                                                boolean bExerciseNeverChanged, int nClassPos, String sExerciseName) {
        /* if errors where founded, a new exercise name is returned by analyzeErrors */
        ownJsonProperties.setSTmpExerciseName(analyzeErrors(lClassList, lClassList.get(nClassPos).getErrorList(),
                                    ownJsonProperties.getLJsonClasses(), ownJsonProperties.getSTmpExerciseName(),
                                                                                        nClassPos, sExerciseName));

        /* Condition for the last run if different exercises were founded */
        if (bLastRun) {
            ownJsonProperties.getOJsonExercise().put(ownJsonProperties.getSTmpExerciseName(),
                                                        ownJsonProperties.getLJsonClasses());
            ownJsonProperties.getLJsonExercises().put(ownJsonProperties.getOJsonExercise());
        }
        /* Condition for the last run and if there was just one exercise with various exercises */
        if ((nClassPos + 1) == lClassList.size() && bExerciseNeverChanged) {
            ownJsonProperties.getOJsonExercise().put(ownJsonProperties.getSTmpExerciseName(),
                                                        ownJsonProperties.getLJsonClasses());
            ownJsonProperties.getLJsonExercises().put(ownJsonProperties.getOJsonExercise());
        }
    }

    /**
     * counts all founded priorities
     * @param lClassList - list with all evaluated java classes
     * @param oJsonRoot - main node of the json file
     */
    private void getClassSeverities(List<Class> lClassList, JSONObject oJsonRoot) {
        int nTmpErrorCount = 0;
        int nTmpWarningCount = 0;
        int nTmpIgnoreCounter = 0;

        /* get severities of the whole project */
        for (Class oClass : lClassList) {
            nTmpErrorCount += oClass.getErrorCount() + oClass.getCriticalCount();
            nTmpWarningCount += oClass.getWarningCount();
            nTmpIgnoreCounter += oClass.getIgnoreCount();
        }
        //adds all priorities to the json Root file (first level of the json object)
        oJsonRoot.put("numberOfErrors", nTmpErrorCount);
        oJsonRoot.put("numberOfWarnings", nTmpWarningCount);
        oJsonRoot.put("numberOfIgnores", nTmpIgnoreCounter);
    }

    /**
     * all founded errors of a java file where analyzed be this method. It adds all attribute-value pairs to an
     * json object (constitutes an error) which will be added to an array of Json Objects which represents a java class.
     * @param lClassList - list with all evaluated java classes
     * @param lTmpErrorList - a list which contains errors of a class object
     * @param lJsonClasses - json object array which stores all class objects that were evaluated and contains errors
     * @param sTmpExerciseName - name of the class object before
     * @param nClassPos - actual position in the class list
     * @param sExerciseName - name of the actual considered class object
     * @return - exercise name of the checked file
     */
    private String analyzeErrors(List<Class> lClassList, List<Error> lTmpErrorList,
                                 JSONArray lJsonClasses, String sTmpExerciseName, int nClassPos, String sExerciseName) {
        String sLocalExerciseName = sTmpExerciseName;

        if (!lTmpErrorList.isEmpty()) {
            JSONArray lJsonErrors = new JSONArray();
            JSONObject oJsonClass = new JSONObject();

            /* all Errors */
            for (Error oError : lTmpErrorList) {
                //get all attribute-value pairs of the founded error object
                JSONObject oJsonError = new JSONObject();

                oJsonError.put("lineBegin", Integer.toString(oError.getLineBegin()));
                oJsonError.put("lineEnd", Integer.toString(oError.getLineEnd()));
                oJsonError.put("columnBegin", Integer.toString(oError.getColumnBegin()));
                oJsonError.put("columnEnd", Integer.toString(oError.getColumnEnd()));
                oJsonError.put("priority", Integer.toString(oError.getPriority()));
                oJsonError.put("rule", oError.getRule());
                oJsonError.put("class", oError.getClassName());
                oJsonError.put("package", oError.getPackage());
                oJsonError.put("ruleset", oError.getRuleset());
                oJsonError.put("message", oError.getMessage());

                //add it to the error list of a represented java file
                lJsonErrors.put(oJsonError);
            }

            //only if errors are founded the results should be added to the whole json file
            if (lJsonErrors.length() > 0) {
                sLocalExerciseName = sExerciseName;

                String sFilePath = util.removeUnnecessaryPathParts(lClassList.get(nClassPos).getFullPath());
                oJsonClass.put("filepath", sFilePath);
                oJsonClass.put("errors", lJsonErrors);
                //sets the priorities for the represented "java file"
                setPriorityCounters(oJsonClass, nClassPos, lClassList);
                //holds all json objects that represent java classes
                lJsonClasses.put(oJsonClass);
            }
        }

        return sLocalExerciseName;
    }

    /**
     * add all errors to a Java Class file, so that each file shows up different error counters
     * @param oJsonClass - json object which represents a java class
     * @param nClassPos -  actual pos of the class
     * @param lClassList - list with all evaluated java classes
     */
    private void setPriorityCounters(JSONObject oJsonClass, int nClassPos, List<Class> lClassList) {
        oJsonClass.put("numberOfErrors", lClassList.get(nClassPos).getCriticalCount()
                                        + lClassList.get(nClassPos).getErrorCount());
        oJsonClass.put("numberOfWarnings", lClassList.get(nClassPos).getWarningCount());
        oJsonClass.put("numberOfIgnores", lClassList.get(nClassPos).getIgnoreCount());
    }
}
