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

/**
 * Utility class for calling microservices
 */
@Service
public class GovernanceUtil {

    // Rest template needed for performing requests
    private RestTemplate restTemplate;

    // Load Balancer to get the microservice adress
    @Autowired
    private LoadBalancerClient loadBalancer;

    // create an Object of the rest template
    @PostConstruct
    private void init() {
        this.restTemplate = new RestTemplate();
    }

    /**
     * Perform a GET request to a certain microservice
     * @param route the route of a microservice
     * @param service the name of the microservice
     * @return the result of the microservice
     * @throws InstantiationException if service is not avaliable
     */
    public String getFromService(String route, String service) throws InstantiationException {

        // get the instance
        ServiceInstance instance = getInstanceOfService(service);

        // build request url
        String requestUrl = instance.getUri() + route;

        // Construct the headers for the requests
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Perform the request
        ResponseEntity<String> entity = restTemplate.getForEntity(requestUrl, String.class, headers);

        // return response as String
        return entity.getBody();
    }

    /**
     * Performing a POST request to the service
     * @param route the route of the microservice
     * @param data payload data of the request
     * @param service the name of the service
     * @return the result of the service
     * @throws InstantiationException if service is not avaliable
     */
    public String postToService(String route, String data, String service) throws InstantiationException {

        // get the instance
        ServiceInstance instance = getInstanceOfService(service);

        // build request url
        String requestUrl = instance.getUri() + route;

        // Construct headers for request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(data, headers);

        // post to service and return response
        return restTemplate.postForObject(requestUrl, entity, String.class);


    }

    /**
     * Private helper method to get the instance of a microservice
     * @param service name of the microservice
     * @return the microservice {@link ServiceInstance}
     * @throws InstantiationException if service is not avaliable
     */
    private ServiceInstance getInstanceOfService(String service) throws InstantiationException {

        // let the loadbalancer find the adress of the microservice
        ServiceInstance instance = loadBalancer.choose(service);

        // check if an instance was found
        if (null == instance) {

            // throw exception
            throw new InstantiationException("service is not available");
        } else {

            // return the found instance
            return instance;
        }
    }
}
