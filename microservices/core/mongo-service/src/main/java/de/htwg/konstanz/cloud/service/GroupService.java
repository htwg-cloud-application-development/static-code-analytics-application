package de.htwg.konstanz.cloud.service;

import de.htwg.konstanz.cloud.model.Course;
import de.htwg.konstanz.cloud.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/groups")
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<Group> getAll() {
        return groupRepository.findAll();
    }

    @RequestMapping(value = "{groupId}", method = RequestMethod.GET)
    public Group getGroup(@PathVariable String groupId) {
        return groupRepository.findOne(groupId);
    }

    @RequestMapping(value = "{groupId}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String groupId) {
        groupRepository.delete(groupId);
    }
}
