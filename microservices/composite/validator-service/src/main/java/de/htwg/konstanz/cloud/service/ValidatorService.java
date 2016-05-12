package de.htwg.konstanz.cloud.service;

import de.htwg.konstanz.cloud.model.ValidationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
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

    @RequestMapping(value = "/validate", method = RequestMethod.POST, consumes = "application/json")
    public String validateGroup(@RequestBody ValidationData data) {
        // TODO POST github url
        String response = getRequestObject("checkstyle-service", "/checkstyle", String.class);

        // TODO search for checkstyle services - eureka
        // TODO select random instance - ribbon
        // TODO call service asynchronously - hystrix

        return response;
    }

    protected <T> T getRequestObject(String serviceId, String requestURI, Class<T> type) {
        String getUserURL = getRequestUrl(serviceId, requestURI);
        return restTemplate.getForObject(getUserURL, type);

    }

    protected String getRequestUrl(String serviceId, String requestURI) {
        // get service URL for message-service
        ServiceInstance instance = loadBalancer.choose(serviceId);
        // build request URL
        return instance.getUri() + requestURI;
    }
}
