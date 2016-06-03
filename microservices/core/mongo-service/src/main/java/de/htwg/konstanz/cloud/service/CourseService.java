package de.htwg.konstanz.cloud.service;

import de.htwg.konstanz.cloud.model.Course;
import de.htwg.konstanz.cloud.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private CourseRepository courseRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<Course> getAll() {
        return courseRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public Course create(@RequestBody Course course) {
        return courseRepository.save(course);
    }

    @RequestMapping(path = "/{courseId}/groups", method = RequestMethod.POST, consumes = "application/json")
    public Group create(@PathVariable String courseId, @RequestBody Group group) throws NoSuchFieldException {
        Course course = courseRepository.findOne(courseId);
        if(null == course) {
            throw new NoSuchFieldException("Course not available - create course first");
        }
        Group newGroup = groupRepository.save(group);
        List<Group> allGroups = course.getGroups();
        if(null == allGroups){
            allGroups = new ArrayList<>();
        }
        allGroups.add(group);
        course.setGroups(allGroups);
        courseRepository.save(course);
        return newGroup;
    }

    @RequestMapping(value = "{courseId}", method = RequestMethod.GET)
    public Course getCourses(@PathVariable String courseId) {
        return courseRepository.findOne(courseId);
    }

    @RequestMapping(value = "{courseId}", method = RequestMethod.PUT, consumes = "application/json")
    public Course update(@PathVariable String courseId, @RequestBody Course course) {
        Course update = courseRepository.findOne(courseId);
        update.setName(course.getName());
        return courseRepository.save(update);
    }

    @RequestMapping(value = "{courseId}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String courseId) {
        courseRepository.delete(courseId);
    }
}
