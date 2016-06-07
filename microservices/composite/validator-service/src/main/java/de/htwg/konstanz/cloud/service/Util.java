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
        if (!securityGroup.isEmpty() && !checkstyleImageId.isEmpty()
                && !checkstyleInstanceType.isEmpty() && !checkstyleKeyName.isEmpty()) {

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

    List<Instance> getAllActiveInstances(AmazonEC2 ec2) {
        List<Instance> instances = new ArrayList();
        for (Instance instance : getAllInstances(ec2)) {
            if (instance.getState().getCode() == 16 || instance.getState().getCode() == 0) {
                instances.add(instance);
            }
        }
        return instances;
    }

    List<Instance> getAllInstances(AmazonEC2 ec2) {
        DescribeInstancesResult result = ec2.describeInstances();
        List<Reservation> reservations = result.getReservations();
        List<Instance> instances = new ArrayList();

        for (Reservation reservation : reservations) {
            instances.addAll(reservation.getInstances());
        }
        return instances;
    }


}
