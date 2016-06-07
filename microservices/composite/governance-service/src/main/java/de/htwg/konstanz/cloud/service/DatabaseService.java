package de.htwg.konstanz.cloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Service
public class DatabaseService {

    private RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient loadBalancer;

    @Autowired
    private GovernanceUtil util;

    @PostConstruct
    private void init() {
        this.restTemplate = new RestTemplate();
    }

    public String getAllGroups() throws InstantiationException {
        return callDatabaseFor("/groups");
    }


    public String getGroupWithId(String groupId) throws InstantiationException {
        return callDatabaseFor("/groups/" + groupId);
    }

    public String getAllCourses() throws InstantiationException {
        return callDatabaseFor("/courses");
    }

    public String getCourseWithId(String courseId) throws InstantiationException {
        return callDatabaseFor("/courses/" + courseId);
    }

    private String callDatabaseFor(String ROUTE) throws InstantiationException {
        return util.getFromService(ROUTE, "mongo");
    }

    public String saveCourses(String valueToSave) throws InstantiationException {
        return postDataBaseFor("", valueToSave); // TODO: wait for route of database
    }

    private String postDataBaseFor(String route, String data) throws InstantiationException {
        return util.postToService(route, data, "mongo");
    }

    public String saveGroups(Integer courseid, String groups) {
        return null;
    }
}

