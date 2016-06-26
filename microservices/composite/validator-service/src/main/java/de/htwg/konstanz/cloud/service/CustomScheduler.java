package de.htwg.konstanz.cloud.service;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import de.htwg.konstanz.cloud.model.CheckstyleStatus;
import de.htwg.konstanz.cloud.model.PmdStatus;
import de.htwg.konstanz.cloud.model.Status;
import de.htwg.konstanz.cloud.model.ValidationData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Component
public class CustomScheduler {

    private static final Logger LOG = LoggerFactory.getLogger(CustomScheduler.class);

    public static final String REPOSITORY = "repository";

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
    private DatabaseService databaseService;

    @Autowired
    private SchedulerHelper helper;

    ArrayList<JSONObject> runValidationSchedulerOnAws(JSONArray groups) throws JSONException, InstantiationException,
            ExecutionException, InterruptedException, NoSuchFieldException {

        // init status object
        Status status = new Status();
        status.setNumberOfOpenTasks(groups.length());

        for (int i = 0; i < groups.length(); i++) {
            JSONObject jsonObject = (JSONObject) groups.get(i);
            int executionTime = jsonObject.optInt("executiontime", 60000);
            status.increaseFullExecutionTimeWith(executionTime);

            LOG.info(jsonObject.toString());
            LOG.info("Executiontime for repo " + i + " is: " + executionTime);

            // if execution time not exists, add new key to pipeline
            if (status.getPipeline().containsKey(executionTime)) {
                // if execution time exists, add to object to existing list
                List<JSONObject> list = status.getPipeline().get(executionTime);
                list.add(jsonObject);
                status.putIntoPipeline(executionTime, list);
            } else {
                List<JSONObject> list = new ArrayList<>();
                list.add(jsonObject);
                status.putIntoPipeline(executionTime, list);
            }
            status.putIntoRepoUserInformationMap(jsonObject.getString(REPOSITORY), jsonObject.getString("userId"));
        }

        return startScheduling(status);
    }

    private ArrayList<JSONObject> startScheduling(Status status) throws NoSuchFieldException,
            JSONException, InterruptedException, ExecutionException, InstantiationException {

        AmazonEC2 ec2 = new AmazonEC2Client(new EnvironmentVariableCredentialsProvider());
        ec2.setRegion(com.amazonaws.regions.Region.getRegion(Regions.EU_CENTRAL_1));

        int numberOfInstancesToStart = magicCalculateForNumberOfIstances(status.getNumberOfTasks(), status.getFullExecutionTime());
        startInstances(numberOfInstancesToStart, ec2);

        LOG.info("Full execution time: " + status.getFullExecutionTime());
        LOG.info("Number of Instances to start: " + numberOfInstancesToStart);
        LOG.info("open tasks: " + status.getNumberOfOpenTasks());

        CheckstyleStatus checkstyleStatus = new CheckstyleStatus();
        PmdStatus pmdStatus = new PmdStatus();

        boolean noFinished = true;
        // while execution not finished
        while (status.getNumberOfOpenTasks() > 0 || noFinished) {
            LOG.info("NumberOfOpenTasks: " + status.getNumberOfOpenTasks());
            LOG.info("noFinished: " + noFinished);

            // check if new instance available and free
            checkstyleStatus.setAvailableCheckstyleInstances(util.getNumberOfActiveCheckstyleInstances(ec2) - status.getNumberOfRunningTasks());
            pmdStatus.setAvailablePmdInstances(util.getNumberOfActivePmdInstances(ec2) - status.getNumberOfRunningTasks());

            LOG.info("numberOfActiveChecksyyleInstances: " + checkstyleStatus.getAvailableCheckstyleInstances());
            LOG.info("numberOfActivePmdInstances: " + pmdStatus.getAvailablePmdInstances());

            //if (status.getAvailableCheckstyleInstances() > 0 && status.getAvailablePmdInstances() > 0) {
            if (checkstyleStatus.isServiceAvailable() && pmdStatus.isServiceAvailable()) {
                startValidationIfServicesActive(status, checkstyleStatus, pmdStatus);
            }

            checkRunningTasks(status, checkstyleStatus, pmdStatus);

            LOG.info("resultListSize: " + status.getResultList().size());
            LOG.info("number of Tasks: " + status.getNumberOfTasks());
            if (status.getResultList().size() == status.getNumberOfTasks()) {
                noFinished = false;
            }

            // slepp 1 second
            Thread.sleep(1000);
        }
        return status.getResultList();
    }

    private void startValidationIfServicesActive(Status status, CheckstyleStatus checkstyleStatus, PmdStatus pmdStatus) {
        LOG.info("availableCheckstyleInstances: " + checkstyleStatus.getAvailableCheckstyleInstances());
        LOG.info("availablePmdInstances: " + pmdStatus.getAvailablePmdInstances());
        JSONObject task = helper.getTaskWithLongestDuration(status.getPipeline(), status.getIndexOfNextTask());
        if (task != null) {
            boolean isExecute = false;
            if (checkstyleStatus.noInstanceAvailable() && pmdStatus.noInstanceAvailable()) {
                ServiceInstance checkstyleIinstance = loadBalancer.choose("checkstyle");
                ServiceInstance pmdInstance = loadBalancer.choose("pmd");

                LOG.info("checkstlye instanceurl: " + checkstyleIinstance.getUri());
                LOG.info("pmd instanceurl: " + pmdInstance.getUri());

                if (!checkstyleStatus.containsBlockedCheckstyleInstance(checkstyleIinstance.getUri())
                        && !pmdStatus.containsBlockedPmdInstance(pmdInstance.getUri())) {

                    isExecute = executeWithNewInstances(status, checkstyleStatus, pmdStatus, task, checkstyleIinstance, pmdInstance);
                }
            } else {

                isExecute = executeFromAvailableInstances(status, checkstyleStatus, pmdStatus, task);
            }

            // update status of task execution
            if (isExecute) {
                status.addToStartTimeList(System.currentTimeMillis());
                status.increaseRunningTasks();
                status.increaseNextTask();
                status.decreaseNumberOfOpenTasks();

                LOG.info("running tasks: " + status.getNumberOfRunningTasks());
                LOG.info("open tasks: " + status.getNumberOfOpenTasks());
                LOG.info("index of next task: " + status.getIndexOfNextTask());
            }
        }
    }

    private boolean executeWithNewInstances(Status status, CheckstyleStatus checkstyleStatus, PmdStatus pmdStatus, JSONObject task, ServiceInstance checkstyleIinstance, ServiceInstance pmdInstance) {
        boolean isExecute;


        ValidationData validationData = new ValidationData();
        try {
            validationData.setRepository(task.getString("repository"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Future<String> checkstyleFuture = validateRepositoryService.validateRepository(validationData.toString(),
                checkstyleIinstance.getUri());
        checkstyleStatus.getBlockedCheckstyleInstancesList().put(checkstyleIinstance.getUri(), task.toString());
        checkstyleStatus.getCheckstyleTaskList().add(checkstyleFuture);

        Future<String> pmdFuture = validateRepositoryService.validateRepository(validationData.toString(),
                checkstyleIinstance.getUri());
        pmdStatus.getBlockedPmdInstancesList().put(pmdInstance.getUri(), task.toString());
        pmdStatus.getPmdTaskList().add(pmdFuture);

        isExecute = true;
        return isExecute;
    }

    private boolean executeFromAvailableInstances(Status status, CheckstyleStatus checkstyleStatus, PmdStatus pmdStatus, JSONObject task) {
        boolean isExecute;

        ValidationData validationData = new ValidationData();
        try {
            validationData.setRepository(task.getString("repository"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Future<String> checkstyleFuture = validateRepositoryService.validateRepository(validationData.toString(),
                checkstyleStatus.getAvailableCheckstyleInstancesList().get(0));
        checkstyleStatus.getBlockedCheckstyleInstancesList().put(checkstyleStatus.getAvailableCheckstyleInstancesList().remove(0), task.toString());
        checkstyleStatus.getCheckstyleTaskList().add(checkstyleFuture);


        Future<String> pmdFuture = validateRepositoryService.validateRepository(validationData.toString(),
                pmdStatus.getAvailablePmdInstancesList().get(0));
        pmdStatus.getBlockedPmdInstancesList().put(pmdStatus.getAvailablePmdInstancesList().remove(0), task.toString());
        pmdStatus.getPmdTaskList().add(pmdFuture);
        // remove first element of available Instance list


        Iterator<URI> itCheckstyle = checkstyleStatus.getAvailableCheckstyleInstancesList().iterator();
        Iterator<URI> itPmd = pmdStatus.getAvailablePmdInstancesList().iterator();
        if (itCheckstyle.hasNext()) {
            itCheckstyle.next();
            itPmd.next();
            itCheckstyle.remove();
            itPmd.remove();
        }

        isExecute = true;
        return isExecute;
    }

    private void checkRunningTasks(Status status, CheckstyleStatus checkstyleStatus, PmdStatus pmdStatus)
            throws JSONException, InterruptedException, ExecutionException, InstantiationException {
        ArrayList<Integer> toDelete = new ArrayList<>();

        for (int i = 0; i < status.getNumberOfRunningTasks(); i++) {
            LOG.info("check running task nr: " + i);
            if (checkstyleStatus.getCheckstyleTaskList().get(i) != null
                    && checkstyleStatus.getCheckstyleTaskList().get(i).isDone()
                    && pmdStatus.getPmdTaskList().get(i) != null
                    && pmdStatus.getPmdTaskList().get(i).isDone()) {
                LOG.info("checkstyle and pmd tasks are done");

                JSONObject checkstyleObj = new JSONObject(checkstyleStatus.getCheckstyleTaskList().get(i).get());
                JSONObject pmdObj = new JSONObject(pmdStatus.getPmdTaskList().get(i).get());
                JSONObject result = new JSONObject();
                result.put("checkstyle", checkstyleObj);
                result.put("pmd", pmdObj);
                result.put("userId", status.getRepoUserInformationMap().get(checkstyleObj.getString(REPOSITORY)));
                result.put("duration", (System.currentTimeMillis() - status.getStartTimeList().get(i)));
                result.put(REPOSITORY, checkstyleObj.getString(REPOSITORY));
                ValidationData data = new ValidationData();
                data.setRepository(checkstyleObj.getString(REPOSITORY));

                URI availableCheckstyleUri = helper.getUriWithValue(checkstyleStatus.getBlockedCheckstyleInstancesList(), data.toString());
                URI availablePmdUri = helper.getUriWithValue(pmdStatus.getBlockedPmdInstancesList(), data.toString());

                checkstyleStatus.getAvailableCheckstyleInstancesList().add(availableCheckstyleUri);
                pmdStatus.getAvailablePmdInstancesList().add(availablePmdUri);

                // remove entry from blocked instance list
                helper.removeEntryFromBlockedInstanceListe(checkstyleStatus.getBlockedCheckstyleInstancesList(), availableCheckstyleUri);
                helper.removeEntryFromBlockedInstanceListe(pmdStatus.getBlockedPmdInstancesList(), availablePmdUri);

                status.getResultList().add(result);

                toDelete.add(i);
                databaseService.saveCheckstleResult(checkstyleObj.toString());
                databaseService.savePmdResult(pmdObj.toString());
            }
        }

        if (!toDelete.isEmpty()) {
            helper.removeTaskFromLists(status, checkstyleStatus, pmdStatus, toDelete);
        }
    }



    private void startInstances(int numberOfInstances, AmazonEC2 ec2) throws NoSuchFieldException {
        if (util.getNumberOfActiveCheckstyleInstances(ec2) < numberOfInstances) {
            util.runNewCheckstyleInstance(ec2, numberOfInstances, numberOfInstances);
        }
        if (util.getNumberOfActivePmdInstances(ec2) < numberOfInstances) {
            util.runNewPmdInstance(ec2, numberOfInstances, numberOfInstances);
        }
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
