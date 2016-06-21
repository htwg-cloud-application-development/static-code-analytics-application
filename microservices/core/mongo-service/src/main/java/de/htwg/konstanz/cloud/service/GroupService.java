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
    public void create(@RequestBody final List<Group> groups, @PathVariable final String courseId) throws NoSuchFieldException {

        if (groups.isEmpty()) { // don't save empty groups
            return;
        }


        final Course course = courseRepo.findOne(courseId);
        if (null == course) {
            throw new NoSuchFieldException("Course not found");
        }

        final List<Group> dbGroups = course.getGroups();

        //System.out.println("____________ " + dbGroups);

        if (null != dbGroups) {
            //delete groups
            groupRepository.delete(dbGroups);
        }

        groupRepository.save(groups);

        course.setGroups(groups);
        courseRepo.save(course);
    }


    @RequestMapping(value = "{userId}", method = RequestMethod.GET)
    public Group getGroup(@PathVariable final String userId) {

        return groupRepository.findOne(userId);
    }
}
