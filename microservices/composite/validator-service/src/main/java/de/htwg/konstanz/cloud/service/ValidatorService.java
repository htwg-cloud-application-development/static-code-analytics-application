package de.htwg.konstanz.cloud.service;

import de.htwg.konstanz.cloud.model.ValidationData;
import org.springframework.web.bind.annotation.*;

@RestController
public class ValidatorService {

    @RequestMapping("/info")
    public String info() {
        return "validator-Service";
    }


    @RequestMapping(value = "/courses/{courseId}/validate", method = RequestMethod.POST)
    public String validateCourse(@PathVariable String courseId) {
        return null;
    }


    @RequestMapping(value = "/courses/{courseId}/groups/{groupId}/validate", method = RequestMethod.POST)
    public String validateGroup(@PathVariable String courseId, @PathVariable String groupId) {
        return null;
    }

    @RequestMapping(value = "/validate", method = RequestMethod.POST, consumes = "application/json")
    public String validateGroup(@RequestBody ValidationData data) {
        return data.getRepositoryUrl();
    }
}
