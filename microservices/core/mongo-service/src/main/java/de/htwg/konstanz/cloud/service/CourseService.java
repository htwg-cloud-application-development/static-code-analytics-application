package de.htwg.konstanz.cloud.service;

import de.htwg.konstanz.cloud.model.Course;
import de.htwg.konstanz.cloud.model.Group;
import de.htwg.konstanz.cloud.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private CourseRepository courseRepo;

    @RequestMapping(path = "/{userId}", method = RequestMethod.POST, consumes = "application/json")
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
        user.setCourses(courses);
        userRepo.save(user);
    }

    @RequestMapping(value = "/{courseId}", method = RequestMethod.GET)
    public Course getCourse(@PathVariable String courseId){

        return courseRepo.findOne(courseId);
    }

    //Returns all groups to matching courseId
    @RequestMapping(value = "/groups/{courseId}", method = RequestMethod.GET)
    public List<Group> getGroups(@PathVariable String courseId) throws NoSuchFieldException{

        Course course = courseRepo.findOne(courseId);
        if (null == course){
            throw new NoSuchFieldException("Course not found");
        }

        return course.getGroups();
    }
}
