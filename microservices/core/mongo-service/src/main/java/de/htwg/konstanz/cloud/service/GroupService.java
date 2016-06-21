package de.htwg.konstanz.cloud.service;

import de.htwg.konstanz.cloud.model.Course;
import de.htwg.konstanz.cloud.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groups")
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private CourseRepository courseRepo;

    //create group to matching course
    @RequestMapping(path = "/{courseId}", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity create(@RequestBody final List<Group> groups, @PathVariable final String courseId) throws NoSuchFieldException {

        // don't save empty groups
        if (groups.isEmpty()) {
            System.out.println("groups.isEmpty" +  groups.isEmpty());
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        final Course course = courseRepo.findOne(courseId);
        if (null == course) {
            System.out.println("null == course" +  course);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        final List<Group> dbGroups = course.getGroups();

        if (null != dbGroups) {
            //delete old groups
            groupRepository.delete(dbGroups);
        }

        groupRepository.save(groups);
        course.setGroups(groups);
        courseRepo.save(course);

        return new ResponseEntity(HttpStatus.OK);
    }

    // return group with associated userId
    @RequestMapping(value = "{userId}", method = RequestMethod.GET)
    public ResponseEntity<Group> getGroup(@PathVariable final String userId) {
        return new ResponseEntity<Group>(groupRepository.findOne(userId), HttpStatus.OK);
    }
}
