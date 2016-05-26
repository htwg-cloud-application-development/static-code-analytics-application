package de.htwg.konstanz.cloud.service;


import com.amazonaws.util.json.JSONObject;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.concurrent.Future;


@RestController
public class GovernanceService {


    @RequestMapping("/info")
    public String info() {
        return "Governance-Service";
    }


    @Autowired
    DatabaseService databaseService;


    @RequestMapping(value = "/courses", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> getCouses() {
        try {
            return createResponse(databaseService.getAllCourses(), HttpStatus.OK);
        } catch (InstantiationException e) {
            return createErrorResponse(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @RequestMapping(value = "/groups", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> getGroups() {
        try {
            return createResponse(databaseService.getAllGroups(), HttpStatus.OK);
        } catch (InstantiationException e) {
            return createErrorResponse(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @RequestMapping(value = "/groups/{groupId}", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> getGroup(@PathVariable String groupId) {
        try {
            return createResponse(databaseService.getGroupWithId(groupId), HttpStatus.OK);
        } catch (InstantiationException e) {
            return createErrorResponse(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @RequestMapping(value = "/courses/{courseId}", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> getCourse(@PathVariable String courseId) {
        try {
            return createResponse(databaseService.getGroupWithId(courseId), HttpStatus.OK);
        } catch (InstantiationException e) {
            return createErrorResponse(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @HystrixCommand(fallbackMethod = "getDefaultMoodleStuff")
    private Object getMoodleStuff() {
        return null;
    }

    private Object getDefaultMoodleStuff() {
        return null;
    }


    <T> ResponseEntity<T> createResponse(T body, HttpStatus httpStatus) {
        return new ResponseEntity<>(body, httpStatus);
    }

    ResponseEntity<String> createErrorResponse(String errorMessage, HttpStatus status) {
        HashMap<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", errorMessage);
        JSONObject errorResponseObject = new JSONObject(errorResponse);
        return createResponse(errorResponseObject.toString(), status);
    }
}
