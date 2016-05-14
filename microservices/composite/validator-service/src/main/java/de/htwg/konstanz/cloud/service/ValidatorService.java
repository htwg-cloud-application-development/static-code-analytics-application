package de.htwg.konstanz.cloud.service;

import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import de.htwg.konstanz.cloud.model.ValidationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.net.URI;

@RestController
public class ValidatorService {

    @Autowired
    private LoadBalancerClient loadBalancer;

    private RestTemplate restTemplate;

    @PostConstruct
    private void init() {
        this.restTemplate = new RestTemplate();
    }

    @RequestMapping("/info")
    public String info() {
        return "Validator-Service";
    }


    @RequestMapping(value = "/courses/{courseId}/validate", method = RequestMethod.POST)
    public String validateCourse(@PathVariable String courseId) {
        return null;
    }


    @RequestMapping(value = "/courses/{courseId}/groups/{groupId}/validate", method = RequestMethod.POST)
    public String validateGroup(@PathVariable String courseId, @PathVariable String groupId) {
        return null;
    }

    // TODO call service asynchronously - hystrix
    // TODO error handling e.g. loadBalancer
    @RequestMapping(value = "/validate", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<String> validateGroup(@RequestBody ValidationData data) {
        String VALIDATE_ROUTE = "/validate";
        JSONObject json = new JSONObject();
        try {
            // build json object for request object
            json.put("repositoryUrl", data.getRepositoryUrl());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // get checkstyle service instance
        ServiceInstance instance = loadBalancer.choose("checkstyle-service");
        // build request url
        String requestUrl = instance.getUri() + VALIDATE_ROUTE;
        // POST to request url and get String (JSON)
        ResponseEntity<String> entity = restTemplate.postForEntity(requestUrl, json.toString(), String.class);
        return entity;
    }
}
