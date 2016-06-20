package de.htwg.konstanz.cloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Service
public class GovernanceUtil {

    private RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient loadBalancer;

    @PostConstruct
    private void init() {
        this.restTemplate = new RestTemplate();
    }

    public String getFromService(String route, String service) throws InstantiationException {
        ServiceInstance instance = getInstanceOfService(service);

        // build request url
        String requestUrl = instance.getUri() + route;

        // Get to request url and get String (JSON)
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<String> entity = restTemplate.getForEntity(requestUrl, String.class, headers);

        return entity.getBody();
    }

    public String postToService(String route, String data, String service) throws InstantiationException {
        ServiceInstance instance = getInstanceOfService(service);

        // build request url
        String requestUrl = instance.getUri() + route;

        // Get to request url and get String (JSON)
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(data, headers);

        // post to service and return response
        return restTemplate.postForObject(requestUrl, entity, String.class);


    }

    private ServiceInstance getInstanceOfService(String service) throws InstantiationException {
        ServiceInstance instance = loadBalancer.choose(service);
        if (null == instance) {
            throw new InstantiationException("service is not available");
        } else {
            return instance;
        }
    }
}
