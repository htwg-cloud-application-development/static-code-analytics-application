package de.htwg.konstanz.cloud.service;

import com.amazonaws.util.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Local wrapper for the database microservice
 */
@Service
public class DatabaseService {


    // inject utility class for performing requests
    @Autowired
    private GovernanceUtil util;

    /**
     * get all groups from database
     * @return all groups
     * @throws InstantiationException if service is not avaliable
     */
    public String getAllGroups() throws InstantiationException {
        // call database service and return result
        return util.getFromService("/groups", "mongo");
    }


    /**
     * Get a group by id
     * @param groupId id of the group
     * @return a single group
     * @throws InstantiationException if service is not avaliable
     */
    public String getGroupWithId(String groupId) throws InstantiationException {
        // call database service and return result
        return util.getFromService("/groups/" + groupId, "mongo");
    }

    /**
     * Get all courses from database
     * @return all courses
     * @throws InstantiationException if service is not avaliable
     */
    public String getAllCourses() throws InstantiationException {
        // call database service and return result
        return util.getFromService("/courses", "mongo");
    }

    /**
     * Get a certain course
     * @param courseId id of course
     * @return a single course by id
     * @throws InstantiationException if service is not avaliable
     */
    public String getCourseWithId(String courseId) throws InstantiationException {
        // call database service and return result
        return util.getFromService("/courses/" + courseId, "mongo");
    }


    /**
     * Save groups to a certain course
     * @param courseid the id of the course
     * @param groups groups to save
     * @return result
     * @throws InstantiationException if service is not avaliable
     */
    public String saveGroups(Integer courseid, String groups) throws InstantiationException {
        String url = "/groups/" + courseid;
        // call database service and return result
        return util.postToService(url, groups, "mongo");
    }


    /**
     * Save a user to database
     * @param user the user to save
     * @return result
     * @throws InstantiationException if service is not avaliable
     */
    public String saveUser(JSONObject user) throws InstantiationException {
        // call database service and return result
        return util.postToService("/user/add", user.toString(), "mongo");
    }

    /**
     * Save a course to database
     * @param userId the id of the user
     * @param course the course which belongs to the user
     * @return result
     * @throws InstantiationException if service is not avaliable
     */
    public String saveCourse(Integer userId, JSONObject course) throws InstantiationException {
        String url = "/courses/" + userId;
        // call database service and return result
        return util.postToService(url, course.toString(), "mongo");
    }

}

