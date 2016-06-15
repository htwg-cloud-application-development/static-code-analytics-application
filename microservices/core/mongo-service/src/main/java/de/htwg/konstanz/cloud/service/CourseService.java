package de.htwg.konstanz.cloud.service;

import de.htwg.konstanz.cloud.model.Course;
import de.htwg.konstanz.cloud.model.Group;
import de.htwg.konstanz.cloud.model.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private CourseRepository courseRepo;

    @RequestMapping(path = "/{userId}", method = RequestMethod.POST, consumes = "application/json")
    public void create(@RequestBody Course course, @PathVariable String userId) throws NoSuchFieldException {

        User user = userRepo.findOne(userId);
        if (null == user) {
            throw new NoSuchFieldException("User not found");
        }
        courseRepo.save(course);

        List<Course> courses = user.getCourses();
        if (null == courses) {
            courses = new ArrayList<>();
        }

        courses.add(course);
        user.setCourses(courses);
        userRepo.save(user);
    }

    //Returns one course without assignements of pmd and checkstyle
    @RequestMapping(value = "/{courseId}", method = RequestMethod.GET)
    public String getCourse(@PathVariable String courseId) throws IOException {

        Course course = courseRepo.findOne(courseId);
        return removeAssignments(course).toString();
    }

    //Returns all courses without assingments of pmd and checkstyle
    @RequestMapping(method = RequestMethod.GET)
    public String getAllCourses() {

        List<Course> courses = courseRepo.findAll();
        List<JSONObject> jsonObjects = new LinkedList<>();

        for (Course course : courses) {
            jsonObjects.add(removeAssignments(course));
        }

        return jsonObjects.toString();
    }


    //Returns all groups to matching courseId
    @RequestMapping(value = "/groups/{courseId}", method = RequestMethod.GET)
    public List<Group> getGroups(@PathVariable String courseId) throws NoSuchFieldException {

        Course course = courseRepo.findOne(courseId);
        if (null == course) {
            throw new NoSuchFieldException("Course not found");
        }

        return course.getGroups();
    }

    public JSONObject removeAssignments(Course course) {

        //Remove assignments from checkstyle and pmd
        JSONObject jCourse = new JSONObject(course);

        if (jCourse.has("groups")) {

            JSONArray groups = jCourse.getJSONArray("groups");

            for (int i = 0; i < groups.length() - 1; i++) {

                JSONObject group = groups.getJSONObject(i);

                if (group.has("pmd")) {
                    JSONObject pmd = group.getJSONObject("pmd");
                    if (pmd.has("assignments")) {
                        pmd.remove("assignments");
                    }
                }

                if (group.has("checkstyle")) {
                    JSONObject checkstyle = group.getJSONObject("checkstyle");
                    if (checkstyle.has("assignments")) {
                        checkstyle.remove("assignments");
                    }
                }
            }
        }

        return jCourse;
    }
}
