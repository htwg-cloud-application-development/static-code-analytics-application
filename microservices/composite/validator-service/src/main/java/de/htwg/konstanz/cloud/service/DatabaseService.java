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
    private static final Logger LOG = LoggerFactory.getLogger(ValidateRepositoryService.class);

    private RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient loadBalancer;

    @PostConstruct
    private void init() {
        this.restTemplate = new RestTemplate();
    }

    @Async
    public Future<String> saveResult(String result) throws InstantiationException {
        // TODO refactor to util class
        String SAVE_ROUTE = "/addEntry";

        // get database service instance
        ServiceInstance instance = loadBalancer.choose("mongo-service");
        if (null != instance) {
            // build request url
            String requestUrl = instance.getUri() + SAVE_ROUTE;
            // POST to request url and get String (JSON)
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            ResponseEntity<String> entity = restTemplate.postForEntity(requestUrl, result, String.class, headers);
            return new AsyncResult<>(entity.getBody());
        }
        throw new InstantiationException("service is not available");
    }

    public String getGroup(String groupId) throws InstantiationException {
        String GET_GROUP_ROUTE = "/groups/" + groupId;

        ServiceInstance instance = loadBalancer.choose("mongo-service");
        if (null != instance) {
            // build request url
            String requestUrl = instance.getUri() + GET_GROUP_ROUTE;
            // POST to request url and get String (JSON)
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            ResponseEntity<String> entity = restTemplate.getForEntity(requestUrl, String.class, headers);
            return entity.getBody();
        }
        throw new InstantiationException("service is not available");
    }

}

