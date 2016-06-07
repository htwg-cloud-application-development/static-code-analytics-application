package de.htwg.konstanz.cloud.service;

import de.htwg.konstanz.cloud.model.Assignment;
import de.htwg.konstanz.cloud.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping(path ="/{courseId}", method = RequestMethod.POST, consumes = "application/json")
    public void create(@PathVariable String courseId, @RequestBody Assignment assignment) throws NoSuchFieldException{

        Course course = courseRepo.findOne(courseId);
        if(null == course){
            throw new NoSuchFieldException("Course not found");
        }

        assignmentRepo.save(assignment);

        List<Assignment> assignments = course.getAssignments();
        if(null == assignments){
            assignments = new ArrayList<>();
        }

        assignments.add(assignment);
        course.setAssignments(assignments);
        courseRepo.save(course);
    }
}
