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

    /**
     * Consumes a list of groups and either updates or creates entries
     * @param groups new or updated groups
     * @param courseId course which holds the gorups
     * @return
     */
    @RequestMapping(path = "/{courseId}", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity create(@RequestBody List<Group> groups, @PathVariable final String courseId) {
        ResponseEntity responseEntity;

        /** find course **/
        Course course = courseRepo.findOne(courseId);

        /** don't save empty groups or if no course is found **/
        if (groups.isEmpty() || null == course) {
            responseEntity = new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        /** course found and group list not empty **/
        else {

            /** iterate over group entries **/
            for (Group group: groups){

                /** querry for savedGroup that is already persisted and is the same as new group**/
                /** then we only need to update **/
                Query query = new Query();
                query.addCriteria(Criteria.where("id").is(group.getId()));
                Group savedGroup = mongo.findOne(query, Group.class);

                /** if match on db is found, just update group **/
                /** don't update pmd and checkstyle **/
                /** pmd and checkstyle are updated over different route **/
                if (savedGroup != null){
                    savedGroup.setAttemptnumber(group.getAttemptnumber());
                    savedGroup.setRepository(group.getRepository());
                    savedGroup.setStatus(group.getStatus());
                    savedGroup.setTimecreated(group.getTimecreated());
                    savedGroup.setTimemodified(group.getTimemodified());
                    /** save updates **/
                    groupRepository.save(savedGroup);

                /** no match found, group is new entry **/
                } else {

                    /** persist new group **/
                    groupRepository.save(group);

                    /** if group is first entry fpr course, first a new array has to be created **/
                    /** array with group has to be set to course **/
                    if (course.getGroups() == null){
                        List<Group> initGroupList = new ArrayList<>();
                        initGroupList.add(group);
                        course.setGroups(initGroupList);

                    /** add new group to course **/
                    } else {
                        course.getGroups().add(group);
                    }
                    /** save updates **/
                    courseRepo.save(course);
                }
            }
            responseEntity = new ResponseEntity(HttpStatus.OK);
        }
        return responseEntity;
    }

    /**
     * Returns group entry belongig to this id
     * @param userId id of group to return
     * @return group
     */
    @RequestMapping(value = "{userId}", method = RequestMethod.GET)
    public ResponseEntity<Group> getGroup(@PathVariable final String userId) {
        return new ResponseEntity<Group>(groupRepository.findOne(userId), HttpStatus.OK);
    }
}
