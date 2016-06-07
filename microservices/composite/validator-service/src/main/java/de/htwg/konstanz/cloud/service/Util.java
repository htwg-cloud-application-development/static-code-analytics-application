package de.htwg.konstanz.cloud.service;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.*;
import com.amazonaws.util.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
class Util {

    private static final Logger LOG = LoggerFactory.getLogger(Util.class);

    @Value("${app.aws.securityGroup}")
    private String securityGroup;

    @Value("${app.aws.services.checkstyle.imageId}")
    private String checkstyleImageId;

    @Value("${app.aws.services.checkstyle.instanceType}")
    private String checkstyleInstanceType;

    @Value("${app.aws.services.checkstyle.keyName}")
    private String checkstyleKeyName;


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

    void runNewCheckstyleInstance(AmazonEC2 ec2, int minCount, int maxCount) throws NoSuchFieldException {
        if (null != securityGroup && null != checkstyleImageId
                && null != checkstyleInstanceType && null != checkstyleKeyName) {

            if (minCount < 1) minCount = 1;
            if (maxCount < 1) maxCount = 1;
            if (minCount > maxCount) maxCount = minCount;

            RunInstancesRequest runInstancesRequest = new RunInstancesRequest()
                    .withInstanceType(checkstyleInstanceType)
                    .withImageId(checkstyleImageId)
                    .withMinCount(minCount)
                    .withMaxCount(maxCount)
                    .withMonitoring(true)
                    .withKeyName(checkstyleKeyName)
                    .withSecurityGroupIds(securityGroup);
            ec2.runInstances(runInstancesRequest);
        } else {
            throw new NoSuchFieldException("Missing Config Parameter for one of following parameter:\n " +
                    "[securityGroup=" + securityGroup +
                    "|checkstyleImageId=" + checkstyleImageId +
                    "|checkstyleInstanceType=" + checkstyleInstanceType +
                    "|checkstyleKeyName=" + checkstyleKeyName + "]");
        }
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
        if (null == checkstyleImageId) throw new NoSuchFieldException("Missing Config Parameter [checkstyleImageId]");
        List<Instance> allActiveInstances = getAllActiveInstances(ec2);
        int numberOfInstances = 0;
        for (Instance instance : allActiveInstances) {
            if (instance.getImageId().equals(checkstyleImageId)) numberOfInstances++;
        }
        return numberOfInstances;
    }
}
