package de.htwg.konstanz.cloud.service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ValidatorService {

    @RequestMapping("/info")
    public String info() {
        return "validator-Service";
    }


    @RequestMapping(value = "/courses/{courseId}/validate", method = RequestMethod.POST, consumes = "application/json")
    public String validateCourse(@PathVariable String courseId) {
        return null;
    }


    @RequestMapping(value = "/courses/{courseId}/groups/{groupId}/validate", method = RequestMethod.POST, consumes = "application/json")
    public String validateGroup(@PathVariable String courseId, @PathVariable String groupId) {
        return null;
    }
}
