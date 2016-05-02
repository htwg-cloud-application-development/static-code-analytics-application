package de.htwg.konstanz.cloud.service;


import de.htwg.konstanz.cloud.models.MoodleCredentials;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;


// to create a RESTful Controller (add Controller and ResponseBody)
@RestController
public class MoodleService {


	@RequestMapping("/info")
	public String info() {
        return "Moode-Service";
     }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<Price> getCourses(@Valid @RequestBody MoodleCredentials input) {


        // make a request to moodle in order to obtain the token
        RestTemplate req = new RestTemplate();
        //req.postForObject("moodle", )


        return new ResponseEntity<Object>("", HttpStatus.OK);

    }

}
