package de.htwg.konstanz.cloud.service;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Component
public class CustomScheduler {

    private static final Logger LOG = LoggerFactory.getLogger(CustomScheduler.class);

    @Autowired
    Util util;

    @Autowired
    ValidateRepositoryService validateRepositoryService;

    @Value("${app.aws.init.instance.duration:90000}")
    private int initInstancetDuration;

    @Value("${app.aws.init.instance.max:5}")
    private int maxNumberOfInstancesGreaterFour;

    @Autowired
    DatabaseService databaseService;

    ArrayList<JSONObject> runValidationSchedulerOnAws(JSONArray groups) throws JSONException, InstantiationException, ExecutionException, InterruptedException {
        ArrayList<JSONObject> result = new ArrayList<>();

        int numberOfExecutions = groups.length();
        LOG.info("Number of groups: " + numberOfExecutions);


        int fullExecutionTime = 0;
        Map<Integer, List<JSONObject>> pipeline = new TreeMap<>();
        for (int i = 0; i < groups.length(); i++) {
            JSONObject jsonObject = (JSONObject) groups.get(i);
            LOG.info(jsonObject.toString());
            int executinTime = jsonObject.getInt("executiontime");
            if (executinTime == 0) executinTime = 30000;
            LOG.info("Executiontime for repo " + i + " is: " + executinTime);
            fullExecutionTime += executinTime;


            // if execution time not exists, add new key to pipeline
            if (pipeline.containsKey(executinTime)) {
                // if execution time exists, add to object to existing list
                List<JSONObject> list = pipeline.get(executinTime);
                list.add(jsonObject);
                pipeline.put(executinTime, list);
            } else {
                List<JSONObject> list = new ArrayList<>();
                list.add(jsonObject);
                pipeline.put(executinTime, list);
            }
        }

        LOG.info("Full execution time: " + fullExecutionTime);

        int numberOfInstances = magicCalculateForNumberOfIstances(numberOfExecutions, fullExecutionTime);
        LOG.info("Number of Instances to start: " + numberOfInstances);


        AmazonEC2 ec2 = new AmazonEC2Client(new EnvironmentVariableCredentialsProvider());
        ec2.setRegion(com.amazonaws.regions.Region.getRegion(Regions.EU_CENTRAL_1));

        try {
            startInstances(numberOfInstances, ec2);

            List<Future<String>> taskList = new ArrayList<>();
            int checkstyleInstances = 0;
            int availableInstances = 0;
            int runningTasks = 0;
            int openTasks = numberOfExecutions;
            int index = 0;

            // start execution measurement
            long startTime = System.currentTimeMillis();

            LOG.info("start time: " + startTime);
            LOG.info("open tasks: " + openTasks);
            while (openTasks > 0) {
                // execute
                availableInstances = util.getNumberOfActiveCheckstyleInstances(ec2) - runningTasks;

                if (availableInstances > 0) {
                    LOG.info("availableInstances: " + availableInstances);
                    JSONObject task = getTaskWithLongestDuration(pipeline, index);
                    index++;
                    if (task != null) {
                        Future<String> future = validateRepositoryService.validateRepository(task.toString());
                        // TODO note which service executes task for specific repository
                        // TODO - if is available means not, that its available on eureka
                        // TODO - hold information - which instance executes which task - from loadbalancer

                        taskList.add(future);
                        runningTasks++;
                        LOG.info("running tasks: " + runningTasks);
                    }
                    openTasks--;
                    LOG.info("open tasks: " + openTasks);
                }


                for (int i = 0; i < runningTasks; i++) {
                    if (taskList.get(i).isDone()) {
                        JSONObject obj = new JSONObject(taskList.get(i).get());
                        LOG.info("Task is done: " + groups.getJSONObject(i).getString("groupId"));
                        obj.put("groupId", groups.getJSONObject(i).getString("groupId"));
                        obj.put("duration", (System.currentTimeMillis() - startTime));
                        result.add(obj);
                        taskList.remove(i);
                        runningTasks--;
                        databaseService.saveResult(obj.toString());
                    }
                }
                // slepp 1 second
                Thread.sleep(1000);
            }


        } catch (NoSuchFieldException e) {
            LOG.info(e.getMessage());
        }

        return result;
    }

    void startInstances(int numberOfInstances, AmazonEC2 ec2) throws NoSuchFieldException {
        if (util.getNumberOfActiveCheckstyleInstances(ec2) < numberOfInstances) {
            util.runNewCheckstyleInstance(ec2, numberOfInstances, numberOfInstances);
        }
    }


    JSONObject getTaskWithLongestDuration(Map<Integer, List<JSONObject>> pipeline, int index) {
        int j = 0;
        ArrayList<Integer> keys = new ArrayList<Integer>(pipeline.keySet());
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

    // magic calculation
    int magicCalculateForNumberOfIstances(int numberOfExecutions, int fullExecutionTime) {
        int numberOfInstances;

        if (fullExecutionTime < (2 * initInstancetDuration) || numberOfExecutions < 2) {
            numberOfInstances = 1;
        } else if (fullExecutionTime < (3 * initInstancetDuration) || numberOfExecutions < 3) {
            numberOfInstances = 2;
        } else if (fullExecutionTime < (4 * initInstancetDuration) || numberOfExecutions < 4) {
            numberOfInstances = 3;
        } else if (fullExecutionTime < (5 * initInstancetDuration) || numberOfExecutions < 5) {
            numberOfInstances = 4;
        } else {
            numberOfInstances = maxNumberOfInstancesGreaterFour;
        }
        return numberOfInstances;
    }

}
