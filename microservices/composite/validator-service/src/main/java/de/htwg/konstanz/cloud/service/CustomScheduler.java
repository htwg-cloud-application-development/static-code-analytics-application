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

    ArrayList<JSONObject> runValidationSchedulerOnAws(JSONArray groups) throws JSONException, InstantiationException, ExecutionException, InterruptedException, NoSuchFieldException {


        int fullExecutionTime = 0;
        Map<Integer, List<JSONObject>> pipeline = new TreeMap<>();
        Map<String, String> repoUserInformationMap = new HashMap<>();

        for (int i = 0; i < groups.length(); i++) {
            JSONObject jsonObject = (JSONObject) groups.get(i);
            int executionTime = jsonObject.optInt("executiontime", 60000);
            fullExecutionTime += executionTime;

            LOG.info(jsonObject.toString());
            LOG.info("Executiontime for repo " + i + " is: " + executionTime);

            // if execution time not exists, add new key to pipeline
            if (pipeline.containsKey(executionTime)) {
                // if execution time exists, add to object to existing list
                List<JSONObject> list = pipeline.get(executionTime);
                list.add(jsonObject);
                pipeline.put(executionTime, list);
            } else {
                List<JSONObject> list = new ArrayList<>();
                list.add(jsonObject);
                pipeline.put(executionTime, list);
            }
            repoUserInformationMap.put(jsonObject.getString("repository"), jsonObject.getString("userId"));
        }

        return startScheduling(groups, fullExecutionTime, pipeline, repoUserInformationMap);
    }

    private ArrayList<JSONObject> startScheduling(JSONArray groups, int fullExecutionTime, Map<Integer, List<JSONObject>> pipeline, Map<String, String> repoUserInformationMap) throws NoSuchFieldException, JSONException, InterruptedException, ExecutionException, InstantiationException {
        AmazonEC2 ec2 = new AmazonEC2Client(new EnvironmentVariableCredentialsProvider());
        ec2.setRegion(com.amazonaws.regions.Region.getRegion(Regions.EU_CENTRAL_1));


        List<Future<String>> checkstyleTaskList = new ArrayList<>();
        List<Future<String>> pmdTaskList = new ArrayList<>();
        List<Long> startTimeList = new ArrayList<>();
        List<URI> availableCheckstyleInstancesList = new ArrayList<>();
        List<URI> availablePmdInstancesList = new ArrayList<>();
        Map<URI, String> blockedCheckstyleInstancesList = new HashMap<>();
        Map<URI, String> blockedPmdInstancesList = new HashMap<>();
        ArrayList<JSONObject> resultList = new ArrayList<>();

        int openTasks = groups.length();
        int numberOfInstancesToStart = magicCalculateForNumberOfIstances(openTasks, fullExecutionTime);
        int availableCheckstyleInstances;
        int availablePmdInstances;
        int runningTasks = 0;
        int indexOfNextTask = 0;

        LOG.info("Full execution time: " + fullExecutionTime);
        LOG.info("Number of Instances to start: " + numberOfInstancesToStart);
        LOG.info("open tasks: " + openTasks);

        startInstances(numberOfInstancesToStart, ec2);

        boolean noFinished = true;
        // while execution not finished
        while (openTasks > 0 || noFinished) {

            // check if new instance available and free
            availableCheckstyleInstances = util.getNumberOfActiveCheckstyleInstances(ec2) - runningTasks;
            availablePmdInstances = util.getNumberOfActivePmdInstances(ec2) - runningTasks;

            if (availableCheckstyleInstances > 0 && availablePmdInstances > 0) {
                LOG.info("availableCheckstyleInstances: " + availableCheckstyleInstances);
                LOG.info("availablePmdInstances: " + availablePmdInstances);
                JSONObject task = getTaskWithLongestDuration(pipeline, indexOfNextTask);
                if (task != null) {
                    boolean isExecute = false;
                    if (availableCheckstyleInstancesList.isEmpty() && availablePmdInstancesList.isEmpty()) {
                        ServiceInstance checkstyleIinstance = loadBalancer.choose("checkstyle");
                        ServiceInstance pmdInstance = loadBalancer.choose("pmd");

                        LOG.info("checkstlye instanceurl: " + checkstyleIinstance.getUri());
                        LOG.info("pmd instanceurl: " + pmdInstance.getUri());

                        if (!blockedCheckstyleInstancesList.containsKey(checkstyleIinstance.getUri())
                                && !blockedPmdInstancesList.containsKey(pmdInstance.getUri())) {

                            validateWithNewInstance(checkstyleTaskList, blockedCheckstyleInstancesList, task, checkstyleIinstance);
                            validateWithNewInstance(pmdTaskList, blockedPmdInstancesList, task, pmdInstance);

                            isExecute = true;
                        }
                    } else {

                        validateWithAvailableInstance(checkstyleTaskList, availableCheckstyleInstancesList, blockedCheckstyleInstancesList, task.toString());
                        validateWithAvailableInstance(pmdTaskList, availablePmdInstancesList, blockedPmdInstancesList, task.toString());

                        util.removeFirstElementFormList(availableCheckstyleInstancesList);
                        util.removeFirstElementFormList(availablePmdInstancesList);

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


            ArrayList<Integer> toDelete = new ArrayList<>();

            for (int i = 0; i < runningTasks; i++) {
                LOG.info("check running task nr: " + i);
                if (checkstyleTaskList.get(i) != null && pmdTaskList.get(i) != null) {
                    if (checkstyleTaskList.get(i).isDone() && pmdTaskList.get(i).isDone()) {
                        LOG.info("checkstyle and pmd tasks are done");

                        JSONObject checkstyleObj = new JSONObject(checkstyleTaskList.get(i).get());
                        JSONObject pmdObj = new JSONObject(checkstyleTaskList.get(i).get());
                        JSONObject result = new JSONObject();
                        result.put("checkstyle", checkstyleObj);
                        result.put("pmd", pmdObj);
                        result.put("userId", repoUserInformationMap.get(checkstyleObj.getString("repository")));
                        result.put("duration", (System.currentTimeMillis() - startTimeList.get(i)));
                        result.put("repository", checkstyleObj.getString("repository"));
                        ValidationData data = new ValidationData();
                        data.setRepository(checkstyleObj.getString("repository"));

                        URI availableCheckstyleUri = getUriWithValue(blockedCheckstyleInstancesList, data.toString());
                        URI availablePmdUri = getUriWithValue(blockedPmdInstancesList, data.toString());

                        availableCheckstyleInstancesList.add(availableCheckstyleUri);
                        availablePmdInstancesList.add(availablePmdUri);

                        // remove entry from blocked instance list
                        removeEntryFromBlockedInstanceListe(blockedCheckstyleInstancesList, availableCheckstyleUri);
                        removeEntryFromBlockedInstanceListe(blockedPmdInstancesList, availablePmdUri);

                        resultList.add(result);

                        if (resultList.size() == groups.length()) {
                            noFinished = false;
                        }
                        toDelete.add(i);
                        databaseService.saveCheckstleResult(checkstyleObj.toString());
                        databaseService.savePmdResult(pmdObj.toString());
                    }
                }
            }


            if (!toDelete.isEmpty()) {
                runningTasks = removeTaskFromLists(checkstyleTaskList, pmdTaskList, startTimeList, runningTasks, toDelete);
            }

            // slepp 1 second
            Thread.sleep(1000);
        }
        return resultList;
    }

    private int removeTaskFromLists(List<Future<String>> checkstyleTaskList, List<Future<String>> pmdTaskList, List<Long> startTimeList, int runningTasks, ArrayList<Integer> toDelete) {
        Iterator<Future<String>> it = checkstyleTaskList.listIterator();
        Iterator<Future<String>> itPmd = pmdTaskList.listIterator();
        Iterator<Long> itTime = startTimeList.iterator();
        int j = 0;
        while (it.hasNext()) {
            it.next();
            itPmd.next();
            itTime.next();
            if (toDelete.contains(j)) {
                it.remove();
                itPmd.remove();
                itTime.remove();
                runningTasks--;
            }
            j++;
        }
        toDelete.clear();
        return runningTasks;
    }

    private void removeEntryFromBlockedInstanceListe(Map<URI, String> blockedCheckstyleInstancesList, URI availableCheckstyleUri) {
        for (Iterator<Map.Entry<URI, String>> it = blockedCheckstyleInstancesList.entrySet().iterator(); it.hasNext(); ) {
            Map.Entry<URI, String> entry = it.next();
            if (entry.getKey().equals(availableCheckstyleUri)) {
                it.remove();
            }
        }
    }

    private void validateWithNewInstance(List<Future<String>> checkstyleTaskList, Map<URI, String> blockedCheckstyleInstancesList, JSONObject task, ServiceInstance checkstyleIinstance) {
        Future<String> checkstyleFuture = validateRepositoryService.validateRepository(task.toString(), checkstyleIinstance.getUri());
        blockedCheckstyleInstancesList.put(checkstyleIinstance.getUri(), task.toString());
        checkstyleTaskList.add(checkstyleFuture);
    }

    private void validateWithAvailableInstance(List<Future<String>> checkstyleTaskList, List<URI> availableCheckstyleInstancesList, Map<URI, String> blockedCheckstyleInstancesList, String taskToExecute) {
        Future<String> checkstyleFuture = validateRepositoryService.validateRepository(taskToExecute, availableCheckstyleInstancesList.get(0));
        blockedCheckstyleInstancesList.put(availableCheckstyleInstancesList.remove(0), taskToExecute);
        checkstyleTaskList.add(checkstyleFuture);
    }

    private URI getUriWithValue(Map<URI, String> blockedInstancesList, String s) throws JSONException {
        ArrayList<URI> keys = new ArrayList<>(blockedInstancesList.keySet());
        JSONObject sObject = new JSONObject(s);
        for (int i = 0; i < blockedInstancesList.size(); i++) {
            JSONObject jsonObject = new JSONObject(blockedInstancesList.get(keys.get(i)));

            String repoUrl = jsonObject.getString("repository");
            if (repoUrl.endsWith("/")) {
                repoUrl = repoUrl.substring(0, repoUrl.length() - 1);
            }

            String repoUrl2 = sObject.getString("repository");
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

    private void startInstances(int numberOfInstances, AmazonEC2 ec2) throws NoSuchFieldException {
        if (util.getNumberOfActiveCheckstyleInstances(ec2) < numberOfInstances) {
            util.runNewCheckstyleInstance(ec2, numberOfInstances, numberOfInstances);
        }
        if (util.getNumberOfActivePmdInstances(ec2) < numberOfInstances) {
            util.runNewPmdInstance(ec2, numberOfInstances, numberOfInstances);
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
