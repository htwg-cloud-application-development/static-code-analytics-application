package de.htwg.konstanz.cloud.service;

import de.htwg.konstanz.cloud.model.Course;
import de.htwg.konstanz.cloud.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groups")
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private CourseRepository courseRepo;

    @RequestMapping(path = "/{courseId}", method = RequestMethod.POST, consumes = "application/json")
    public void create(@RequestBody List<Group> groups, @PathVariable String courseId) throws NoSuchFieldException {

        System.out.println(groups);
        if (groups.isEmpty()) { // don't save empty groups
            System.out.println("no groups, return");
            return;
        }


        Course course = courseRepo.findOne(courseId);
        if (null == course) {
            throw new NoSuchFieldException("Course not found");
        }

        List<Group> dbGroups = course.getGroups();

        if (null != dbGroups) {
            //delete groups
            groupRepository.delete(dbGroups);

        }

        groupRepository.save(groups);

        course.setGroups(groups);
        courseRepo.save(course);
    }


    @RequestMapping(value = "{groupId}", method = RequestMethod.GET)
    public Group getGroup(@PathVariable String groupId) {

        return groupRepository.findOne(groupId);
    }
}
