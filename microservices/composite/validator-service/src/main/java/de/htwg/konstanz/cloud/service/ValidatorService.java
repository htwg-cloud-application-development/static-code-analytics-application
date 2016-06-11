package de.htwg.konstanz.cloud.service;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.RunInstancesResult;
import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import de.htwg.konstanz.cloud.model.ValidationData;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
@EnableAsync
public class ValidatorService {

    private static final Logger LOG = LoggerFactory.getLogger(ValidatorService.class);

    @Autowired
    private LoadBalancerClient loadBalancer;

    @Autowired
    private Environment environment;

    @Autowired
    ValidateRepositoryService validateRepositoryService;

    @Autowired
    DatabaseService databaseService;

    @Autowired
    Util util;

    @Value("${spring.application.name}")
    private String serviceName;

    @Value("${app.aws.init.instance.duration:90000}")
    private int initInstancetDuration;

    @Value("${app.aws.init.instance.max:5}")
    private int maxNumberOfInstancesGreaterFour;


    @RequestMapping(value = "/info", method = RequestMethod.GET, produces = "application/json")
    public String info() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nActiveProfile: ").append(environment.getActiveProfiles()[0]);
        if (environment.getActiveProfiles()[0].equals("aws")) {
            try {
                AmazonEC2 ec2 = new AmazonEC2Client(new EnvironmentVariableCredentialsProvider());
                ec2.setRegion(com.amazonaws.regions.Region.getRegion(Regions.EU_CENTRAL_1));

                // - read number of available services (Checkstyle, etc)
                sb.append("\nNumberOfActiveCheckstyleInstances").append(util.getNumberOfActiveCheckstyleInstances(ec2));

                RunInstancesResult result = util.runNewCheckstyleInstance(ec2, 1, 1);
                for (Instance instance : result.getReservation().getInstances()) {
                    sb.append(instance.getInstanceId());
                }

            } catch (NoSuchFieldException e) {
                return e.getMessage();
            }
        }

        return "{\"timestamp\":\"" + new Date() + "\",\"serviceId\":\"" + sb.toString() + "\"}";
    }


    @RequestMapping(value = "/courses/{courseId}/validate", method = RequestMethod.POST)
    public ResponseEntity<String> validateCourse(@PathVariable String courseId) {

        try {
            String course = databaseService.getCourse(courseId);
            JSONObject jsonObj = new JSONObject(course);
            JSONArray groups = jsonObj.getJSONArray("groups");


            ArrayList<JSONObject> result = new ArrayList<>();
            // check if service runs on aws
            if (environment.getActiveProfiles()[0].equals("aws")) {
                result = runValidationSchedulerOnAws(groups);
            }


            return util.createResponse(result.toString(), HttpStatus.OK);

        } catch (InstantiationException e) {
            LOG.error(Arrays.toString(e.getStackTrace()));
            return util.createErrorResponse(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        } catch (Exception e) {
            LOG.error(Arrays.toString(e.getStackTrace()));
            return util.createErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ArrayList<JSONObject> runValidationSchedulerOnAws(JSONArray groups) throws JSONException, InstantiationException, ExecutionException, InterruptedException {

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
            ArrayList<JSONObject> result = new ArrayList<>();
            int availableInstances = util.getNumberOfActiveCheckstyleInstances(ec2);
            int runningTasks = 0;
            int openTasks = numberOfExecutions;
            int index = 0;

            // start execution measurement
            long startTime = System.currentTimeMillis();


            while (openTasks > 0) {
                if (availableInstances > 0) {
                    JSONObject task = getTaskWithLongestDuration(pipeline, index);
                    index++;
                    if (task != null) {
                        Future<String> future = validateRepositoryService.validateRepository(task.toString());
                        // TODO note which service executes task for specific repository
                        taskList.add(future);
                        runningTasks++;
                    }
                    openTasks--;
                }


                for(int i = 0; i < runningTasks; i++) {
                    if(taskList.get(i).isDone()) {
                        JSONObject obj = new JSONObject(taskList.get(i).get());
                        obj.put("groupId", groups.getJSONObject(i).getString("groupId"));
                        obj.put("duration", (System.currentTimeMillis() - startTime));
                        result.add(obj);
                        taskList.remove(i);
                        runningTasks--;
                        databaseService.saveResult(obj.toString());
                    }
                }
            }


        } catch (NoSuchFieldException e) {
            LOG.info(e.getMessage());
        }

/*
        while (numberOfRepos > 0) {
            if (taskList.get(i).isDone()) {
                JSONObject obj = new JSONObject(taskList.get(i).get());
                obj.put("groupId", groups.getJSONObject(i).getString("groupId"));
                obj.put("duration", (System.currentTimeMillis() - startTime));
                result.add(obj);

                numberOfRepos--;
                // TODO return error
                databaseService.saveResult(obj.toString());
            }
            i++;
            if (i >= numberOfRepos) {
                i = 0;
            }
            Thread.sleep(100);
        }

        return result;*/
        return null;
    }

    private JSONObject getTaskWithLongestDuration(Map<Integer, List<JSONObject>> pipeline, int index) {
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


    private void startInstances(int numberOfInstances, AmazonEC2 ec2) throws NoSuchFieldException {
        if (util.getNumberOfActiveCheckstyleInstances(ec2) < numberOfInstances) {
            util.runNewCheckstyleInstance(ec2, numberOfInstances, numberOfInstances);
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

    @ApiOperation(value = "validate", nickname = "validate")
    @RequestMapping(value = "/groups/{groupId}/validate", method = RequestMethod.POST)
    @ApiResponse(code = 200, message = "Success", response = String.class)
    public ResponseEntity<String> validateGroup(@PathVariable String groupId) {
        try {
            String group = databaseService.getGroup(groupId);
            // start execution measurement
            long startTime = System.currentTimeMillis();
            Future<String> repo = validateRepositoryService.validateRepository(group);

            // Wait until they are done
            while (!(repo.isDone())) {
                //10-millisecond pause between each check
                Thread.sleep(10);
            }

            JSONObject result = new JSONObject(repo.get());
            result.put("groupId", groupId);
            result.put("duration", (System.currentTimeMillis() - startTime));

            Future<String> save = databaseService.saveResult(result.toString());
            return util.createResponse(result.toString(), HttpStatus.OK);
        } catch (InstantiationException e) {
            LOG.error(e.getMessage());
            return util.createErrorResponse(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return util.createErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/validate", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> validateGroupVerify(@RequestBody ValidationData data) {
        try {
            // Call validation asynchronous
            Future<String> repo = validateRepositoryService.validateRepository(data.toString());

            // Wait until they are done
            while (!(repo.isDone())) {
                //10-millisecond pause between each check
                Thread.sleep(10);
            }

            return util.createResponse(repo.get(), HttpStatus.OK);
        } catch (InstantiationException e) {
            LOG.error(e.getMessage());
            return util.createErrorResponse(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
            return util.createErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/scheduler", method = RequestMethod.GET, produces = "application/json")
    public String schedulerTest() {
        try {

            JSONObject gitRepo1 = new JSONObject();
            gitRepo1.put("executiontime", 60001);

            JSONObject gitRepo2 = new JSONObject();
            gitRepo2.put("executiontime", 20000);

            JSONObject svnRepo1 = new JSONObject();
            svnRepo1.put("executiontime", 60001);

            JSONArray groups = new JSONArray();
            groups.put(gitRepo1);
            groups.put(gitRepo2);
            groups.put(svnRepo1);

            runValidationSchedulerOnAws(groups);
        } catch (JSONException e) {
            e.printStackTrace();
            return e.getMessage();
        } catch (InstantiationException e) {
            e.printStackTrace();
            return e.getMessage();
        } catch (ExecutionException e) {
            e.printStackTrace();
            return e.getMessage();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return "{\"timestamp\":\"" + new Date() + "\",\"serviceId\":\"" + "\"}";
    }


}
