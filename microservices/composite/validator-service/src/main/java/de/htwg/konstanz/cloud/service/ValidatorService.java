package de.htwg.konstanz.cloud.service;

import com.amazonaws.util.json.JSONArray;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.Future;

@RestController
@EnableAsync
public class ValidatorService {

    private static final Logger LOG = LoggerFactory.getLogger(ValidatorService.class);
    public static final String APPLICATION_JSON = "application/json";
    public static final String CHECKSTYLE = "checkstyle";
    public static final String PMD = "pmd";

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

    // config property from application yml
    @Value("${spring.application.name}")
    private String serviceName;

    /**
     * Default route to check if service allive
     *
     * @return timetamp and service id
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET, produces = APPLICATION_JSON)
    public String info() {
        return "{\"timestamp\":\"" + new Date() + "\",\"serviceId\":\"" + serviceName + "\"}";
    }


    /**
     * Validate all groups of a specific course
     *
     * @param courseId course id from moodle
     * @return List of checkstyle and pmd result for all groups
     */
    @RequestMapping(value = "/courses/{courseId}/validate", method = RequestMethod.POST)
    public ResponseEntity<String> validateCourse(@PathVariable String courseId) {

        try {
            // call database to get all courses
            String course = databaseService.getCourse(courseId);
            // Convert to json
            JSONObject jsonObj = new JSONObject(course);
            JSONArray groups = jsonObj.getJSONArray("groups");


            ArrayList<JSONObject> result = new ArrayList<>();
            // check if service runs on aws
            if (environment.getActiveProfiles()[0].equals("aws")) {
                // run custom scheduler
                result = customScheduler.runValidationSchedulerOnAws(groups);
            }

            // return json result
            return util.createResponse(result.toString(), HttpStatus.OK);

        } catch (InstantiationException e) {
            // Service unavailable
            LOG.error(Arrays.toString(e.getStackTrace()));
            return util.createErrorResponse(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        } catch (Exception e) {
            // Internal server error
            LOG.error(Arrays.toString(e.getStackTrace()));
            return util.createErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Route to validate a specific group
     *
     * @param userId alias group id from moodle
     * @return checkstyle and pmd result
     */
    @ApiOperation(value = "validate", nickname = "validate")
    @RequestMapping(value = "/groups/{userId}/validate", method = RequestMethod.POST)
    @ApiResponse(code = 200, message = "Success", response = String.class)
    public ResponseEntity<String> validateGroup(@PathVariable String userId) {
        try {
            // call database to get course
            String group = databaseService.getGroup(userId);
            // build json with result
            JSONObject jsonObject = new JSONObject(group);
            LOG.info("Validate: " + jsonObject.toString());
            // get instances
            ServiceInstance checkstyleInstance = loadBalancer.choose(CHECKSTYLE);
            ServiceInstance pmdInstance = loadBalancer.choose(PMD);

            // start time measurement
            long startTime = System.currentTimeMillis();
            ValidationData repositoryData = new ValidationData();
            repositoryData.setRepository(jsonObject.getString("repository"));

            // execute checkstyle and pmd validation and save future
            Future<String> checkstyleRepo = validateRepositoryService.validateRepository(repositoryData.toString(), checkstyleInstance.getUri());
            Future<String> pmdRepo = validateRepositoryService.validateRepository(repositoryData.toString(), pmdInstance.getUri());

            // check if services available
            if (checkstyleRepo == null || pmdRepo == null) {
                return util.createErrorResponse("Validation services not found!", HttpStatus.SERVICE_UNAVAILABLE);
            }

            boolean run = true;
            // Wait until they are done
            while (run) {
                if (checkstyleRepo.isDone() && pmdRepo.isDone()) {
                    run = false;
                }
                //10-millisecond pause between each check
                Thread.sleep(500);
            }

            // build result json
            JSONObject checkstyleResult = new JSONObject(checkstyleRepo.get());
            checkstyleResult.put("userId", userId);
            checkstyleResult.put("duration", (System.currentTimeMillis() - startTime));

            JSONObject pmdResult = new JSONObject(pmdRepo.get());
            pmdResult.put("userId", userId);
            pmdResult.put("duration", (System.currentTimeMillis() - startTime));

            // save results into database
            databaseService.savePmdResult(pmdResult.toString());
            databaseService.saveCheckstleResult(checkstyleResult.toString());

            jsonObject.put(CHECKSTYLE, checkstyleResult);
            jsonObject.put(PMD, pmdResult);

            return util.createResponse(jsonObject.toString(), HttpStatus.OK);
        } catch (InstantiationException e) {
            LOG.error(e.getMessage());
            return util.createErrorResponse(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return util.createErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/courses/{courseId}/validate/duplication", method = RequestMethod.POST)
    public ResponseEntity<String> validateDuplication(@PathVariable String courseId) {
        try {
            // get course with all groups from database
            String course = databaseService.getCourse(courseId);

            // get pmd instance from eureka
            ServiceInstance pmdInstance = loadBalancer.choose(PMD);
            // get json object with array of repositores to call code dublication service
            JSONObject repositories = new JSONObject().put("repositories", util.getRepositoriesFromJsonObject(course));

            // Call validation asynchronous
            Future<String> pmdRepo = validateRepositoryService.validateCodeDublication(repositories.toString(), pmdInstance.getUri());

            // Wait until they are done
            while (!pmdRepo.isDone()) {
                //10-millisecond pause between each check
                Thread.sleep(500);
            }

            // build result
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("duplication", pmdRepo.get());

            return util.createResponse(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return util.createErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(value = "/validate", method = RequestMethod.POST, consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    public ResponseEntity<String> validateCodeDublication(@RequestBody ValidationData data) {
        try {
            // get checkstyle and pmd instance from eureka
            ServiceInstance checkstyleInstance = loadBalancer.choose(CHECKSTYLE);
            ServiceInstance pmdInstance = loadBalancer.choose(PMD);

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

            // build result
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(CHECKSTYLE, checkstyleRepo.get());
            jsonObject.put(PMD, pmdRepo.get());

            return util.createResponse(jsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return util.createErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * return last checkstyle result of specific user
     *
     * @param userId alias groupId
     * @return last result for user with userId
     */
    @RequestMapping(value = "/groups/{userId}/checkstyle/last-result", method = RequestMethod.GET, produces = APPLICATION_JSON)
    public ResponseEntity<String> getLastCheckstyleResult(@PathVariable String userId) {
        try {
            // call database to get last result
            return util.createResponse(databaseService.getLastCheckstyleResult(userId), HttpStatus.OK);
        } catch (InstantiationException e) {
            return util.createErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * return last pmd result of specific user
     *
     * @param userId alias groupid
     * @return last result for user with userId
     */
    @RequestMapping(value = "/groups/{userId}/pmd/last-result", method = RequestMethod.GET, produces = APPLICATION_JSON)
    public ResponseEntity<String> getLastPmdResult(@PathVariable String userId) {
        try {
            // call database to get last result
            return util.createResponse(databaseService.getLastPmdResult(userId), HttpStatus.OK);
        } catch (InstantiationException e) {
            return util.createErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}