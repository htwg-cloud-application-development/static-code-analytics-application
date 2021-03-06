package de.htwg.konstanz.cloud.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.concurrent.Future;

@Service
public class ValidateRepositoryService {
    private static final Logger LOG = LoggerFactory.getLogger(ValidateRepositoryService.class);

    private RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient loadBalancer;

    @PostConstruct
    private void init() {//NOPMD
        this.restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
    }

    @Async
    public Future<String> validateCodeDublication(String repositoryUrlJsonObj, URI requestUri) {
        String validateRoute = "/validate/copypaste";
        LOG.info("Validate dublication for " + repositoryUrlJsonObj);
        return executePostRequest(repositoryUrlJsonObj, requestUri + validateRoute);
    }

    @Async
    public Future<String> validateRepository(String repositoryUrlJsonObj, URI requestUri) {
        String validateRoute = "/validate";
        LOG.info("Validate " + repositoryUrlJsonObj);
        return executePostRequest(repositoryUrlJsonObj, requestUri + validateRoute);
    }

    private Future<String> executePostRequest(String repositoryUrlJsonObj, String requestUrl) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(repositoryUrlJsonObj, headers);
        // post to service and return response
        return new AsyncResult<>(restTemplate.postForObject(requestUrl, entity, String.class));
    }

}
