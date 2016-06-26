package de.htwg.konstanz.cloud.service;

import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.concurrent.Future;

@Service
public class DatabaseService {

    private RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient loadBalancer;

    @PostConstruct
    private void init() {//NOPMD
        this.restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
    }

    @Async
    public Future<String> saveResult(String result) throws InstantiationException {
        String route = "/addEntry";
        return addResultToDatabase(result, route);
    }

    @Async
    public Future<String> saveCheckstleResult(String result) throws InstantiationException {
        String route = "/addCheckstyleEntry";
        return addResultToDatabase(result, route);
    }

    @Async
    public Future<String> savePmdResult(String result) throws InstantiationException {
        String route = "/addPmdEntry";
        return addResultToDatabase(result, route);
    }

    @Async
    public Future<String> saveCpdResult(String result) throws InstantiationException {
        String route = "/cpdresults";
        return addResultToDatabase(result, route);
    }

    @Async
    public Future<String> updateExecutionTimeOfGroup(long duration, String id) throws JSONException, InstantiationException {
        JSONObject executiontime = new JSONObject();
        executiontime.put("executiontime", duration);
        String route = "/updateExecutiontime/" + id;
        return addResultToDatabase(executiontime.toString(), route);
    }

    private Future<String> addResultToDatabase(String result, String saveRoute) throws InstantiationException {
        // get database service instance
        ServiceInstance instance = loadBalancer.choose("mongo");
        if (null != instance) {
            // build request url
            String requestUrl = instance.getUri() + saveRoute;
            // POST to request url and get String (JSON)
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAcceptCharset(Collections.singletonList(Charset.forName("UTF-8")));
            HttpEntity<String> entity = new HttpEntity<>(result, headers);
            // post to service and return response
            return new AsyncResult<>(restTemplate.postForObject(requestUrl, entity, String.class));
        }
        throw new InstantiationException("service is not available");
    }

    public String getGroup(String groupId) throws InstantiationException {
        return callDatabaseRoute("/groups/" + groupId);
    }

    public String getCourse(String courseId) throws InstantiationException {
        return callDatabaseRoute("/courses/" + courseId);
    }

    public String getLastCheckstyleResult(String userId) throws InstantiationException {
        return callDatabaseRoute("/courses/" + userId + "/findLastCheckstyleResult");
    }

    public String getLastPmdResult(String userId) throws InstantiationException {
        return callDatabaseRoute("/courses/" + userId + "/findLastPmdResult");
    }

    public String getLastCpdResult(String userId) throws InstantiationException {
        return callDatabaseRoute("/courses/" + userId + "/findLastCpdResult");
    }

    private String callDatabaseRoute(String serviceRoute) throws InstantiationException {
        ServiceInstance instance = loadBalancer.choose("mongo");
        if (null != instance) {
            // build request url
            String requestUrl = instance.getUri() + serviceRoute;
            // POST to request url and get String (JSON)
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            ResponseEntity<String> entity = restTemplate.getForEntity(requestUrl, String.class, headers);
            return entity.getBody();
        }
        throw new InstantiationException("service is not available");
    }
}

