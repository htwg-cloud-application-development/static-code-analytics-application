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
import org.springframework.cloud.client.ServiceInstance;
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

    @Autowired
    CustomScheduler customScheduler;

    @Value("${spring.application.name}")
    private String serviceName;


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
                result = customScheduler.runValidationSchedulerOnAws(groups);
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


    @ApiOperation(value = "validate", nickname = "validate")
    @RequestMapping(value = "/groups/{userId}/validate", method = RequestMethod.POST)
    @ApiResponse(code = 200, message = "Success", response = String.class)
    public ResponseEntity<String> validateGroup(@PathVariable String userId) {
        try {
            String group = databaseService.getGroup(userId);
            JSONObject jsonObject = new JSONObject(group);
            // start execution measurement
            ServiceInstance checkstyleInstance = loadBalancer.choose("checkstyle");
            ServiceInstance pmdInstance = loadBalancer.choose("pmd");

            long startTime = System.currentTimeMillis();
            Future<String> checkstyleRepo = validateRepositoryService.validateRepository(jsonObject.toString(), checkstyleInstance.getUri());
            Future<String> pmdRepo = validateRepositoryService.validateRepository(jsonObject.toString(), pmdInstance.getUri());

            boolean run = true;
            // Wait until they are done
            while (run) {
                if (checkstyleRepo.isDone() && pmdRepo.isDone()) {
                    run = false;
                }
                //10-millisecond pause between each check
                Thread.sleep(500);
            }

            JSONObject checkstyleResult = new JSONObject(checkstyleRepo.get());
            checkstyleResult.put("userId", userId);
            checkstyleResult.put("duration", (System.currentTimeMillis() - startTime));

            JSONObject pmdResult = new JSONObject(pmdRepo.get());
            pmdResult.put("userId", userId);
            pmdResult.put("duration", (System.currentTimeMillis() - startTime));

            Future<String> savePmd = databaseService.saveResult(pmdResult.toString());
            Future<String> saveCheckstyle = databaseService.saveResult(checkstyleResult.toString());

            jsonObject.put("checkstyle", checkstyleResult);
            jsonObject.put("pmd", pmdResult);

            return util.createResponse(jsonObject.toString(), HttpStatus.OK);
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
            ServiceInstance checkstyleInstance = loadBalancer.choose("checkstyle");
            ServiceInstance pmdInstance = loadBalancer.choose("pmd");

            // Call validation asynchronous
            Future<String> checkstyleRepo = validateRepositoryService.validateRepository(data.toString(), checkstyleInstance.getUri());
            Future<String> pmdRepo = validateRepositoryService.validateRepository(data.toString(), pmdInstance.getUri());

            boolean run = true;
            // Wait until they are done
            while (run) {
                if (checkstyleRepo.isDone() && pmdRepo.isDone()) {
                    run = false;
                }
                //10-millisecond pause between each check
                Thread.sleep(500);
            }

            // TODO validate strucutre of result with team
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("structure", "TODO");
            jsonObject.put("checkstyle", checkstyleRepo.get());
            jsonObject.put("pmd", pmdRepo.get());

            return util.createResponse(jsonObject.toString(), HttpStatus.OK);
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

            customScheduler.runValidationSchedulerOnAws(groups);
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
