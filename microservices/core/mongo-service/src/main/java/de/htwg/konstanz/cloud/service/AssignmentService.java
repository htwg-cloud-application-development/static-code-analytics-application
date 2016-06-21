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

    @RequestMapping(path = "/{courseId}", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity create(@PathVariable final String courseId, @RequestBody final Assignment assignment) throws NoSuchFieldException {

        final Course course = courseRepo.findOne(courseId);
        if (null == course) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        assignmentRepo.save(assignment);

        List<Assignment> assignments = course.getAssignments();
        if (null == assignments) {
            assignments = new ArrayList<>();
        }

        assignments.add(assignment);
        course.setAssignments(assignments);
        courseRepo.save(course);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(path = "/{assignmentId}", method = RequestMethod.GET)
    public ResponseEntity<Assignment> getAssignment(@PathVariable final String assignmentId) {
        return new ResponseEntity<Assignment>(assignmentRepo.findOne(assignmentId), HttpStatus.OK);
    }
}
