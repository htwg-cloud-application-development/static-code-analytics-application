package de.htwg.konstanz.cloud.service;

import de.htwg.konstanz.cloud.model.Course;
import de.htwg.konstanz.cloud.model.Group;
import de.htwg.konstanz.cloud.model.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity create(@RequestBody final Course course, @PathVariable final String userId) throws NoSuchFieldException {

        //check if user exits
        final User user = userRepo.findOne(userId);
        if (null == user) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        courseRepo.save(course);
        List<Course> courses = user.getCourses();

        if (null == courses) {
            courses = new ArrayList<>();
        }

        courses.add(course);
        user.setCourses(courses);
        userRepo.save(user);
        return new ResponseEntity(HttpStatus.OK);
    }

    //Returns one course without errors of pmd and checkstyle
    @RequestMapping(value = "/{courseId}", method = RequestMethod.GET)
    public ResponseEntity<String> getCourse(@PathVariable final String courseId) throws IOException {

        final Course course = courseRepo.findOne(courseId);
        return new ResponseEntity<String>(goToPmdAndCheckstyleInJson(course).toString(), HttpStatus.OK);
    }

    //Returns all courses without errors of pmd and checkstyle
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> getAllCourses() {

        final List<Course> courses = courseRepo.findAll();
        final List<JSONObject> jsonObjects = new LinkedList<>();

        for (final Course course : courses) {
            jsonObjects.add(goToPmdAndCheckstyleInJson(course));
        }
        return new ResponseEntity<String>(jsonObjects.toString(), HttpStatus.OK);
    }


    //Returns all groups to matching courseId
    @RequestMapping(value = "/groups/{courseId}", method = RequestMethod.GET)
    public ResponseEntity<List<Group>> getGroups(@PathVariable final String courseId) throws NoSuchFieldException {

        ResponseEntity<List<Group>> responseEntity;
        final Course course = courseRepo.findOne(courseId);
        if (null == course) {
            responseEntity = new ResponseEntity<List<Group>>(HttpStatus.NO_CONTENT);
        } else {
            responseEntity = new ResponseEntity<List<Group>>(course.getGroups(), HttpStatus.OK);
        }
        return responseEntity;
    }

    //converts course to JSON
    //navigates to groups.pmd.assignments and groups.checkstyle.assignments
    //in assignments inovkes removeErrosInAssignment()
    public JSONObject goToPmdAndCheckstyleInJson(final Course course) {

        //go to assignments from checkstyle and pmd
        final JSONObject jCourse = new JSONObject(course);
        final String assignments = "assignments";

        if (jCourse.has("groups")) {

            final JSONArray groups = jCourse.getJSONArray("groups");
            JSONObject group;

            for (int i = 0; i < groups.length(); i++) {

                group = groups.getJSONObject(i);

                //checks if group has pmd result
                if (group.has("pmd")) {
                    JSONObject pmd = group.getJSONObject("pmd");
                    if (pmd.has(assignments)){
                        removeErrorsInAssignments(pmd);
                    }

                }
                //checks if group has checkstyle result
                if (group.has("checkstyle")) {
                    final JSONObject checkstyle = group.getJSONObject("checkstyle");
                    if (checkstyle.has(assignments)) {
                        removeErrorsInAssignments(checkstyle);
                    }
                }
            }
        }

        return jCourse;
    }

    //removes extensive Errors
    public void removeErrorsInAssignments(JSONObject analysisResult){

        // gets assignments
        JSONArray assignments = analysisResult.getJSONArray("assignments");

        for (int i = 0; i < assignments.length(); i++) {
            JSONObject assignment = assignments.getJSONObject(i);

            //getting name of "key", which is assignment name
            String[] assignmentName = JSONObject.getNames(assignment);

            //getting array under assignment name
            JSONArray files = assignment.getJSONArray(assignmentName[0]);

            //iterating over "key" array and remove errors
            for (int o = 0; o < files.length(); o++) {
            JSONObject file = files.getJSONObject(o);
            file.remove("errors");
            }
        }
    }
}
