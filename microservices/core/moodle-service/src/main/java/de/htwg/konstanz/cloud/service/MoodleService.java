package de.htwg.konstanz.cloud.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import de.htwg.konstanz.cloud.models.GeneralMoodleInfo;
import de.htwg.konstanz.cloud.models.MoodleAssignment;
import de.htwg.konstanz.cloud.models.MoodleCourse;
import de.htwg.konstanz.cloud.models.MoodleToken;
import de.htwg.konstanz.cloud.moodle.Moodle;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


// to create a RESTful Controller (add Controller and ResponseBody)
@RestController
public class MoodleService {


    @RequestMapping("/info")
    public String info() {
        return "Moodle-Service";
    }


    @RequestMapping(value = "/courses", method = RequestMethod.POST)
    public ResponseEntity<List<MoodleCourse>> getCourses(@Valid @RequestBody MoodleToken moodleToken) {


        Moodle moodle = new Moodle(moodleToken.getToken());

        // first, get the user
        GeneralMoodleInfo user = moodle.getMoodleInfoFromMoodleToken();

        try {
            List<MoodleCourse> courses = moodle.getCoursesOfMoodleUser(user.getUserid());


            return new ResponseEntity(courses, HttpStatus.OK);


        } catch (JsonProcessingException e) {
            return new ResponseEntity("ERROR", HttpStatus.OK);
        }


    }

    @RequestMapping(value = "/courses/{id}/assignment", method = RequestMethod.POST)
    public ResponseEntity<List<MoodleAssignment>> getCourses2(
            @Valid @RequestBody MoodleToken moodleToken, @Valid @PathVariable int id) {

        Moodle moodle = new Moodle(moodleToken.getToken());


        try {
            List<MoodleAssignment> assignments = moodle.getAssignmentsOfMoodleCourse(id);

            return new ResponseEntity(assignments, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            return new ResponseEntity("ERROR", HttpStatus.OK);
        }


    }

    @HystrixCommand(fallbackMethod = "getDefaultMoodleStuff")
    private Object getMoodleStuff() {
        return null;
    }

    private Object getDefaultMoodleStuff() {
        return null;
    }

}
