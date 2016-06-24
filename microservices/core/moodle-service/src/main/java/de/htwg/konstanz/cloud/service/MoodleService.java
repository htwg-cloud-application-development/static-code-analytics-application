package de.htwg.konstanz.cloud.service;


import com.fasterxml.jackson.core.JsonProcessingException;
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


    // basic route for getting name of this microservice
    @RequestMapping("/info")
    public String info() {
        return "Moodle-Service";
    }

    /**
     * Login route to get token from moodle
     * @param credentials password and username from moodle
     * @return Token from Moodle for accessing moodle
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<MoodleToken> login(@RequestBody MoodleCredentials credentials) {

        // rest template needed for performing request to moodle
        RestTemplate templ = new RestTemplate();

        // Rest URL for accesing token from moodle
        String url = "https://moodle.htwg-konstanz.de/moodle/login/token.php?"
                + "moodlewsrestformat=json&service=moodle_mobile_app"
                + "&username=" + credentials.getUsername() + "&password=" + credentials.getPassword();

        // perform request and obtain token
        MoodleToken token = templ.getForObject(url, MoodleToken.class);

        // print debug information
        System.out.println("Moodle Token for user " + credentials.getUsername() + ": " + token.getToken());

        // return token
        return new ResponseEntity(token, HttpStatus.OK);

    }

    /**
     * Route for checking permission on a certain course based on token
     * @param courseId the course which will be checked
     * @param token the token from moodle
     * @return "true" or "false" depending on the rights of that course
     */
    @RequestMapping(value = "/courses/{courseId}/token/{token}/permission", method = RequestMethod.GET)
    public ResponseEntity<String> checkPermissionWithTokenOnly(@Valid @PathVariable String courseId,
                                                               @Valid @PathVariable String token) {

        // create moodle object with token
        Moodle moodle = new Moodle(token);

        // retrievie general info based on the token
        GeneralMoodleInfo generalInfo = moodle.getMoodleInfoFromMoodleToken();

        // check the permission
        Boolean hasPermission = moodle.hasPermissionOnCourse(courseId, generalInfo.getUserid());

        // return permission status
        return new ResponseEntity<String>(hasPermission.toString(), HttpStatus.OK);

    }


    /**
     * Route for checking permission on a certain course based on token and user id
     * @param courseId the course which will be checked
     * @param userId the user which ask for permission status
     * @param token the token needed for checking the permission
     * @return "true" or "false" depending on the rights of that course
     */
    @RequestMapping(value = "/courses/{courseId}/users/{userId}/token/{token}/permission", method = RequestMethod.GET)
    public ResponseEntity<String> checkPermission(@Valid @PathVariable String courseId,
                                                  @Valid @PathVariable Integer userId,
                                                  @Valid @PathVariable String token) {

        // create moodle object with token
        Moodle moodle = new Moodle(token);

        // check the permission and return it
        return new ResponseEntity<String>(moodle.hasPermissionOnCourse(courseId, userId).toString(), HttpStatus.OK);

    }

    /**
     * route for getting all courses which belongs to a user with that token
     * @param token the token of a user
     * @return a list with all courses
     */
    @RequestMapping(value = "/courses/token/{token}", method = RequestMethod.GET)
    public ResponseEntity<List<MoodleCourse>> getCourses(@Valid @PathVariable String token) {

        // create moodle object with token
        Moodle moodle = new Moodle(token);

        // first, get the user
        GeneralMoodleInfo user = moodle.getMoodleInfoFromMoodleToken();

        try {
            // optain list of courses based on userid
            List<MoodleCourse> courses = moodle.getCoursesOfMoodleUser(user.getUserid());

            // return list
            return new ResponseEntity(courses, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            // return error in error case
            return new ResponseEntity("ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    /**
     * Route for getting the user based on a token
     * @param token a token from moodle
     * @return general user information of a moodle user
     */
    @RequestMapping(value = "/user/token/{token}", method = RequestMethod.GET)
    public ResponseEntity<GeneralMoodleInfo> getUser(@Valid @PathVariable String token) {


        // create moodle object with token
        Moodle moodle = new Moodle(token);

        // get the user
        GeneralMoodleInfo user = moodle.getMoodleInfoFromMoodleToken();

        // return user
        return new ResponseEntity(user, HttpStatus.OK);
    }

    /**
     * Route for getting all assignments of a certain course
     * @param token moodle token for accessing moodle
     * @param id the id of a certain course in moodle
     * @return a list of all assignments in a course
     */
    @RequestMapping(value = "/courses/{id}/assignment/token/{token}", method = RequestMethod.GET)
    public ResponseEntity<List<MoodleAssignment>> getAssignmentOfCourse(
            @Valid @PathVariable String token, @Valid @PathVariable int id) {

        // create moodle object with token
        Moodle moodle = new Moodle(token);


        try {
            // get all assignments in a certain course
            List<MoodleAssignment> assignments = moodle.getAssignmentsOfMoodleCourse(id);

            // return the list with assignments
            return new ResponseEntity(assignments, HttpStatus.OK);
        } catch (JsonProcessingException e) {

            // return error in error case
            return new ResponseEntity("ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    /**
     * Route for getting all submissions of an assignment in a certain course in moodle. The assignment MUST be named
     * "Repository" (not case sensitive)
     * @param token a token from moodle to access moodle information
     * @param courseid the id of a course in moodle
     * @return a list with all submissions of an assignment
     */
    @RequestMapping(value = "/courses/{courseid}/groups/token/{token}", method = RequestMethod.GET)
    public ResponseEntity<List<MoodleSubmissionOfAssignmet>> getGroupsOfCourse(
            @Valid @PathVariable String token, @Valid @PathVariable String courseid) {

        // create moodle object with token
        Moodle moodle = new Moodle(token);

        // create empty list
        List<MoodleSubmissionOfAssignmet> submissionsOfAssignment = Collections.emptyList();

        try {

            // get all assignments of given course
            List<MoodleAssignment> assignmentsOfMoodleCourse = moodle.getAssignmentsOfMoodleCourse(Integer.parseInt(courseid));

            // iterate over all assignments
            for (MoodleAssignment assignment : assignmentsOfMoodleCourse) {

                // find the assignment which is named "Repository"
                if ("repository".equals(assignment.getName().toLowerCase())) {
                    // get all submissions of that assignment
                    submissionsOfAssignment = moodle.getSubmissionsOfAssignment(assignment.getId());
                }
            }


            // return the list with all assignments
            return new ResponseEntity(submissionsOfAssignment, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            // return error in error case
            return new ResponseEntity("ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }


    /**
     * Route for getting all submissions of a certain assignment
     * @param token moodle token for accessing moodle
     * @param id the id of a certain assignment
     * @return a list of all submissions
     */
    @RequestMapping(value = "/assignments/{id}/submission/token/{token}", method = RequestMethod.GET)
    public ResponseEntity<List<MoodleSubmissionOfAssignmet>> getSubmissionsOfAssignment(
            @Valid @PathVariable String token, @Valid @PathVariable int id) {

        // create moodle object with token
        Moodle moodle = new Moodle(token);


        try {
            // retrievie all submissions of given assignment
            List<MoodleSubmissionOfAssignmet> submissions = moodle.getSubmissionsOfAssignment(id);

            // return the list of assignemnts
            return new ResponseEntity(submissions, HttpStatus.OK);
        } catch (JsonProcessingException e) {

            // return error in error case
            return new ResponseEntity("ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

}
