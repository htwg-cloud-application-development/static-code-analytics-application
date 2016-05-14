package de.htwg.konstanz.cloud.service;

import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import de.htwg.konstanz.cloud.model.ValidationData;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
@EnableAsync
public class ValidatorService {

    private static final Logger LOG = LoggerFactory.getLogger(ValidatorService.class);

    @Autowired
    private LoadBalancerClient loadBalancer;

    @Autowired
    ValidateRepositoryService validateRepositoryService;

    @Value("${spring.application.name}")
    private String serviceName;

    private RestTemplate restTemplate;

    @PostConstruct
    private void init() {
        this.restTemplate = new RestTemplate();
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET, produces = "application/json")
    public String info() {
        return "{\"timestamp\":\"" + new Date() + "\",\"serviceId\":\"" + serviceName + "\"}";
    }


    @RequestMapping(value = "/courses/{courseId}/validate", method = RequestMethod.POST)
    public String validateCourse(@PathVariable String courseId) {
        return null;
    }

    @ApiOperation(value = "validate", nickname = "validate")
    @RequestMapping(value = "/courses/{courseId}/groups/{groupId}/validate", method = RequestMethod.POST)
    @ApiResponse(code = 200, message = "Success", response = String.class)
    public String validateGroup(@PathVariable String courseId, @PathVariable String groupId) {
        return null;
    }

    // TODO call service asynchronously - hystrix
    @RequestMapping(value = "/validate", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> validateGroup(@RequestBody ValidationData data) {
        String VALIDATE_ROUTE = "/validate";
        JSONObject json = new JSONObject();
        try {
            // build json object for request object
            json.put("repositoryUrl", data.getRepositoryUrl());

            Future<String> repo = validateRepositoryService.validateRepository(json.toString());

            // Wait until they are done
            while (!(repo.isDone())) {
                Thread.sleep(10); //10-millisecond pause between each check
            }
            return createResponse(repo.get(), HttpStatus.OK);
        } catch (JSONException e) {
            return createErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (InterruptedException e) {
            return createErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ExecutionException e) {
            return createErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            System.out.println(e);
        }

        return createErrorResponse("no services available", HttpStatus.SERVICE_UNAVAILABLE);

    }

    protected <T> ResponseEntity<T> createResponse(T body, HttpStatus httpStatus) {
        return new ResponseEntity<>(body, httpStatus);
    }

    private ResponseEntity<String> createErrorResponse(String errorMessage, HttpStatus status) {
        LOG.error(errorMessage);
        HashMap<String, String> errorResponse = new HashMap<String, String>();
        errorResponse.put("error", errorMessage);
        JSONObject errorResponseObject = new JSONObject(errorResponse);
        return createResponse(errorResponseObject.toString(), status);
    }
}
