package de.htwg.konstanz.cloud.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
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
    public Future<String> validateRepository(String repositoryUrlJsonObj) throws InstantiationException {
        String VALIDATE_ROUTE = "/validate";
        System.out.println("Validate " + repositoryUrlJsonObj);

        // get checkstyle service instance
        ServiceInstance instance = loadBalancer.choose("checkstyle");
        if (null != instance) {
            // build request url
            String requestUrl = instance.getUri() + VALIDATE_ROUTE;
            // POST to request url and get String (JSON)
            LOG.debug("repositoryUrl: " + repositoryUrlJsonObj);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(repositoryUrlJsonObj, headers);
            // post to service and return response
            return new AsyncResult<String>(restTemplate.postForObject(requestUrl, entity, String.class));
        }
        throw new InstantiationException("service is not available");
    }

}
