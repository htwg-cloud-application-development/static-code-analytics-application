package de.htwg.konstanz.cloud.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import de.htwg.konstanz.cloud.model.CheckstyleResults;
import de.htwg.konstanz.cloud.model.Course;
import de.htwg.konstanz.cloud.model.Group;
import de.htwg.konstanz.cloud.model.User;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseService {

    @Autowired
    private MongoOperations mongo;

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
    public Course getCourse(@PathVariable String courseId) throws IOException {

        Course course = courseRepo.findOne(courseId);
/*
        JSONObject jcourse = new JSONObject(course);
        JSONObject groups = jcourse.getJSONObject("groups");
        groups.getJSONObject("checkstyle").remove("assignments");

        System.out.println(groups);

        /*
        ObjectMapper mapper = new ObjectMapper();
        String courseString = mapper.writeValueAsString(course);
        JsonNode node = mapper.readTree(courseString);

        JsonNode groups = node.get("groups");
        JsonNode checkstyle = groups.get("checkstyleId");
        System.out.println(checkstyle);

        ((ObjectNode) checkstyle).remove("assignments");


        System.out.println(node);
*/
        return courseRepo.findOne(courseId);
    }

    //Returns all courses
    @RequestMapping(value = "/courses", method = RequestMethod.GET)
    public List<Course> getAllCourses(){

        return courseRepo.findAll();
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
