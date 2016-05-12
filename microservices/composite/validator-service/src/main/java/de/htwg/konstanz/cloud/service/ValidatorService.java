package de.htwg.konstanz.cloud.service;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ValidatorService {

    @RequestMapping("/info")
    public String info() {
        return "validator-Service";
    }


    @RequestMapping(value = "/validate/{groupId}", method = RequestMethod.POST)
    public String validateGroup(@RequestBody String data) {
        return null;
    }
}
