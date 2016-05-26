package de.htwg.konstanz.cloud.service;


import com.amazonaws.util.json.JSONObject;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
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
    public ResponseEntity<String> validateGroup() {
        try {
            return createResponse(databaseService.getAllCourses(), HttpStatus.OK);
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
