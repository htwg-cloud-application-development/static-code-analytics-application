package de.htwg.konstanz.cloud.service;

import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import com.netflix.discovery.converters.Auto;
import de.htwg.konstanz.cloud.model.ValidationData;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.codehaus.jackson.map.HandlerInstantiator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.concurrent.Future;

@RestController
@EnableAsync
public class ValidatorService {

    private static final Logger LOG = LoggerFactory.getLogger(ValidatorService.class);

    @Autowired
    private LoadBalancerClient loadBalancer;

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
        return "{\"timestamp\":\"" + new Date() + "\",\"serviceId\":\"" + serviceName + "\"}";
    }


    @RequestMapping(value = "/courses/{courseId}/validate", method = RequestMethod.POST)
    public String validateCourse(@PathVariable String courseId) {

        // TODO scheduling!!

        // [ ] get course data from database
        // [ ] for each repo:
        // [ ] - call validateRepositoryService async
        // [ ] - save result in database
        // [ ] build result JSON for course (all repos)
        // [ ] return result
        return null;
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
            return util.createErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
