package de.htwg.konstanz.cloud.service;

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

class OwnJson {
    private static final Logger LOG = LoggerFactory.getLogger(OwnJson.class);

    private final Util util = new Util();

    void readAndSaveAttributes(int nClassPos, NodeList nList, int nNodePos, List<Class> lClassList) {
        Node nNode = nList.item(nNodePos);

        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
            Element eElement = (Element) nNode;

            String sMessage = eElement.getFirstChild().getTextContent();

            int nLineBegin = util.getParsableElement(eElement, "beginline");
            int nLineEnd = util.getParsableElement(eElement, "endline");
            int nColumnBegin = util.getParsableElement(eElement, "begincolumn");
            int nColumnEnd = util.getParsableElement(eElement, "endcolumn");
            int nPriority = util.getParsableElement(eElement, "priority");

            lClassList.get(nClassPos).incErrorType(nPriority);

            String sClassName = util.getNonEmptyElement(eElement, "class");
            String sRule = util.getNonEmptyElement(eElement, "rule");
            String sRuleset = util.getNonEmptyElement(eElement, "ruleset");
            String sPackage = util.getNonEmptyElement(eElement, "package");

            Error oError = new Error(nLineBegin, nLineEnd, nColumnBegin, nColumnEnd, nPriority,
                    sRule, sClassName, sPackage, sRuleset, sMessage);

            lClassList.get(nClassPos).getErrorList().add(oError);
        }
    }

    JSONObject buildJson(String sRepo, long lStartTime, String sLastRepoUpdateTime, List<Class> lClassList) {
        List<Error> lTmpErrorList;
        JSONObject oJsonRoot = new JSONObject();
        JSONObject oJsonExercise = new JSONObject();
        JSONArray lJsonClasses = new JSONArray();
        JSONArray lJsonExercises = new JSONArray();
        String sTmpExcerciseName = "";
        boolean bExcerciseChange = false;
        boolean bLastRun = false;
        boolean bExcerciseNeverChanged = true;
        int nTmpErrorCount = 0;
        int nTmpWarningCount = 0;
        int nTmpIgnoreCounter = 0;

		/* add general information to the JSON object */
        oJsonRoot.put("repository", sRepo);

        /* get severities of the whole project */
        for (Class oFormattedClassList : lClassList) {
            nTmpErrorCount += oFormattedClassList.getErrorCount();
            nTmpWarningCount += oFormattedClassList.getWarningCount();
            nTmpIgnoreCounter += oFormattedClassList.getIgnoreCount();
        }
        oJsonRoot.put("numberOfErrors", nTmpErrorCount);
        oJsonRoot.put("numberOfWarnings", nTmpWarningCount);
        oJsonRoot.put("numberOfIgnores", nTmpIgnoreCounter);

        oJsonRoot.put("lastRepoUpdateTime", sLastRepoUpdateTime);
        LOG.info("Last Update Time: " + sLastRepoUpdateTime);

		/* all Classes */
        for (int nClassPos = 0; nClassPos < lClassList.size(); nClassPos++) {
            if (bExcerciseChange) {
                nClassPos--;
                bExcerciseChange = false;
            }

            String sExcerciseName = lClassList.get(nClassPos).getsExcerciseName();

			/* first run the TmpName is empty */
            if (sExcerciseName.equals(sTmpExcerciseName) || "".equals(sTmpExcerciseName)) {
                lTmpErrorList = lClassList.get(nClassPos).getErrorList();

                if (!lTmpErrorList.isEmpty()) {
                    JSONArray lJsonErrors = new JSONArray();
                    JSONObject oJsonClass = new JSONObject();

				    /* all Errors */
                    for (Error aLTmpErrorList : lTmpErrorList) {
                        JSONObject oJsonError = new JSONObject();

                        oJsonError.put("lineBegin", Integer.toString(aLTmpErrorList.getLineBegin()));
                        oJsonError.put("lineEnd", Integer.toString(aLTmpErrorList.getLineEnd()));
                        oJsonError.put("columnBegin", Integer.toString(aLTmpErrorList.getColumnBegin()));
                        oJsonError.put("columnEnd", Integer.toString(aLTmpErrorList.getColumnEnd()));
                        oJsonError.put("priority", Integer.toString(aLTmpErrorList.getPriority()));
                        oJsonError.put("rule", aLTmpErrorList.getRule());
                        oJsonError.put("class", aLTmpErrorList.getClassName());
                        oJsonError.put("package", aLTmpErrorList.getPackage());
                        oJsonError.put("ruleset", aLTmpErrorList.getRuleset());
                        oJsonError.put("message", aLTmpErrorList.getMessage());

                        lJsonErrors.put(oJsonError);
                    }

                    if (lJsonErrors.length() > 0) {
                        sTmpExcerciseName = sExcerciseName;

                        String sFilePath = util.removeUnnecessaryPathParts(lClassList.get(nClassPos).getFullPath());
                        oJsonClass.put("filepath", sFilePath);
                        oJsonClass.put("errors", lJsonErrors);
                        setPriorityCounters(oJsonClass, nClassPos, lClassList);

                        lJsonClasses.put(oJsonClass);
                    }
                }

                /* last run if different exercises were found */
                if (bLastRun) {
                    oJsonExercise.put(sTmpExcerciseName, lJsonClasses);
                    lJsonExercises.put(oJsonExercise);
                }
                /* last run if there was just one exercise */
                if ((nClassPos + 1) == lClassList.size() && bExcerciseNeverChanged) {
                    oJsonExercise.put(sTmpExcerciseName, lJsonClasses);
                    lJsonExercises.put(oJsonExercise);
                }
            }
            /* swap for a different exercise */
            else {
                if (lClassList.get(nClassPos).getErrorList().size() > 0) {
                    oJsonExercise.put(sTmpExcerciseName, lJsonClasses);
                    lJsonExercises.put(oJsonExercise);
                    oJsonExercise = new JSONObject();
                    lJsonClasses = new JSONArray();
                    sTmpExcerciseName = lClassList.get(nClassPos).getsExcerciseName();
                    bExcerciseChange = true;
                    bExcerciseNeverChanged = false;

				    /* decrement the position to get the last class from the list */
                    if (nClassPos + 1 == lClassList.size()) {
                        nClassPos--;
                        bExcerciseChange = false;
                        bLastRun = true;
                    }
                }
            }
        }

        long lEndTime = System.currentTimeMillis();
        long lTotalTime = (lEndTime - lStartTime);

        oJsonRoot.put("totalExpendedTime", lTotalTime);
        oJsonRoot.put("assignments", lJsonExercises);

        LOG.debug("Pmd Static Analysis check for Repo: " + sRepo);
        return oJsonRoot;
    }

    private void setPriorityCounters(JSONObject oJsonClass, int nClassPos, List<Class> lClassList) {
        oJsonClass.put("numberOfErros", lClassList.get(nClassPos).getErrorCount());
        oJsonClass.put("numberOfWarnings", lClassList.get(nClassPos).getWarningCount());
        oJsonClass.put("numberOfIgnores", lClassList.get(nClassPos).getIgnoreCount());
    }
}
