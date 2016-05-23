package de.htwg.konstanz.cloud.service;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import de.htwg.konstanz.cloud.models.MoodleCredentials;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


// to create a RESTful Controller (add Controller and ResponseBody)
@RestController
public class MoodleService {


    @RequestMapping("/info")
    public String info() {
        return "Moode-Service";
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<Object> getCourses(@Valid @RequestBody MoodleCredentials input) {


        Object moodle = getMoodleStuff();


        return new ResponseEntity<Object>(moodle, HttpStatus.OK);

    }

    @HystrixCommand(fallbackMethod = "getDefaultMoodleStuff")
    private Object getMoodleStuff() {
        return null;
    }

    private Object getDefaultMoodleStuff() {
        return null;
    }

}
