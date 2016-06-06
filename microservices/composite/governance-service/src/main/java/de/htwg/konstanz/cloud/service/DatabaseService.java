package de.htwg.konstanz.cloud.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.concurrent.Future;

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

    public void saveCourses(String valueToSave) {
    }
}

