package de.htwg.konstanz.cloud.service;

import com.amazonaws.util.json.JSONException;
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

    /**
     * Constructor called before creation of service.
     */
    @PostConstruct
    private void init() {//NOPMD
        this.restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
    }

    /**
     * Save checkstyle and pmd Result to Database.
     *
     * @param result validation result converted form json object to string.
     * @return result of database action.
     */
    @Async
    public Future<String> saveResult(String result) throws InstantiationException {
        String route = "/addEntry";
        return addResultToDatabase(result, route);
    }

    /**
     * Save checkstyle result into database.
     *
     * @param result validation result converted form json object to string.
     * @return result of database action.
     */
    @Async
    public Future<String> saveCheckstleResult(String result) throws InstantiationException {
        String route = "/addCheckstyleEntry";
        return addResultToDatabase(result, route);
    }

    /**
     * Save pmd result into database.
     *
     * @param result validation result converted form json object to string.
     * @return result of database action.
     */
    @Async
    public Future<String> savePmdResult(String result) throws InstantiationException {
        String route = "/addPmdEntry";
        return addResultToDatabase(result, route);
    }

    /**
     * Save cpd  result into database.
     *
     * @param result validation result converted form json object to string.
     * @return result of database action.
     */
    @Async
    public Future<String> saveCpdResult(String result) throws InstantiationException {
        String route = "/cpdresults";
        return addResultToDatabase(result, route);
    }

    /**
     * Update 'exectiontime' of group. Neddet for scheduler calculation.
     *
     * @param duration duration of validation.
     * @param id       database id of group
     * @return result of database action.
     */
    @Async
    public Future<String> updateExecutionTimeOfGroup(long duration, String id) throws JSONException, InstantiationException {
        String data = "{\"executiontime\": " + duration + "}";
        String route = "/groups/updateExecutiontime/" + id;
        return addResultToDatabase(data, route);
    }

    /**
     * Method to save documents into mongodb. Get instance with round robin scheduler and execute POST.
     *
     * @param result    JSON object to save as String
     * @param saveRoute route to call for adding json object to database.
     * @return result of database action.
     */
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

    /**
     * Get group by id.
     *
     * @param groupId group id-
     * @return Json object as String. Needs to be convert.
     */
    public String getGroup(String groupId) throws InstantiationException {
        return callDatabaseRoute("/groups/" + groupId);
    }

    /**
     * Get course by id.
     *
     * @param courseId course id-
     * @return Json object as String. Needs to be convert.
     */
    public String getCourse(String courseId) throws InstantiationException {
        return callDatabaseRoute("/courses/" + courseId);
    }

    /**
     * Get last checkstlye result of user with userid.
     *
     * @param userId id of user
     * @return Json object as String. Needs to be convert.
     */
    public String getLastCheckstyleResult(String userId) throws InstantiationException {
        return callDatabaseRoute("/courses/" + userId + "/findLastCheckstyleResult");
    }

    /**
     * Get last pmd result of user with userid.
     *
     * @param userId id of user
     * @return Json object as String. Needs to be convert.
     */
    public String getLastPmdResult(String userId) throws InstantiationException {
        return callDatabaseRoute("/courses/" + userId + "/findLastPmdResult");
    }

    /**
     * Get last cpd result of user with userid.
     *
     * @param userId id of user
     * @return Json object as String. Needs to be convert.
     */
    public String getLastCpdResult(String userId) throws InstantiationException {
        return callDatabaseRoute("/cpdresults/courses/" + userId + "/findLastCpdResult");
    }

    /**
     * Method to call Database with GET.
     *
     * @param serviceRoute Route to be called.
     * @return Json Object as String.
     */
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

