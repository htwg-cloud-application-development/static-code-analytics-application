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

        /** don't save empty groups or if no course is found **/
        if (groups.isEmpty() || null == course) {
            responseEntity = new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        /** course found and group set not empty **/
        else {
            /** get already stored groups of course **/
            final List<Group> storedDbGroups = course.getGroups();
            System.out.println(storedDbGroups);

            /** if course doesn't have groups, save all groups from RequestBody**/
            if (null == storedDbGroups) {
                groupRepository.save(groups);
                /** associate groups with course **/
                course.setGroups(groups);
                /** save course with new associated groups **/
                courseRepo.save(course);
            }
            /** if course has already associatd groups **/
            else {

                for (Group newGroup: groups){
                    boolean matchFound = false;

                    for (Group storedGroup: storedDbGroups){
                        if (newGroup.equals(storedGroup)){
                            matchFound = true;
                            Query query = new Query();
                            query.addCriteria(Criteria.where("id").is(newGroup.getId()));

                            Update update = new Update();

                            update.set("attemptnumber", newGroup.getAttemptnumber());
                            update.set("repository", newGroup.getRepository());
                            update.set("status", newGroup.getStatus());
                            update.set("timecreated", newGroup.getTimecreated());
                            update.set("timemodified", newGroup.getTimemodified());
                            update.set("pmd", storedGroup.getPmd());
                            update.set("checkstyle", storedGroup.getCheckstyle());

                            mongo.upsert(query, update, Group.class);
                        }

                    }

                    if (!matchFound){
                        groupRepository.save(newGroup);
                        course.getGroups().add(newGroup);
                        courseRepo.save(course);
                    }
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
