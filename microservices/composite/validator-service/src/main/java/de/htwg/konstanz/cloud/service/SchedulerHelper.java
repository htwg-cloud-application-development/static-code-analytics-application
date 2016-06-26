package de.htwg.konstanz.cloud.service;

import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import de.htwg.konstanz.cloud.model.CheckstyleStatus;
import de.htwg.konstanz.cloud.model.PmdStatus;
import de.htwg.konstanz.cloud.model.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

@Component
public class SchedulerHelper {

    private static final Logger LOG = LoggerFactory.getLogger(SchedulerHelper.class);

    public JSONObject getTaskWithLongestDuration(Map<Integer, List<JSONObject>> pipeline, int index) {
        int j = 0;
        ArrayList<Integer> keys = new ArrayList<>(pipeline.keySet());
        for (int i = pipeline.size() - 1; i >= 0; i--) {
            List<JSONObject> list = pipeline.get(keys.get(i));
            for (JSONObject obj : list) {
                if (j == index) {
                    return obj;
                }
                j++;
            }
        }
        return null;
    }

    URI getUriWithValue(Map<URI, String> blockedInstancesList, String s) throws JSONException {
        ArrayList<URI> keys = new ArrayList<>(blockedInstancesList.keySet());
        JSONObject sObject = new JSONObject(s);
        for (int i = 0; i < blockedInstancesList.size(); i++) {
            JSONObject jsonObject = new JSONObject(blockedInstancesList.get(keys.get(i)));

            String repoUrl = jsonObject.getString(CustomScheduler.REPOSITORY);
            if (repoUrl.endsWith("/")) {
                repoUrl = repoUrl.substring(0, repoUrl.length() - 1);
            }

            String repoUrl2 = sObject.getString(CustomScheduler.REPOSITORY);
            if (repoUrl2.endsWith("/")) {
                repoUrl2 = repoUrl2.substring(0, repoUrl2.length() - 1);
            }

            if (repoUrl.equals(repoUrl2)) {
                LOG.info("key: " + keys.get(i));
                return keys.get(i);
            }
        }
        return null;
    }

    void removeEntryFromBlockedInstanceListe(Map<URI, String> blockedCheckstyleInstancesList,
                                             URI availableCheckstyleUri) {
        for (Iterator<Map.Entry<URI, String>> it = blockedCheckstyleInstancesList.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<URI, String> entry = it.next();
            if (entry.getKey().equals(availableCheckstyleUri)) {
                it.remove();
            }
        }
    }

    void removeTaskFromLists(Status status, CheckstyleStatus checkstyleStatus, PmdStatus pmdStatus, ArrayList<Integer> toDelete) {
        Iterator<Future<String>> it = checkstyleStatus.getCheckstyleTaskList().listIterator();
        Iterator<Future<String>> itPmd = pmdStatus.getPmdTaskList().listIterator();
        Iterator<Long> itTime = status.getStartTimeList().iterator();

        int j = 0;
        while (it.hasNext()) {
            it.next();
            itPmd.next();
            itTime.next();
            if (toDelete.contains(j)) {
                it.remove();
                itPmd.remove();
                itTime.remove();
            }
            j++;
        }
        toDelete.clear();
    }
}
