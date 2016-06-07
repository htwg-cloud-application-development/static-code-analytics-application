package de.htwg.konstanz.cloud.service;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.util.json.JSONArray;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
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

    @RequestMapping(value = "/info", method = RequestMethod.GET, produces = "application/json")
    public String info() {
        StringBuilder sb = new StringBuilder();
        if (environment.getActiveProfiles()[0].equals("aws")) {
            try {
                AmazonEC2 ec2 = new AmazonEC2Client(new EnvironmentVariableCredentialsProvider());
                ec2.setRegion(com.amazonaws.regions.Region.getRegion(Regions.EU_CENTRAL_1));

                // - read number of available services (Checkstyle, etc)
                sb.append("NumberOfActiveCheckstyleInstances").append(util.getNumberOfActiveCheckstyleInstances(ec2));

                util.runNewCheckstyleInstance(ec2, 1, 1);
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
            JSONArray array = jsonObj.getJSONArray("groups");

            int threadNum = array.length();
            List<Future<String>> taskList = new ArrayList<>();


            // check if service runs on aws
            if (environment.getActiveProfiles()[0].equals("aws")) {
                // TODO:
                // - read executiontime of each repository
                // - sort repositories after execution time


                // - calculation to get number of new instances (if necessary)
                // - start only 1 execution at same time
                // - note which service executes task for specific repository
                // - loop:
                //      - check if service free
                //      - execute shortes repo first (greedy)
            }


            // start execution measurement
            long startTime = System.currentTimeMillis();

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                Future<String> repo = validateRepositoryService.validateRepository(obj.toString());
                taskList.add(repo);
            }

            ArrayList<JSONObject> result = new ArrayList<>();
            int numberOfRepos = array.length();
            int i = 0;
            while (numberOfRepos > 0) {
                if (taskList.get(i).isDone()) {
                    JSONObject obj = new JSONObject(taskList.get(i).get());
                    obj.put("groupId", array.getJSONObject(i).getString("groupId"));
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
    public ResponseEntity<String> validateGroup(@RequestBody ValidationData data) {
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
}
