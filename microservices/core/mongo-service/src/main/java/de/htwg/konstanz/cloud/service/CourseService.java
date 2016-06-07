package de.htwg.konstanz.cloud.service;

import de.htwg.konstanz.cloud.model.Course;
import de.htwg.konstanz.cloud.model.Group;
import de.htwg.konstanz.cloud.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private CourseRepository courseRepo;

    @RequestMapping(path = "/{userId}", method = RequestMethod.PUT, consumes = "application/json")
    public void create(@RequestBody Course course, @PathVariable String userId) throws NoSuchFieldException{

        User user = userRepo.findOne(userId);
        if(null == user){
            throw new NoSuchFieldException("User not found");
        }

        courseRepo.save(course);

        List<Course> courses = user.getCourses();
        if (null == courses){
            courses = new ArrayList<>();

        }
        courses.add(course);
    }

    @RequestMapping(value = "{courseId}", method = RequestMethod.GET)
    public Course getCourse(@PathVariable String courseId){

        return courseRepo.findOne(courseId);
    }
}
