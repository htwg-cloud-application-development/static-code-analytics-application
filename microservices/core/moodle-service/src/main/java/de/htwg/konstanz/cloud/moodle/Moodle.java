package de.htwg.konstanz.cloud.moodle;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.htwg.konstanz.cloud.models.*;
import org.jsoup.Jsoup;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Moodle {

    private final RestTemplate templ = new RestTemplate();

    private String MOODLE_BASE_URL = "https://moodle.htwg-konstanz.de/moodle/webservice/rest/server.php?" +
            "&moodlewsrestformat=json";

    public Moodle(String token) {
        this.MOODLE_BASE_URL += "&wstoken=" + token;
    }


    public GeneralMoodleInfo getMoodleInfoFromMoodleToken() {

        String service = "core_webservice_get_site_info";
        String requestURL = MOODLE_BASE_URL + "&wsfunction=" + service;

        GeneralMoodleInfo moodleInfo = templ.getForObject(requestURL, GeneralMoodleInfo.class);


        return moodleInfo;
    }

    public List<MoodleCourse> getCoursesOfMoodleUser(Integer userId) throws JsonProcessingException {

        String service = "core_enrol_get_users_courses";
        String requestURL = MOODLE_BASE_URL + "&wsfunction=" + service + "&userid=" + userId;

        JsonNode courses = templ.getForObject(requestURL, JsonNode.class);


        ObjectMapper mapper = new ObjectMapper();
        List<MoodleCourse> moodleCourses = new ArrayList<>();
        for (JsonNode course : courses) {
            moodleCourses.add(mapper.treeToValue(course, MoodleCourse.class));
        }


        return moodleCourses;
    }

    public List<MoodleAssignment> getAssignmentsOfMoodleCourse(Integer courseId) throws JsonProcessingException {

        final String service = "mod_assign_get_assignments";
        final String requestURL = MOODLE_BASE_URL + "&wsfunction=" + service + "&courseids[0]=" + courseId;

        JsonNode course = templ.getForObject(requestURL, JsonNode.class);

        return findAssignmentsInCourse(course);
    }

    public List<MoodleSubmissionOfAssignmet> getSubmissionsOfAssignment(Integer assignmentId) throws JsonProcessingException {

        final String service = "mod_assign_get_submissions";
        final String requestURL = MOODLE_BASE_URL + "&wsfunction=" + service + "&assignmentids[0]=" + assignmentId;

        JsonNode assignment = templ.getForObject(requestURL, JsonNode.class).findPath("assignments");

        if (assignment.size() < 1) {
            return Collections.emptyList();
        }

        return findSubmissionsInAssignment(assignment.get(0));

    }


    public Boolean hasPermissionOnCourse(String courseId, Integer userId) {
        final String service = "core_enrol_get_enrolled_users";
        final String requestURL = MOODLE_BASE_URL + "&wsfunction=" + service + "&courseid=" + courseId;

        JsonNode enrolledUser = templ.getForObject(requestURL, JsonNode.class);

        Boolean hasPermission = false;

        for (JsonNode user : enrolledUser) {

            // check on userid
            if (userId == user.findPath("id").asInt()) {

                // user found --> check on permission
                JsonNode roles = user.findPath("roles");


                // iterate over roles of user in that course
                for (JsonNode role : roles) {

                    if ("editingteacher".equals(role.findPath("shortname").asText())) {
                        return true;
                    }
                }

                // user found, but has no permission
                return false;
            }


        }

        // user not found in course
        return false;
    }


    private List<MoodleAssignment> findAssignmentsInCourse(JsonNode course) throws JsonProcessingException {

        JsonNode jsonCourse = course.findPath("courses");

        if (jsonCourse.size() < 1) {
            return Collections.emptyList();
        }
        JsonNode jsonAssignments = jsonCourse.elements().next().findPath("assignments");

        ObjectMapper mapper = new ObjectMapper();
        List<MoodleAssignment> assignments = new ArrayList<>();

        // iterate over all assignments
        for (JsonNode assign : jsonAssignments) {
            assignments.add(mapper.treeToValue(assign, MoodleAssignment.class));
        }

        return assignments;
    }

    private List<MoodleSubmissionOfAssignmet> findSubmissionsInAssignment(JsonNode assignment) throws JsonProcessingException {


        JsonNode moodleSubmissions = assignment.findPath("submissions");

        if (moodleSubmissions.size() < 1) {
            return Collections.emptyList();
        }

        ObjectMapper mapper = new ObjectMapper();

        List<MoodleSubmissionOfAssignmet> submissions = new ArrayList<>();
        for (JsonNode singleSubmission : moodleSubmissions) {

            // create pojo
            MoodleSubmissionOfAssignmet tempSubmission = mapper.treeToValue(singleSubmission, MoodleSubmissionOfAssignmet.class);

            // find repo url and add it to pojo
            tempSubmission.setRepository(findRepoInSubmission(singleSubmission));

            // add pojo to list
            submissions.add(tempSubmission);
        }

        return submissions;

    }

    private String findRepoInSubmission(JsonNode singleSubmission) {

        JsonNode plugins = singleSubmission.findPath("plugins");

        if (plugins.size() < 1) {
            return "";
        }

        for (JsonNode plugin : plugins) {

            if ("onlinetext".equals(plugin.findPath("type").asText())) {
                JsonNode editorfields = plugin.findPath("editorfields");

                if (editorfields.size() < 1) {
                    return "";
                }

                for (JsonNode field : editorfields) {
                    JsonNode actualSubmit = field.findPath("text");

                    if (!actualSubmit.isMissingNode()) {
                        String repo = Jsoup.parse(actualSubmit.asText()).text();

                        // remove last dash if present
                        if (repo.endsWith("/")) {
                            repo = repo.substring(0, repo.length() - 1);

                        }
                        
                        return repo;
                    }
                }

            }
        }
        return "";
    }


}
