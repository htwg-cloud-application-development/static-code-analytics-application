package de.htwg.konstanz.cloud.service;

import com.amazonaws.util.json.JSONObject;
import de.htwg.konstanz.cloud.model.MoodleCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


/**
 * Helper Class for accessing the moodle microservice
 */
@Service
public class MoodleService {


    // Utility class for performing requests
    @Autowired
    private GovernanceUtil util;


    /**
     * Get courses from moodle
     * @param token a token from moodle
     * @return all courses
     * @throws InstantiationException if service is not avaliable
     */
    @Async
    public String getCourses(String token) throws InstantiationException {

        // call microservice and return result
        return util.getFromService("/courses/token/" + token, "moodle");
    }

    /**
     * Get user information from moodle
     * @param token a token from moodle
     * @return user information
     * @throws InstantiationException if service is not avaliable
     */
    public String getUserInformation(String token) throws InstantiationException {

        // assemble route
        String route = "/user/token/" + token;

        // call microservice and return result
        return util.getFromService(route, "moodle");

    }

    /**
     * Get all submissions
     * @param courseid the id of the course in moodle
     * @param token a token of moodle
     * @return all submissions
     * @throws InstantiationException if service is not avaliable
     */
    public String getSubmissionsOfCourses(Integer courseid, String token) throws InstantiationException {

        // assemble route
        String route = "/courses/" + courseid + "/groups/token/" + token;

        // call microservice and return result
        return util.getFromService(route, "moodle");
    }

    /**
     * Get Token from moodle
     * @param credentials username and password for moodle
     * @return a token
     * @throws InstantiationException if service is not avaliable
     */
    public String getToken(MoodleCredentials credentials) throws InstantiationException {

        // assemble route
        String route = "/login";

        // create json of object
        JSONObject jsonObject = new JSONObject(credentials);

        // call microservice and return result
        return util.postToService(route, jsonObject.toString(), "moodle");
    }

    /**
     * Check permision status
     * @param userId the id of a user in moodle
     * @param courseId the id of a course in moodle
     * @param token a token from moodle
     * @return true if the user has permissions on that course, false otherwise
     * @throws InstantiationException if service is not avaliable
     */
    public boolean hasPermission(Integer userId, Integer courseId, String token) throws InstantiationException {

        // assemble route
        String route = "/courses/ " + courseId + "/users/" + userId + "/token/" + token + "/permission";

        // call microservice
        String response = util.getFromService(route, "moodle");

        // check response and return boolean
        return "true".equals(response);
    }
}
