package de.htwg.konstanz.cloud.service;

import com.amazonaws.util.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatabaseService {


    @Autowired
    private GovernanceUtil util;

    public String getAllGroups() throws InstantiationException {
        return callDatabaseFor("/groups");
    }


    public String getGroupWithId(String groupId) throws InstantiationException {
        return callDatabaseFor("/groups/" + groupId);
    }

    public String getAllCourses() throws InstantiationException {
        return callDatabaseFor("/courses");
    }

    public String getCourseWithId(String courseId) throws InstantiationException {
        return callDatabaseFor("/courses/" + courseId);
    }


    public String saveGroups(Integer courseid, String groups) throws InstantiationException {
        String url = "/groups/" + courseid;
        return postDataBaseFor(url, groups);
    }


    public String saveUser(JSONObject user) throws InstantiationException {
        return postDataBaseFor("/user/add", user.toString());
    }

    public String saveCourse(Integer userId, JSONObject course) throws InstantiationException {
        String url = "/courses/" + userId;
        return postDataBaseFor(url, course.toString());
    }


    private String postDataBaseFor(String route, String data) throws InstantiationException {
        return util.postToService(route, data, "mongo");
    }


    private String callDatabaseFor(String route) throws InstantiationException {
        return util.getFromService(route, "mongo");
    }
}

