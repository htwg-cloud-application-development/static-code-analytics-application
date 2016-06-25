package de.htwg.konstanz.cloud.model;

import com.amazonaws.util.json.JSONObject;
import lombok.Data;

import java.util.*;

@Data
@SuppressWarnings("PMD")
public class Status {
    private int numberOfTasks = 0;

    private int numberOfOpenTasks = 0;

    private int numberOfRunningTasks = 0;

    private int fullExecutionTime = 0;

    private int indexOfNextTask = 0;

    private Map<Integer, List<JSONObject>> pipeline = new TreeMap<>();

    private Map<String, String> repoUserInformationMap = new HashMap<>();

    private List<Long> startTimeList = new ArrayList<>();

    private ArrayList<JSONObject> resultList = new ArrayList<>();

    public void decreaseNumberOfOpenTasks() {
        this.numberOfOpenTasks--;
    }

    public void increaseFullExecutionTimeWith(int executionTime) {
        this.fullExecutionTime += executionTime;
    }

    public void decreaseNumberOfRunningTasks() {
        this.numberOfRunningTasks--;
    }

    public void increaseRunningTasks() {
        this.numberOfRunningTasks++;
    }

    public void increaseNextTask() {
        this.indexOfNextTask++;
    }

    public void putIntoPipeline(int executionTime, List<JSONObject> list) {
        this.pipeline.put(executionTime, list);
    }

    public void putIntoRepoUserInformationMap(String string, String userId) {
        this.repoUserInformationMap.put(string, userId);
    }

    public void addToStartTimeList(long l) {
        this.startTimeList.add(l);
    }

}
