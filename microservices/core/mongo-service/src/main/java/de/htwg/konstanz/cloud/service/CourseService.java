package de.htwg.konstanz.cloud.service;

import de.htwg.konstanz.cloud.model.Course;
import de.htwg.konstanz.cloud.model.Group;
import de.htwg.konstanz.cloud.model.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseService {

    @Autowired
    private MongoOperations mongo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private CourseRepository courseRepo;

    /**
     * saves a course on database
     * links course to userId
     *
     * @param course course to save
     * @param userId user who holds the course
     * @return HttpStatus
     */
    @RequestMapping(path = "/{userId}", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity create(@RequestBody final Course course, @PathVariable final String userId) {
        ResponseEntity responseEntity;

        /** check if user exits **/
        final User user = userRepo.findOne(userId);
        if (null == user) {
            responseEntity = new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            /** user exists **/

            /** querry for savedCourse that is already persisted and is the same as new course**/
            /** then we only need to update **/
            Query query = new Query();
            query.addCriteria(Criteria.where("id").is(course.getId()));
            Course savedCourse = mongo.findOne(query, Course.class);

            /** no match found, course is new entry **/
            if (savedCourse == null) {

                /** persist new course **/
                courseRepo.save(course);

                /** if course is first entry for user, first a new array has to be created **/
                /** array with course has to be set to user **/
                if (user.getCourses() == null) {
                    List<Course> initCourseList = new ArrayList<>();
                    initCourseList.add(course);
                    user.setCourses(initCourseList);
                } else {

                    /** add new course to user **/
                    user.getCourses().add(course);
                }
                /** update user **/
                userRepo.save(user);

            }

            /** if match on db is found, just update course **/
            /** don't groups **/
            /** groups are updated over different route **/
            else {

                savedCourse.setEnrolledusercount(course.getEnrolledusercount());
                savedCourse.setFullname(course.getFullname());
                savedCourse.setIdnumber(course.getIdnumber());
                savedCourse.setShortname(course.getShortname());
                savedCourse.setVisible(course.getVisible());
                /** save updates **/
                courseRepo.save(savedCourse);
            }

            responseEntity = new ResponseEntity(HttpStatus.OK);
        }
        return responseEntity;
    }

    /**
     * find course without attached errors of pmd and checkstyle
     *
     * @param courseId id which course to be returned
     * @return course without errors in pmd and checkstyle
     */
    @RequestMapping(value = "/{courseId}", method = RequestMethod.GET)
    public ResponseEntity<String> getCourse(@PathVariable final String courseId) {

        /** find course **/
        final Course course = courseRepo.findOne(courseId);
        /** return course without errors of pmd and checkstyle **/
        return new ResponseEntity<String>(removeErrors(course).toString(), HttpStatus.OK);
    }

    /**
     * finds all courses without errors of pmd and checkstyle
     *
     * @return all courses without errors
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> getAllCourses() {

        final List<Course> courses = courseRepo.findAll();
        final List<JSONObject> jsonObjects = new LinkedList<>();

        for (final Course course : courses) {
            jsonObjects.add(removeErrors(course));
        }
        return new ResponseEntity<String>(jsonObjects.toString(), HttpStatus.OK);
    }


    //Returns all groups to matching courseId

    /**
     * returns all groups to matching coursId
     * @param courseId id of course
     * @return list of groups matching coursId
     */

    @RequestMapping(value = "/groups/{courseId}", method = RequestMethod.GET)
    public ResponseEntity<List<Group>> getGroups(@PathVariable final String courseId) {

        ResponseEntity<List<Group>> responseEntity;
        final Course course = courseRepo.findOne(courseId);
        if (null == course) {
            responseEntity = new ResponseEntity<List<Group>>(HttpStatus.NO_CONTENT);
        } else {
            responseEntity = new ResponseEntity<List<Group>>(course.getGroups(), HttpStatus.OK);
        }
        return responseEntity;
    }

    /**
     * Converts course to JSON
     * Navigates to groups.pmd.assigments and groups.checkstyle.assignments
     * In assignments invokes removeErrorsInAssignment()
     *
     * @param course course where errors should be removed
     * @return course with removed errors
     */

    public JSONObject removeErrors(final Course course) {

        /** o to assignments from checkstyle and pmd **/
        final JSONObject jCourse = new JSONObject(course);
        final String assignments = "assignments";

        if (jCourse.has("groups")) {

            final JSONArray groups = jCourse.getJSONArray("groups");
            JSONObject group;

            for (int i = 0; i < groups.length(); i++) {

                group = groups.getJSONObject(i);

                /** checks if group has pmd result **/
                if (group.has("pmd")) {
                    JSONObject pmd = group.getJSONObject("pmd");
                    if (pmd.has(assignments)) {
                        removeErrorsInAssignments(pmd);
                    }

                }
                /** checks if group has checkstyle result **/
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

    /**
     * Removes extensive error descriptions
     * Consumes JSON from course structure navigated to assignments of pmd or checkstyle
     * @param analysisResult JSON from course navigated to assignments
     */

    public void removeErrorsInAssignments(JSONObject analysisResult) {

        /** gets assignments **/
        JSONArray assignments = analysisResult.getJSONArray("assignments");

        for (int i = 0; i < assignments.length(); i++) {
            JSONObject assignment = assignments.getJSONObject(i);

            /** getting name of "key", which is assignment name **/
            String[] assignmentName = JSONObject.getNames(assignment);

            /** getting array under assignment name **/
            JSONArray files = assignment.getJSONArray(assignmentName[0]);

            /** iterating over "key" array and remove errors **/
            for (int o = 0; o < files.length(); o++) {
                JSONObject file = files.getJSONObject(o);
                file.remove("errors");
            }
        }
    }
}
