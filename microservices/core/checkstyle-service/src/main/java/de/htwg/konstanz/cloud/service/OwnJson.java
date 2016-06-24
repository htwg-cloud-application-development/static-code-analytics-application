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

    private final Util oUtil = new Util();

    void readAndSaveAttributes(int nClassPos, NodeList nList, int nNodePos, List<Class> lClassList) {
        Node nNode = nList.item(nNodePos);

        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
            Element eElement = (Element) nNode;

            int nLine = oUtil.getParsableElement(eElement, "line");
            int nColumn = oUtil.getParsableElement(eElement, "column");

            String sSeverity = oUtil.getNonEmptyElement(eElement, "severity");
            lClassList.get(nClassPos).incErrorType(sSeverity);

            String sMessage = oUtil.getNonEmptyElement(eElement, "message");
            String sSource = oUtil.getNonEmptyElement(eElement, "source");

            Error oError = new Error(nLine, nColumn, sSeverity, sMessage, sSource);

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
        LOG.info("Number of Errors: " + nTmpErrorCount);
        oJsonRoot.put("numberOfWarnings", nTmpWarningCount);
        LOG.info("Number of Warnings: " + nTmpWarningCount);
        oJsonRoot.put("numberOfIgnores", nTmpIgnoreCounter);
        LOG.info("Number of Ignores: " + nTmpIgnoreCounter);

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
            if (sExcerciseName.equals(sTmpExcerciseName) || sTmpExcerciseName.equals("")) {
                lTmpErrorList = lClassList.get(nClassPos).getErrorList();
                JSONArray lJsonErrors = new JSONArray();
                JSONObject oJsonClass = new JSONObject();

				/* all Errors */
                for (Error oError : lTmpErrorList) {
                    JSONObject oJsonError = new JSONObject();

                    oJsonError.put("line", Integer.toString(oError.getErrorAtLine()));
                    oJsonError.put("column", Integer.toString(oError.getColumn()));
                    oJsonError.put("severity", oError.getSeverity());
                    oJsonError.put("message", oError.getMessage());
                    oJsonError.put("source", oError.getSource());

                    lJsonErrors.put(oJsonError);
                }

                if (lJsonErrors.length() > 0) {
                    sTmpExcerciseName = sExcerciseName;
                    String sFilePath = oUtil.removeUnnecessaryPathParts(lClassList.get(nClassPos).getFullPath());

                    oJsonClass.put("filepath", sFilePath);
                    oJsonClass.put("errors", lJsonErrors);
                    setSeverityCounters(oJsonClass, nClassPos, lClassList);

                    lJsonClasses.put(oJsonClass);
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
        LOG.info("Total expended time: " + lTotalTime);
        oJsonRoot.put("assignments", lJsonExercises);

        return oJsonRoot;
    }

    private void setSeverityCounters(JSONObject oJsonClass, int nClassPos, List<Class> lClassList) {
        oJsonClass.put("numberOfErros", lClassList.get(nClassPos).getErrorCount());
        oJsonClass.put("numberOfWarnings", lClassList.get(nClassPos).getWarningCount());
        oJsonClass.put("numberOfIgnores", lClassList.get(nClassPos).getIgnoreCount());
    }
}
