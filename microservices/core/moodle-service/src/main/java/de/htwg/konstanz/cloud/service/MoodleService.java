package de.htwg.konstanz.cloud.service;


import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import de.htwg.konstanz.cloud.models.*;
import de.htwg.konstanz.cloud.moodle.Moodle;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;


// to create a RESTful Controller (add Controller and ResponseBody)
@RestController
public class MoodleService {


    @RequestMapping("/info")
    public String info() {
        return "Moodle-Service";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<MoodleCredentials> login(@RequestBody String credentials) throws JSONException {


        JSONObject credentialsObject = new JSONObject(credentials);

        RestTemplate templ = new RestTemplate();

        String url = "https://moodle.htwg-konstanz.de/moodle/login/token.php?" +
                "moodlewsrestformat=json&service=moodle_mobile_app" +
                "&username=" + credentialsObject.getString("username") + "&password=" + credentialsObject.getString("password");

        MoodleToken token = templ.getForObject(url, MoodleToken.class);

        return new ResponseEntity(token, HttpStatus.OK);

    }

    @RequestMapping(value = "/courses/token/{token}", method = RequestMethod.GET)
    public ResponseEntity<List<MoodleCourse>> getCourses(@Valid @PathVariable String token) {


        Moodle moodle = new Moodle(token);

        // first, get the user
        GeneralMoodleInfo user = moodle.getMoodleInfoFromMoodleToken();

        try {
            List<MoodleCourse> courses = moodle.getCoursesOfMoodleUser(user.getUserid());

            return new ResponseEntity(courses, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            return new ResponseEntity("ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @RequestMapping(value = "/user/token/{token}", method = RequestMethod.GET)
    public ResponseEntity<List<MoodleCourse>> getUser(@Valid @PathVariable String token) {


        Moodle moodle = new Moodle(token);

        // get the user
        GeneralMoodleInfo user = moodle.getMoodleInfoFromMoodleToken();

        return new ResponseEntity(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/courses/{id}/assignment/token/{token}", method = RequestMethod.GET)
    public ResponseEntity<List<MoodleAssignment>> getAssignmentOfCourse(
            @Valid @PathVariable String token, @Valid @PathVariable int id) {

        Moodle moodle = new Moodle(token);


        try {
            List<MoodleAssignment> assignments = moodle.getAssignmentsOfMoodleCourse(id);

            return new ResponseEntity(assignments, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            return new ResponseEntity("ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    // helper method to gather all needed information
    @RequestMapping(value = "/courses/{courseid}/groups/token/{token}", method = RequestMethod.GET)
    public ResponseEntity<List<MoodleSubmissionOfAssignmet>> getGroupsOfCourse(
            @Valid @PathVariable String token, @Valid @PathVariable String courseid) {

        Moodle moodle = new Moodle(token);

        List<MoodleSubmissionOfAssignmet> submissionsOfAssignment = Collections.emptyList();

        try {

            List<MoodleAssignment> assignmentsOfMoodleCourse = moodle.getAssignmentsOfMoodleCourse(Integer.parseInt(courseid));

            for (MoodleAssignment assignment : assignmentsOfMoodleCourse) {

                if ("repository".equals(assignment.getName().toLowerCase())) {
                    submissionsOfAssignment = moodle.getSubmissionsOfAssignment(assignment.getId());
                }
            }


            return new ResponseEntity(submissionsOfAssignment, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            return new ResponseEntity("ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }


    @RequestMapping(value = "/assignments/{id}/submission/token/{token}", method = RequestMethod.GET)
    public ResponseEntity<List<MoodleSubmissionOfAssignmet>> getSubmissionsOfAssignment(
            @Valid @PathVariable String token, @Valid @PathVariable int id) {

        Moodle moodle = new Moodle(token);


        try {
            List<MoodleSubmissionOfAssignmet> submissions = moodle.getSubmissionsOfAssignment(id);

            return new ResponseEntity(submissions, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            return new ResponseEntity("ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @HystrixCommand(fallbackMethod = "getDefaultMoodleStuff")
    private Object getMoodleStuff() {
        return null;
    }

    private Object getDefaultMoodleStuff() {
        return null;
    }

}
