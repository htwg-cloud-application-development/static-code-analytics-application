package de.htwg.konstanz.cloud.service;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.*;
import com.amazonaws.util.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
class Util {

    private static final Logger LOG = LoggerFactory.getLogger(Util.class);


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

    void runNewCheckstyleInstance(AmazonEC2 ec2) {
        RunInstancesRequest runInstancesRequest = new RunInstancesRequest()
                .withInstanceType("t2.micro")
                .withImageId("ami-1e04ea71")
                .withMinCount(1)
                .withMaxCount(1)
                .withMonitoring(true)
                .withKeyName("aws_frankfurt")
                .withSecurityGroupIds("sg-5f2cd437");
        ec2.runInstances(runInstancesRequest);
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
