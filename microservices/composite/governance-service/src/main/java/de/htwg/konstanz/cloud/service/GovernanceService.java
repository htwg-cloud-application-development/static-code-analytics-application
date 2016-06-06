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


    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String info() {
        return "governance";
    }

    @Autowired
    DatabaseService databaseService;

    @Autowired
    MoodleService moodleService;

    @RequestMapping(value = "/courses", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> getCouses() {
        try {
            return createResponse(databaseService.getAllCourses(), HttpStatus.OK);
        } catch (InstantiationException e) {
            return createErrorResponse(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @RequestMapping(value = "/groups", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> getGroups() {
        try {
            // TODO alle Gruppen (Nur mit Zusammenfassung)
            return createResponse(databaseService.getAllGroups(), HttpStatus.OK);
        } catch (InstantiationException e) {
            return createErrorResponse(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @RequestMapping(value = "/groups/{groupId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> getGroup(@PathVariable String groupId) {
        try {

            // TODO mit letztem Ergebnis, wenn vorhanden
            return createResponse(databaseService.getGroupWithId(groupId), HttpStatus.OK);
        } catch (InstantiationException e) {
            return createErrorResponse(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @RequestMapping(value = "/courses/{courseId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> getCourse(@PathVariable String courseId) {
        try {
            return createResponse(databaseService.getCourseWithId(courseId), HttpStatus.OK);
        } catch (InstantiationException e) {
            return createErrorResponse(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @RequestMapping(value = "/import/{token}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> importCourses(@PathVariable String token) {
        try {

            // get the list of all courses
            String courses = moodleService.getCourses(token);

            // get the user information of the prof
            String user = moodleService.getUserInformation(token);

            // compose json for database
            String valueToSave = "{ \"user\":" + user + ",\"courses\":" + courses + "}";

            databaseService.saveCourses(valueToSave);

            return createResponse(valueToSave, HttpStatus.OK);
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
