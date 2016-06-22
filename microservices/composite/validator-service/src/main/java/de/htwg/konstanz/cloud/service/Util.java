package de.htwg.konstanz.cloud.service;

import com.amazonaws.services.cloudwatch.AmazonCloudWatchClient;
import com.amazonaws.services.cloudwatch.model.ComparisonOperator;
import com.amazonaws.services.cloudwatch.model.Dimension;
import com.amazonaws.services.cloudwatch.model.PutMetricAlarmRequest;
import com.amazonaws.services.cloudwatch.model.Statistic;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;
import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Component
class Util {

    private static final Logger LOG = LoggerFactory.getLogger(Util.class);

    @Value("${app.aws.securityGroup}")
    private String securityGroup;

    /* checkstyle config */
    @Value("${app.aws.services.checkstyle.imageId}")
    private String checkstyleImageId;

    @Value("${app.aws.services.checkstyle.instanceType}")
    private String checkstyleInstanceType;

    @Value("${app.aws.services.checkstyle.keyName}")
    private String checkstyleKeyName;

    /* pmd config */
    @Value("${app.aws.services.pmd.imageId}")
    private String pmdImageId;

    @Value("${app.aws.services.pmd.instanceType}")
    private String pmdInstanceType;

    @Value("${app.aws.services.pmd.keyName}")
    private String pmdKeyName;


    <T> ResponseEntity<T> createResponse(T body, HttpStatus httpStatus) {
        return new ResponseEntity<>(body, httpStatus);
    }

    ResponseEntity<String> createErrorResponse(String errorMessage, HttpStatus status) {
        LOG.error(errorMessage);
        HashMap<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", errorMessage);
        JSONObject errorResponseObject = new JSONObject(errorResponse);
        return createResponse(errorResponseObject.toString(), status);
    }

    RunInstancesResult runNewCheckstyleInstance(AmazonEC2 ec2, int minCount, int maxCount) throws NoSuchFieldException {
        if (null == securityGroup && null == checkstyleImageId
                && null == checkstyleInstanceType && null == checkstyleKeyName) {

            throw new NoSuchFieldException("Missing Config Parameter for one of following parameter:\n "
                    + "[securityGroup=" + securityGroup
                    + "|checkstyleImageId=" + checkstyleImageId
                    + "|checkstyleInstanceType=" + checkstyleInstanceType
                    + "|checkstyleKeyName=" + checkstyleKeyName + "]");

        } else {
            return runInstance(ec2, minCount, maxCount, checkstyleImageId,
                    checkstyleKeyName, checkstyleInstanceType, securityGroup);
        }
    }

    RunInstancesResult runNewPmdInstance(AmazonEC2 ec2, int minCount, int maxCount) throws NoSuchFieldException {
        if (null == securityGroup && null == pmdImageId
                && null == pmdKeyName && null == pmdInstanceType) {

            throw new NoSuchFieldException("Missing Config Parameter for one of following parameter:\n "
                    + "[securityGroup=" + securityGroup
                    + "|pmdImageId=" + pmdImageId
                    + "|pmdInstanceType=" + pmdImageId
                    + "|pmdKeyName=" + pmdImageId + "]");

        } else {
            return runInstance(ec2, minCount, maxCount, pmdImageId, pmdKeyName, pmdInstanceType, securityGroup);
        }
    }

    private RunInstancesResult runInstance(AmazonEC2 ec2, int minCountParam, int maxCountParam, String imageId,
                                           String keyName, String instanceType, String securityGroup) {
        int maxCount;
        int minCount;
        if (minCountParam < 1) {
            minCount = 1;
        } else {
            minCount = minCountParam;
        }
        if (maxCountParam < 1) {
            maxCount = 1;
        } else {
            if (minCountParam > maxCountParam) {
                maxCount = minCountParam;
            } else {
                maxCount = maxCountParam;
            }
        }

        RunInstancesRequest runInstancesRequest = new RunInstancesRequest()
                .withInstanceType(instanceType)
                .withImageId(imageId)
                .withMinCount(minCount)
                .withMaxCount(maxCount)
                .withMonitoring(true)
                .withKeyName(keyName)
                .withSecurityGroupIds(securityGroup);
        RunInstancesResult result = ec2.runInstances(runInstancesRequest);
        for (Instance instance : result.getReservation().getInstances()) {
            createDefaultAlarm(instance.getInstanceId());
        }
        return result;
    }

    private List<Instance> getAllActiveInstances(AmazonEC2 ec2) {
        return getAllInstances(ec2)
                .stream()
                .filter(instance -> instance.getState().getCode() == 16 || instance.getState().getCode() == 0)
                .collect(Collectors.toList());
    }

    private List<Instance> getAllInstances(AmazonEC2 ec2) {
        List<Reservation> reservations = ec2.describeInstances().getReservations();
        List<Instance> instances = new ArrayList<>();

        for (Reservation reservation : reservations) {
            instances.addAll(reservation.getInstances());
        }
        return instances;
    }

    int getNumberOfActiveCheckstyleInstances(AmazonEC2 ec2) throws NoSuchFieldException {
        return getNumberOfActiveInstances(ec2, checkstyleImageId);
    }

    int getNumberOfActivePmdInstances(AmazonEC2 ec2) throws NoSuchFieldException {
        return getNumberOfActiveInstances(ec2, pmdImageId);
    }

    private int getNumberOfActiveInstances(AmazonEC2 ec2, String imageId) throws NoSuchFieldException {
        if (null == imageId) {
            throw new NoSuchFieldException("Missing Config Parameter [ImageId]");
        }
        List<Instance> allActiveInstances = getAllActiveInstances(ec2);
        int numberOfInstances = 0;
        for (Instance instance : allActiveInstances) {
            if (instance.getImageId().equals(pmdImageId)) {
                numberOfInstances++;
            }
        }
        return numberOfInstances;
    }

    private void createDefaultAlarm(String instanceId) {
        AmazonCloudWatchClient cloudWatch = new AmazonCloudWatchClient();
        cloudWatch.setEndpoint("monitoring.eu-central-1.amazonaws.com");

        Dimension dimension = new Dimension();
        dimension.setName("InstanceId");
        dimension.setValue(instanceId);

        cloudWatch.putMetricAlarm(new PutMetricAlarmRequest()
                .withAlarmName(instanceId)
                .withStatistic(Statistic.Average)
                .withThreshold(40.00)
                .withPeriod(300)
                .withMetricName("CPUUtilization")
                .withNamespace("AWS/EC2")
                .withComparisonOperator(ComparisonOperator.LessThanOrEqualToThreshold)
                .withDimensions(dimension)
                .withAlarmActions("arn:aws:automate:eu-central-1:ec2:terminate")
                .withEvaluationPeriods(1)
                .withActionsEnabled(true));
    }

    void removeFirstElementFormList(List<URI> availableCheckstyleInstancesList) {
        // remove first element of available Instance list
        Iterator<URI> it = availableCheckstyleInstancesList.iterator();
        if (it.hasNext()) {
            it.next();
            it.remove();
        }
    }

    /**
     * Get all repositories from json course object
     *
     * @param course JSON object of courses as String
     * @return Arrays with all repositories. For each repository an object with repository.
     * @throws JSONException If string to json failed
     */
    public JSONArray getRepositoriesFromJsonObject(String course) throws JSONException {
        // Convert to json
        JSONObject jsonObj = new JSONObject(course);
        // get all grous
        JSONArray groups = jsonObj.getJSONArray("groups");
        // result json object
        JSONArray result = new JSONArray();

        // get repository url from obejct and save it to result array
        for (int i = 0; i < groups.length(); i++) {
            JSONObject obj = groups.getJSONObject(i);
            JSONObject element = new JSONObject();
            element.put("repository", obj.getString("repository"));
            result.put(element);
        }

        // return json array with all repositories of course
        return result;


    }
}
