package de.htwg.konstanz.cloud.service;

import de.htwg.konstanz.cloud.model.Assignment;
import de.htwg.konstanz.cloud.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/assignment")
public class AssignmentService {

    @Autowired
    CourseRepository courseRepo;

    @Autowired
    AssignmentRepository assignmentRepo;

    //creates an Assignment and matches it give Course
    //if no course found return NO_CONTENT
    @RequestMapping(path = "/{courseId}", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity create(@PathVariable final String courseId, @RequestBody final Assignment assignment) throws NoSuchFieldException {

        ResponseEntity responseEntity;
        final Course course = courseRepo.findOne(courseId);
        //if no course found
        if (null == course) {
            responseEntity = new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {

            assignmentRepo.save(assignment);
            List<Assignment> assignments = course.getAssignments();

            //if no previous assignments on course create ArrayList
            if (null == assignments) {
                assignments = new ArrayList<>();
            }
            assignments.add(assignment);
            course.setAssignments(assignments);
            courseRepo.save(course);

            responseEntity = new ResponseEntity(HttpStatus.OK);
        }return responseEntity;
    }

    //returns search Assigmnet according to ID
    @RequestMapping(path = "/{assignmentId}", method = RequestMethod.GET)
    public ResponseEntity<Assignment> getAssignment(@PathVariable final String assignmentId) {
        return new ResponseEntity<Assignment>(assignmentRepo.findOne(assignmentId), HttpStatus.OK);
    }
}
