package de.htwg.konstanz.cloud.service;

import com.amazonaws.util.json.JSONObject;
import de.htwg.konstanz.cloud.model.MoodleCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public class MoodleService {
    private static final Logger LOG = LoggerFactory.getLogger(MoodleService.class);


    @Autowired
    private LoadBalancerClient loadBalancer;

    @Autowired
    private GovernanceUtil util;


    @Async
    public String getCourses(String token) throws InstantiationException {
        String route = "/courses/token/" + token;

        return util.getFromService(route, "moodle");
    }

    public String getUserInformation(String token) throws InstantiationException {

        String route = "/user/token/" + token;

        return util.getFromService(route, "moodle");

    }

    public String getSubmissionsOfCourses(Integer courseid, String token) throws InstantiationException {

        String route = "/courses/" + courseid + "/groups/token/" + token;

        return util.getFromService(route, "moodle");
    }

    public String getToken(MoodleCredentials credentials) throws InstantiationException {

        String route = "/login";
        JSONObject jsonObject = new JSONObject(credentials);

        return util.postToService(route, jsonObject.toString(), "moodle");
    }

    public boolean hasPermission(Integer userId, Integer courseId, String token) throws InstantiationException {
        String route = "/courses/ " + courseId + "/users/" + userId + "/token/" + token + "/permission";

        String response = util.getFromService(route, "moodle");

        return "true".equals(response);
    }
}
