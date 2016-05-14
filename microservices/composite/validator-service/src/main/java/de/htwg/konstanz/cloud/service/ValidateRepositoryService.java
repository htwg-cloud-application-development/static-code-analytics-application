package de.htwg.konstanz.cloud.service;

import com.amazonaws.util.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.concurrent.Future;

@Service
public class ValidateRepositoryService {
    private static final Logger LOG = LoggerFactory.getLogger(ValidateRepositoryService.class);

    RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient loadBalancer;

    @PostConstruct
    private void init() {
        this.restTemplate = new RestTemplate();
    }

    @Async
    public Future<String> validateRepository(String repositoryUrl) throws InterruptedException, InstantiationException {
        String VALIDATE_ROUTE = "/validate";
        System.out.println("Validate " + repositoryUrl);

        // get checkstyle service instance
        ServiceInstance instance = loadBalancer.choose("checkstyle-service");
        if (null != instance) {
            // build request url
            String requestUrl = instance.getUri() + VALIDATE_ROUTE;
            // POST to request url and get String (JSON)
            System.out.println(repositoryUrl);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            ResponseEntity<String> entity = restTemplate.postForEntity(requestUrl, repositoryUrl, String.class, headers);
            return new AsyncResult<String>(entity.getBody());
        }
        throw new InstantiationException("service is not available");
    }

}
