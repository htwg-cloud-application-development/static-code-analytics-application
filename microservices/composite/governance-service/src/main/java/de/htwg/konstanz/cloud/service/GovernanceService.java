package de.htwg.konstanz.cloud.service;


import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import de.htwg.konstanz.cloud.model.Courses;
import de.htwg.konstanz.cloud.model.MoodleCourse;
import de.htwg.konstanz.cloud.model.MoodleCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


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

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<String> getToken(@RequestBody String credentialsParam) {

        try {
            JSONObject obj = new JSONObject(credentialsParam);
            MoodleCredentials credentials = new MoodleCredentials(obj.getString("username"), obj.getString("password"));
            return createResponse(moodleService.getToken(credentials), HttpStatus.OK);
        } catch (InstantiationException e) {
            return createErrorResponse(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        } catch (JSONException e) {
            return createErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/import/{token}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> importCourses(@PathVariable String token) {
        try {

            // get the list of all courses
            String courses = moodleService.getCourses(token);

            return createResponse(courses, HttpStatus.OK);
        } catch (InstantiationException e) {
            return createErrorResponse(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    // TODO fix reqeustBody
    @RequestMapping(value = "/import/courses/{token}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<String> importCoursesOfProf(@PathVariable String token, @RequestBody Courses courses) {
        try {


            JSONObject user = new JSONObject(moodleService.getUserInformation(token));

            // TODO: check if successful

            databaseService.saveUser(user);

            // TODO: check if successful

            Integer userId = Integer.parseInt((String) user.get("userid"));

            // iterate over courses and save them
            for (MoodleCourse course : courses.getCourses()) {

                // first save course
                databaseService.saveCourse(userId, new JSONObject(course));

                // then get submissions of course
                String groups = moodleService.getSubmissionsOfCourses(course.getId(), token);

                // finally save submissions
                databaseService.saveGroups(course.getId(), groups);

            }

            return createResponse("{\"ok\":true}", HttpStatus.OK);

        } catch (InstantiationException e) {
            return createErrorResponse(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        } catch (JSONException e) {
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
