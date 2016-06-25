package de.htwg.konstanz.cloud.util;

import de.htwg.konstanz.cloud.model.Class;
import de.htwg.konstanz.cloud.model.Error;
import de.htwg.konstanz.cloud.model.OwnJsonProperties;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.List;

/*  To provide easy access to generated data objects, this class builds a JSON file,
    which will be easy to transmit attribute-value pairs to other services, like
    the database service.
 */
public class OwnJson {
    private static final Logger LOG = LoggerFactory.getLogger(OwnJson.class);

    private final Util oUtil = new Util();

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

            //check if the founded String Line is parsable to an integer
            // if not the default value of integer is zero (0)
            int nLine = oUtil.getParsableElement(eElement, "line");
            int nColumn = oUtil.getParsableElement(eElement, "column");

            //checks if the String Severity is empty or not. Default Value for empty Strings: ""
            //checkstyle provides different severity levels for all founded problems
            String sSeverity = oUtil.getNonEmptyElement(eElement, "severity");
            //increment the problem belonging to the founded severity
            lClassList.get(nClassPos).incErrorType(sSeverity);

            String sMessage = oUtil.getNonEmptyElement(eElement, "message");
            String sSource = oUtil.getNonEmptyElement(eElement, "source");

            //all founded problems are stored in an data objekt Error
            Error oError = new Error(nLine, nColumn, sSeverity, sMessage, sSource);
            //this error object are added to the belonging java class
            lClassList.get(nClassPos).getErrorList().add(oError);
        }
    }

    /**
     * counts all founded severities
     * @param lClassList- list with all evaluated java classes
     * @param oJsonRoot - main node of the json file
     */
    private void getClassSeverities(List<Class> lClassList, JSONObject oJsonRoot) {
        int nTmpErrorCount = 0;
        int nTmpWarningCount = 0;
        int nTmpIgnoreCounter = 0;

        /* get severities of the whole project files */
        for (Class oFormattedClassList : lClassList) {
            nTmpErrorCount += oFormattedClassList.getErrorCount();
            nTmpWarningCount += oFormattedClassList.getWarningCount();
            nTmpIgnoreCounter += oFormattedClassList.getIgnoreCount();
        }

        //adds all severitys to the json Root file (first level of the json object)
        oJsonRoot.put("numberOfErrors", nTmpErrorCount);
        LOG.info("Number of Errors: " + nTmpErrorCount);
        oJsonRoot.put("numberOfWarnings", nTmpWarningCount);
        LOG.info("Number of Warnings: " + nTmpWarningCount);
        oJsonRoot.put("numberOfIgnores", nTmpIgnoreCounter);
        LOG.info("Number of Ignores: " + nTmpIgnoreCounter);
    }


    /**
     *  buildJson provides  the main functionality to generate a customized JSON File.
     *    following the structure of our JSON is shown:
     *
     *     "numberOfIgnores": 0,                              <-- some general json information
     *    "totalExpendedTime": 75730,
     *    "assignments": [                                   <-- Array for all exercises
     *       {
     *       "Aufgabe10": [                                     <-- Example of an Exercise
     *           {
     *            "numberOfIgnores": 0,                         <-- some general exercise information
     *            "filepath": "Aufgabe10\\Aufgabe10.java",
     *            "numberOfErros": 0,
     *            "numberOfWarnings": 9,
     *            "errors": [                                   <-- Array of all related Errors
     *               {
     *                "severity": "warning",                    <-- some general error information
     *                "line": "4",
     *                "column": "9",
     *                "source": "com.puppycrawl.tools.checkstyle.checks.naming.PackageNameCheck",
     *                "message": "Package name 'Aufgabe10' must match pattern '^[a-z]+(\\.[a-z][a-z0-9]*)*$'."
     *        ...
     *       "Aufgabe11": [
     *      ...
     *    "numberOfWarnings": 412,                               <-- more general json information
     *   "lastRepoUpdateTime": "1466081768",
     *   "repository": "https://github.com/MaxMustermann/MusterAufgabe/",
     *  "numberOfErrors": 0
     *
     * @param sRepo - svn/git repository path
     * @param lStartTime - start time of the execution
     * @param sLastRepoUpdateTime - last time the svn/git repository was updated
     * @param lClassList - list with all evaluated java classes
     * @return - a json object consisting of all analyzed checkstyle problems
     */
    public JSONObject buildJson(String sRepo, long lStartTime, String sLastRepoUpdateTime, List<Class> lClassList) {
        //this object contains different attributes for which were needed for the functionality
        OwnJsonProperties oOwnJsonProperties = new OwnJsonProperties();
        boolean bExerciseChange = false;
        boolean bLastRun = false;
        boolean bExerciseNeverChanged = true;

		/* add general information to the JSON object - in the first level */
        oOwnJsonProperties.getOJsonRoot().put("repository", sRepo);
        getClassSeverities(lClassList, oOwnJsonProperties.getOJsonRoot());

        //adds a timestamp when the repository was updated a last
        oOwnJsonProperties.getOJsonRoot().put("lastRepoUpdateTime", sLastRepoUpdateTime);
        LOG.info("Last Update Time: " + sLastRepoUpdateTime);

		/* iterate through ALL classes, which were founded by checkstyle */
        for (int nClassPos = 0; nClassPos < lClassList.size(); nClassPos++) {
            //checks if there is more than one exercise
            if (bExerciseChange) {
                nClassPos--;
                bExerciseChange = false;
            }

            String sExcerciseName = lClassList.get(nClassPos).getsExcerciseName();

			// checks if its the first run (TmpName is empty) or if the name of the last analyzed exercise is
            // the same as the actual. It is needed for a valid json representation.
            if (sExcerciseName.equals(oOwnJsonProperties.getSTmpExcerciseName())
                    || oOwnJsonProperties.getSTmpExcerciseName().equals("")) {
                storeJsonInformation(lClassList, oOwnJsonProperties, bLastRun, bExerciseNeverChanged, nClassPos, sExcerciseName);
            }
			// the last exercise is different through the actual
            else {
                // add the exercise and all related classes and related errors through the Json array for all exercises
                // decrements the class position with the flag bExerciseChange
                // to even check the exercise which did not correspond
                if (lClassList.get(nClassPos).getErrorList().size() > 0) {
                    oOwnJsonProperties.getOJsonExercise().put(oOwnJsonProperties.getSTmpExcerciseName(),
                                                                    oOwnJsonProperties.getLJsonClasses());
                    //puts the exercise object to the array for all of them
                    oOwnJsonProperties.getLJsonExercises().put(oOwnJsonProperties.getOJsonExercise());
                    //initialize new objects for the next exercise
                    oOwnJsonProperties.setOJsonExercise(new JSONObject());
                    oOwnJsonProperties.setLJsonClasses(new JSONArray());
                    oOwnJsonProperties.setSTmpExcerciseName(lClassList.get(nClassPos).getsExcerciseName());
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

        //calculates the time which were needed to execute the checkstyle service
        long lEndTime = System.currentTimeMillis();
        long lTotalTime = (lEndTime - lStartTime);

        oOwnJsonProperties.getOJsonRoot().put("totalExpendedTime", lTotalTime);
        LOG.info("Total expended time: " + lTotalTime);
        //add all evaluated exercises to the main json object
        oOwnJsonProperties.getOJsonRoot().put("assignments", oOwnJsonProperties.getLJsonExercises());

        return oOwnJsonProperties.getOJsonRoot();
    }

    /**
     * this method adds information of the checkstyle validation to the json file.
     * @param lClassList -list with all evaluated java classes
     * @param oOwnJsonProperties - object with important json attributes
     * @param bLastRun - is the actual iterations the last one?
     * @param bExcerciseNeverChanged - Flag which indicates, if there was only one exercise
     * @param nClassPos - actual position in the class list
     * @param sExcerciseName - name of the actual considered class object
     */
    private void storeJsonInformation(List<Class> lClassList, OwnJsonProperties oOwnJsonProperties, boolean bLastRun,
                                      boolean bExcerciseNeverChanged, int nClassPos, String sExcerciseName) {
        /* if errors where founded, a new exercise name is returned by analyeErrors */
        oOwnJsonProperties.setSTmpExcerciseName(analyzeErrors(lClassList, lClassList.get(nClassPos).getErrorList(),
                oOwnJsonProperties.getLJsonClasses(), oOwnJsonProperties.getSTmpExcerciseName(),
                                                                            nClassPos, sExcerciseName));


        /* Condition for the last run if different exercises were founded */
        if (bLastRun) {
            oOwnJsonProperties.getOJsonExercise().put(oOwnJsonProperties.getSTmpExcerciseName(),
                                                            oOwnJsonProperties.getLJsonClasses());
            oOwnJsonProperties.getLJsonExercises().put(oOwnJsonProperties.getOJsonExercise());
        }

        /* Condition for the last run and if there was just one exercise with various exercises */
        if ((nClassPos + 1) == lClassList.size() && bExcerciseNeverChanged) {
            oOwnJsonProperties.getOJsonExercise().put(oOwnJsonProperties.getSTmpExcerciseName(),
                                                            oOwnJsonProperties.getLJsonClasses());
            oOwnJsonProperties.getLJsonExercises().put(oOwnJsonProperties.getOJsonExercise());
        }
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
     * @return -
     */
    private String analyzeErrors(List<Class> lClassList, List<Error> lTmpErrorList,
                                 JSONArray lJsonClasses, String sTmpExerciseName, int nClassPos, String sExerciseName) {
        String sLocalExerciseName = sTmpExerciseName;
        JSONArray lJsonErrors = new JSONArray();
        JSONObject oJsonClass = new JSONObject();

        /* iterate through all Errors */
        for (Error oError : lTmpErrorList) {
            //get all attribute-value pairs of the founded error object
            JSONObject oJsonError = new JSONObject();

            oJsonError.put("line", Integer.toString(oError.getErrorAtLine()));
            oJsonError.put("column", Integer.toString(oError.getColumn()));
            oJsonError.put("severity", oError.getSeverity());
            oJsonError.put("message", oError.getMessage());
            oJsonError.put("source", oError.getSource());
            //add it to the error list of a represented java file
            lJsonErrors.put(oJsonError);
        }

        //only if errors are founded the results should be added to the whole json file
        if (lJsonErrors.length() > 0) {
            sLocalExerciseName = sExerciseName;
            String sFilePath = oUtil.removeUnnecessaryPathParts(lClassList.get(nClassPos).getFullPath());
            oJsonClass.put("filepath", sFilePath);
            oJsonClass.put("errors", lJsonErrors);
            //set the severities for the represented "java file"
            setSeverityCounters(oJsonClass, nClassPos, lClassList);

            lJsonClasses.put(oJsonClass);
        }

        return sLocalExerciseName;
    }

    /**
     * add all errors to a Java Class file, so that each file shows up different error counters
     * @param oJsonClass - json object which represents a java class
     * @param nClassPos -  actual pos of the class
     * @param lClassList - list with all evaluated java classes
     */
    private void setSeverityCounters(JSONObject oJsonClass, int nClassPos, List<Class> lClassList) {
        oJsonClass.put("numberOfErrors", lClassList.get(nClassPos).getErrorCount());
        oJsonClass.put("numberOfWarnings", lClassList.get(nClassPos).getWarningCount());
        oJsonClass.put("numberOfIgnores", lClassList.get(nClassPos).getIgnoreCount());
    }
}
