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


    private void getClassSeverities(List<Class> lClassList, JSONObject oJsonRoot) {
        int nTmpErrorCount = 0;
        int nTmpWarningCount = 0;
        int nTmpIgnoreCounter = 0;

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
    }


    JSONObject buildJson(String sRepo, long lStartTime, String sLastRepoUpdateTime, List<Class> lClassList) {
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

            String sExcerciseName = lClassList.get(nClassPos).getsExcerciseName();

			/* first run the TmpName is empty */
            if (sExcerciseName.equals(oOwnJsonProperties.getSTmpExcerciseName())
                    || oOwnJsonProperties.getSTmpExcerciseName().equals("")) {
                storeJsonInformation(lClassList, oOwnJsonProperties, bLastRun, bExerciseNeverChanged, nClassPos, sExcerciseName);
            }
			/* swap for a different exercise */
            else {
                if (lClassList.get(nClassPos).getErrorList().size() > 0) {
                    oOwnJsonProperties.getOJsonExercise().put(oOwnJsonProperties.getSTmpExcerciseName(),
                                                                    oOwnJsonProperties.getLJsonClasses());
                    oOwnJsonProperties.getLJsonExercises().put(oOwnJsonProperties.getOJsonExercise());
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

        long lEndTime = System.currentTimeMillis();
        long lTotalTime = (lEndTime - lStartTime);

        oOwnJsonProperties.getOJsonRoot().put("totalExpendedTime", lTotalTime);
        LOG.info("Total expended time: " + lTotalTime);
        oOwnJsonProperties.getOJsonRoot().put("assignments", oOwnJsonProperties.getLJsonExercises());

        return oOwnJsonProperties.getOJsonRoot();
    }

    private void storeJsonInformation(List<Class> lClassList, OwnJsonProperties oOwnJsonProperties, boolean bLastRun,
                                      boolean bExcerciseNeverChanged, int nClassPos, String sExcerciseName) {
        /*  */
        oOwnJsonProperties.setSTmpExcerciseName(analyzeErrors(lClassList, lClassList.get(nClassPos).getErrorList(),
                oOwnJsonProperties.getLJsonClasses(), oOwnJsonProperties.getSTmpExcerciseName(),
                                                                            nClassPos, sExcerciseName));


        /* last run if different exercises were found */
        if (bLastRun) {
            oOwnJsonProperties.getOJsonExercise().put(oOwnJsonProperties.getSTmpExcerciseName(),
                                                            oOwnJsonProperties.getLJsonClasses());

            oOwnJsonProperties.getLJsonExercises().put(oOwnJsonProperties.getOJsonExercise());
        }

				/* last run if there was just one exercise */
        if ((nClassPos + 1) == lClassList.size() && bExcerciseNeverChanged) {
            oOwnJsonProperties.getOJsonExercise().put(oOwnJsonProperties.getSTmpExcerciseName(),
                                                            oOwnJsonProperties.getLJsonClasses());
            oOwnJsonProperties.getLJsonExercises().put(oOwnJsonProperties.getOJsonExercise());
        }
    }

    private String analyzeErrors(List<Class> lClassList, List<Error> lTmpErrorList,
                                 JSONArray lJsonClasses, String sTmpExerciseName, int nClassPos, String sExerciseName) {
        String sLocalExerciseName = sTmpExerciseName;
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
            sLocalExerciseName = sExerciseName;
            String sFilePath = oUtil.removeUnnecessaryPathParts(lClassList.get(nClassPos).getFullPath());
            oJsonClass.put("filepath", sFilePath);
            oJsonClass.put("errors", lJsonErrors);
            setSeverityCounters(oJsonClass, nClassPos, lClassList);

            lJsonClasses.put(oJsonClass);
        }

        return sLocalExerciseName;
    }

    private void setSeverityCounters(JSONObject oJsonClass, int nClassPos, List<Class> lClassList) {
        oJsonClass.put("numberOfErros", lClassList.get(nClassPos).getErrorCount());
        oJsonClass.put("numberOfWarnings", lClassList.get(nClassPos).getWarningCount());
        oJsonClass.put("numberOfIgnores", lClassList.get(nClassPos).getIgnoreCount());
    }
}
