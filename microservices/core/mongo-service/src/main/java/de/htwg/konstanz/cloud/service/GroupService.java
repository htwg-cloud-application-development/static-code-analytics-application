package de.htwg.konstanz.cloud.service;

import de.htwg.konstanz.cloud.model.Course;
import de.htwg.konstanz.cloud.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Circle;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/groups")
public class GroupService {

    @Autowired
    private MongoOperations mongo;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private CourseRepository courseRepo;

    /** Consumes a set of groups and updates/creates the entries **/
    /** Associates the set of gorups with the {courseId} **/
    @RequestMapping(path = "/{courseId}", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity create(@RequestBody List<Group> groups, @PathVariable final String courseId) {
        ResponseEntity responseEntity;
        /** find course **/
        Course course = courseRepo.findOne(courseId);
        System.out.print("Course: " + course);

        /** don't save empty groups or if no course is found **/
        if (groups.isEmpty() || null == course) {
            responseEntity = new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        /** course found and group set not empty **/
        else {

            for (Group group: groups){

                Query query = new Query();
                query.addCriteria(Criteria.where("id").is(group.getId()));

                Group savedGroup = mongo.findOne(query, Group.class);

                if (savedGroup != null){
                    savedGroup.setAttemptnumber(group.getAttemptnumber());
                    savedGroup.setRepository(group.getRepository());
                    savedGroup.setStatus(group.getStatus());
                    savedGroup.setTimecreated(group.getTimecreated());
                    savedGroup.setTimemodified(group.getTimemodified());
                    groupRepository.save(savedGroup);
                } else {

                    groupRepository.save(group);

                    if (course.getGroups() == null){
                        List<Group> initGroupList = new ArrayList<>();
                        initGroupList.add(group);
                        course.setGroups(initGroupList);
                    } else {
                        course.getGroups().add(group);
                    }
                    courseRepo.save(course);
                }
            }
            responseEntity = new ResponseEntity(HttpStatus.OK);
        }
        return responseEntity;
    }

    // return group with associated userId
    @RequestMapping(value = "{userId}", method = RequestMethod.GET)
    public ResponseEntity<Group> getGroup(@PathVariable final String userId) {
        return new ResponseEntity<Group>(groupRepository.findOne(userId), HttpStatus.OK);
    }
}
