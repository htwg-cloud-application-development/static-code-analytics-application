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

    // Default duration time of start proccess of new instance
    @Value("${app.aws.init.instance.duration:90000}")
    private int initInstancetDuration;

    // Used if more than 4 instances should be starts
    @Value("${app.aws.init.instance.max:5}")
    private int maxNumberOfInstancesGreaterFour;

    @Autowired
    private DatabaseService databaseService;

    @Autowired
    private SchedulerHelper helper;

    /**
     * Method to execute the validation of a course.
     *
     * @param groups A list of repositories to validate with checkstyle and pmd.
     * @return validation result of all tasks.
     */
    ArrayList<JSONObject> runValidationSchedulerOnAws(JSONArray groups) throws JSONException, InstantiationException,
            ExecutionException, InterruptedException, NoSuchFieldException {

        // init status object
        Status status = new Status();
        status.setNumberOfOpenTasks(groups.length());
        status.setNumberOfTasks(groups.length());

        for (int i = 0; i < groups.length(); i++) {
            JSONObject jsonObject = (JSONObject) groups.get(i);
            int executionTime = jsonObject.optInt("executiontime", 100000);
            if (executionTime == 0) {
                executionTime = 100000;
            }
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
            status.putIntoRepoUserInformationMap(jsonObject.getString(REPOSITORY), jsonObject.getString("id"));
        }

        return startScheduling(status);
    }

    /**
     * Private Method to start the validation of all repositories.
     *
     * @param status Status object which holds all needet information for scheduling.
     * @return json result
     */
    private ArrayList<JSONObject> startScheduling(Status status) throws NoSuchFieldException,
            JSONException, InterruptedException, ExecutionException, InstantiationException {

        AmazonEC2 ec2 = new AmazonEC2Client(new EnvironmentVariableCredentialsProvider());
        ec2.setRegion(com.amazonaws.regions.Region.getRegion(Regions.EU_CENTRAL_1));

        int numberOfInstancesToStart = magicCalculateForNumberOfInstances(status.getNumberOfTasks(),
                status.getFullExecutionTime());
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
            checkstyleStatus.setAvailableCheckstyleInstances(util.getNumberOfActiveCheckstyleInstances(ec2)
                    - status.getNumberOfRunningTasks());
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

    /**
     * Check if new services active and start execution.
     *
     * @param status           Holds needet Information of running and blocked tasks.
     * @param checkstyleStatus Contains information about checkstyle instances.
     * @param pmdStatus        Contains information about pmd instances
     */
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

                    executeWithNewInstances(status, checkstyleStatus, pmdStatus, task, checkstyleIinstance,
                            pmdInstance);
                    isExecute = true;
                }
            } else {
                executeFromAvailableInstances(checkstyleStatus, pmdStatus, task);
                isExecute = true;
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

    /**
     * Executes a task within a new available instance.
     *
     * @param status              Holds information about scheduling.
     * @param checkstyleStatus    Hold information about checkstyle instances.
     * @param pmdStatus           Hold information about pmd instances.
     * @param task                Task (repository) to execute.
     * @param checkstyleIinstance new available checkstyle instances.
     * @param pmdInstance         new available pmd instances.
     */
    private void executeWithNewInstances(Status status, CheckstyleStatus checkstyleStatus, PmdStatus pmdStatus,
                                         JSONObject task, ServiceInstance checkstyleIinstance, ServiceInstance pmdInstance) {

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
                pmdInstance.getUri());
        pmdStatus.getBlockedPmdInstancesList().put(pmdInstance.getUri(), task.toString());
        pmdStatus.getPmdTaskList().add(pmdFuture);
    }


    /**
     * Executes a task from available instance.
     *
     * @param checkstyleStatus Hold information about checkstyle instances.
     * @param pmdStatus        Hold information about pmd instances.
     * @param task             Task (repository) to execute.
     */
    private void executeFromAvailableInstances(CheckstyleStatus checkstyleStatus, PmdStatus pmdStatus,
                                               JSONObject task) {
        ValidationData validationData = new ValidationData();
        try {
            validationData.setRepository(task.getString("repository"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Future<String> checkstyleFuture = validateRepositoryService.validateRepository(validationData.toString(),
                checkstyleStatus.getAvailableCheckstyleInstancesList().get(0));
        checkstyleStatus.getBlockedCheckstyleInstancesList()
                .put(checkstyleStatus.getAvailableCheckstyleInstancesList().remove(0), task.toString());
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
    }

    /**
     * Check all running tasks if its finished, update status variables and save results into database.
     *
     * @param status           Task (repository) to execute.
     * @param checkstyleStatus Hold information about checkstyle instances.
     * @param pmdStatus        Hold information about pmd instances.
     */
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
                checkstyleObj.put("userId", status.getRepoUserInformationMap().get(checkstyleObj.getString(REPOSITORY)));
                JSONObject pmdObj = new JSONObject(pmdStatus.getPmdTaskList().get(i).get());
                pmdObj.put("userId", status.getRepoUserInformationMap().get(pmdObj.getString(REPOSITORY)));
                JSONObject result = new JSONObject();
                result.put("checkstyle", checkstyleObj);
                result.put("pmd", pmdObj);
                result.put("id", status.getRepoUserInformationMap().get(checkstyleObj.getString(REPOSITORY)));
                long duration = (System.currentTimeMillis() - status.getStartTimeList().get(i));
                result.put("duration", duration);
                result.put(REPOSITORY, checkstyleObj.getString(REPOSITORY));
                ValidationData data = new ValidationData();
                data.setRepository(checkstyleObj.getString(REPOSITORY));

                URI availableCheckstyleUri = helper.getUriWithValue(checkstyleStatus.getBlockedCheckstyleInstancesList(),
                        data.toString());
                URI availablePmdUri = helper.getUriWithValue(pmdStatus.getBlockedPmdInstancesList(), data.toString());

                checkstyleStatus.getAvailableCheckstyleInstancesList().add(availableCheckstyleUri);
                pmdStatus.getAvailablePmdInstancesList().add(availablePmdUri);

                // remove entry from blocked instance list
                helper.removeEntryFromBlockedInstanceListe(checkstyleStatus.getBlockedCheckstyleInstancesList(),
                        availableCheckstyleUri);
                helper.removeEntryFromBlockedInstanceListe(pmdStatus.getBlockedPmdInstancesList(), availablePmdUri);

                status.getResultList().add(result);
                LOG.info("ToSave:" + checkstyleObj.toString());
                toDelete.add(i);
                databaseService.saveCheckstleResult(checkstyleObj.toString());
                databaseService.savePmdResult(pmdObj.toString());
                databaseService.updateExecutionTimeOfGroup(duration, status.getRepoUserInformationMap()
                        .get(checkstyleObj.getString(REPOSITORY)));

                LOG.info("Task finished - numberOfRunnigntasks before: " + status.getNumberOfRunningTasks());
                status.decreaseNumberOfRunningTasks();
                LOG.info("Task finished - numberOfRunnigntasks after: " + status.getNumberOfRunningTasks());

            }
        }

        if (!toDelete.isEmpty()) {
            helper.removeTaskFromLists(status, checkstyleStatus, pmdStatus, toDelete);
        }
    }

    /**
     * Start checkstyle and pmd instance on aws.
     *
     * @param numberOfInstances number of instances to start of each type.
     * @param ec2               EC2 object to execute aws functions.
     */
    private void startInstances(int numberOfInstances, AmazonEC2 ec2) throws NoSuchFieldException {
        if (util.getNumberOfActiveCheckstyleInstances(ec2) < numberOfInstances) {
            util.runNewCheckstyleInstance(ec2, numberOfInstances, numberOfInstances);
        }
        if (util.getNumberOfActivePmdInstances(ec2) < numberOfInstances) {
            util.runNewPmdInstance(ec2, numberOfInstances, numberOfInstances);
        }
    }


    /**
     * Magic calculation
     *
     * @param numberOfExecutions number of tasks to execute.
     * @param fullExecutionTime  execution time of all repositories.
     * @return number of instances to start.
     */
    private int magicCalculateForNumberOfInstances(int numberOfExecutions, int fullExecutionTime) {
        int numberOfInstances;

        LOG.info("initInstanceDuration: " + initInstancetDuration);

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
        LOG.info("Instance calculation result: " + numberOfInstances);
        return numberOfInstances;
    }

}
