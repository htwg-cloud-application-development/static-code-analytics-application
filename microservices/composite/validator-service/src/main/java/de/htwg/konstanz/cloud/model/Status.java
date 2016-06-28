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

    /**
     * Decreasing number of open task
     */
    public void decreaseNumberOfOpenTasks() {
        this.numberOfOpenTasks--;
    }

    /**
     * Increase execution time by value
     *
     * @param executionTime time to increase full execution time
     */
    public void increaseFullExecutionTimeWith(int executionTime) {
        this.fullExecutionTime += executionTime;
    }

    /**
     * Decrease number of running tasks
     */
    public void decreaseNumberOfRunningTasks() {
        this.numberOfRunningTasks--;
    }

    /**
     * Increase running tasks
     */
    public void increaseRunningTasks() {
        this.numberOfRunningTasks++;
    }

    /**
     * Increase Number of next task. Call if a task is executed.
     */
    public void increaseNextTask() {
        this.indexOfNextTask++;
    }

    /**
     * Add task to pipeline.
     * @param executionTime execution time of task
     * @param list of all task to be executed.
     */
    public void putIntoPipeline(int executionTime, List<JSONObject> list) {
        this.pipeline.put(executionTime, list);
    }

    /**
     * Hold Information about user depends of repository.
     * @param string repository url
     * @param userId id of user
     */
    public void putIntoRepoUserInformationMap(String string, String userId) {
        this.repoUserInformationMap.put(string, userId);
    }

    /**
     * List to hold all times of executed tasks.
     * @param l long value of time in seconds.
     */
    public void addToStartTimeList(long l) {
        this.startTimeList.add(l);
    }

}
