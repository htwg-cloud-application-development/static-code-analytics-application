package de.htwg.konstanz.cloud.service;

import de.htwg.konstanz.cloud.model.Course;
import de.htwg.konstanz.cloud.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/group")
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private CourseRepository courseRepo;

    @RequestMapping(path = "/{courseId}", method = RequestMethod.POST, consumes = "application/json")
    public void create(@RequestBody Group group, @PathVariable String courseId)throws NoSuchFieldException {

        Course course = courseRepo.findOne(courseId);
        if(null == course){
            throw new NoSuchFieldException("Course not found");
        }

        groupRepository.save(group);

        List<Group> groups = course.getGroups();
        if(null == groups){
            groups = new ArrayList<>();
        }

        groups.add(group);
        course.setGroups(groups);
        courseRepo.save(course);
    }


    @RequestMapping(value = "{groupId}", method = RequestMethod.GET)
    public Group getGroup(@PathVariable String groupId) {

        return groupRepository.findOne(groupId);
    }
}
