package de.htwg.konstanz.cloud.service;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import de.htwg.konstanz.cloud.model.ValidationData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Component
public class CustomScheduler {

    private static final Logger LOG = LoggerFactory.getLogger(CustomScheduler.class);

    @Autowired
    Util util;

    @Autowired
    ValidateRepositoryService validateRepositoryService;

    @Autowired
    private LoadBalancerClient loadBalancer;

    @Value("${app.aws.init.instance.duration:90000}")
    private int initInstancetDuration;

    @Value("${app.aws.init.instance.max:5}")
    private int maxNumberOfInstancesGreaterFour;

    @Autowired
    DatabaseService databaseService;

    ArrayList<JSONObject> runValidationSchedulerOnAws(JSONArray groups) throws JSONException, InstantiationException, ExecutionException, InterruptedException {


        int fullExecutionTime = 0;
        Map<Integer, List<JSONObject>> pipeline = new TreeMap<>();
        for (int i = 0; i < groups.length(); i++) {
            JSONObject jsonObject = (JSONObject) groups.get(i);
            int executinTime = jsonObject.optInt("executiontime", 60000);
            if (executinTime == 0) executinTime = 30000;
            fullExecutionTime += executinTime;

            LOG.info(jsonObject.toString());
            LOG.info("Executiontime for repo " + i + " is: " + executinTime);

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

        AmazonEC2 ec2 = new AmazonEC2Client(new EnvironmentVariableCredentialsProvider());
        ec2.setRegion(com.amazonaws.regions.Region.getRegion(Regions.EU_CENTRAL_1));


        List<Future<String>> taskList = new ArrayList<>();
        List<Long> startTimeList = new ArrayList<>();
        List<URI> availableInstancesList = new ArrayList<>();
        Map<URI, String> blockedInstancesList = new HashMap<>();
        ArrayList<JSONObject> resultList = new ArrayList<>();

        int openTasks = groups.length();
        int numberOfInstancesToStart = magicCalculateForNumberOfIstances(openTasks, fullExecutionTime);
        int availableInstances;
        int runningTasks = 0;
        int indexOfNextTask = 0;

        LOG.info("Full execution time: " + fullExecutionTime);
        LOG.info("Number of Instances to start: " + numberOfInstancesToStart);
        LOG.info("open tasks: " + openTasks);

        try {
            startInstances(numberOfInstancesToStart, ec2);

            // while execution not finished
            while (openTasks > 0) {

                // check if new instance available and free
                availableInstances = util.getNumberOfActiveCheckstyleInstances(ec2) - runningTasks;

                if (availableInstances > 0) {
                    LOG.info("availableInstances: " + availableInstances);
                    JSONObject task = getTaskWithLongestDuration(pipeline, indexOfNextTask);
                    if (task != null) {
                        boolean isExecute = false;
                        if (availableInstancesList.isEmpty()) {
                            ServiceInstance instance = loadBalancer.choose("checkstyle");

LOG.info("instanceurl: "+instance.getUri());
                            if (!blockedInstancesList.containsKey(instance.getUri())) {

LOG.info("validate: " + instance.getUri());
LOG.info("blabla: " + task.toString());
                                Future<String> future = validateRepositoryService.validateRepository(task.toString(), instance.getUri());
                                blockedInstancesList.put(instance.getUri(), task.toString());
                                taskList.add(future);
                                isExecute = true;
                            }
                        } else {
 
LOG.info("HIER KRACHT ES!!! wegen availbaleInstancesList");
                           Future<String> future = validateRepositoryService.validateRepository(task.toString(), availableInstancesList.get(0));
                            blockedInstancesList.put(availableInstancesList.remove(0), task.toString());
                            taskList.add(future);
                            isExecute = true;
                        }

                        // update status of task execution
                        if (isExecute) {
                            startTimeList.add(System.currentTimeMillis());
                            runningTasks++;
                            indexOfNextTask++;
                            openTasks--;

                            LOG.info("running tasks: " + runningTasks);
                            LOG.info("open tasks: " + openTasks);
                            LOG.info("index of next task: " + indexOfNextTask);
                        }
                    }
                }



ArrayList<Integer> tmp = new ArrayList<>();

                for (int i = 0; i < runningTasks; i++) {
LOG.info("check running task nr: " + i);



                    if (taskList.get(i) != null) {


if(taskList.get(i).isDone()) {
LOG.info("task is done");
LOG.info("Laenge der taskliste" + taskList.size());


                        JSONObject obj = new JSONObject(taskList.get(i).get());
                        // TODO validate parameter - groupId exists?

//LOG.info(obj.toString());
LOG.info("OBJECT");
//                        LOG.info("Task is done: " + obj.getString("userId"));
//                        obj.put("userId", obj.getString("userId"));
                        obj.put("duration", (System.currentTimeMillis() - startTimeList.get(i)));

                        // TODO VALIDATE!!! - remove entry from blocked instance and add to available
                        ValidationData data = new ValidationData();
LOG.info("huhu");
                        data.setRepository(obj.getString("repository"));
                        URI availableUri = getUriWithValue(blockedInstancesList, data.toString());
                        availableInstancesList.add(availableUri);
LOG.info("Finished!");
                        blockedInstancesList.remove(availableUri);

                        resultList.add(obj);

tmp.add(i);
//                        taskList.remove(i);
//                        startTimeList.remove(i);
//                        runningTasks--;
                        databaseService.saveCheckstleResult(obj.toString());
LOG.info("hhhhhhhh");
                    }}
                }
  

for(int i = 0; i < tmp.size(); i++) {

LOG.info("remove task: "+ tmp.get(i));	
	taskList.set(tmp.get(i), null);
LOG.info("task liste länge: " + taskList.size());	
startTimeList.remove(tmp.get(i));
	runningTasks--;
LOG.info("neu running tasks: " + runningTasks);
}

              // slepp 1 second
                Thread.sleep(1000);
            }


        } catch (NoSuchFieldException e) {
            LOG.info(e.getMessage());
        }

        return resultList;
    }

    private URI getUriWithValue(Map<URI, String> blockedInstancesList, String s) {
        ArrayList<URI> keys = new ArrayList<>(blockedInstancesList.keySet());
        for (int i = 0; i < blockedInstancesList.size(); i++) {
            if (blockedInstancesList.get(keys.get(i)).equals(s)) {
                return keys.get(i);
            }
        }
        return null;
    }

    private void startInstances(int numberOfInstances, AmazonEC2 ec2) throws NoSuchFieldException {
        if (util.getNumberOfActiveCheckstyleInstances(ec2) < numberOfInstances) {
            util.runNewCheckstyleInstance(ec2, numberOfInstances, numberOfInstances);
        }
    }


    private JSONObject getTaskWithLongestDuration(Map<Integer, List<JSONObject>> pipeline, int index) {
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

    // magic calculation
    private int magicCalculateForNumberOfIstances(int numberOfExecutions, int fullExecutionTime) {
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
