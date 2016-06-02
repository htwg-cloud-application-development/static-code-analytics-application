package de.htwg.konstanz.cloud.moodle;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.htwg.konstanz.cloud.models.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by steffen on 27/05/16.
 */
public class Moodle {

    private RestTemplate templ = new RestTemplate();

    private String token;
    private String MOODLE_BASE_URL = "https://moodle.htwg-konstanz.de/moodle/webservice/rest/server.php?" +
            "&moodlewsrestformat=json";

    public Moodle(String token) {
        this.token = token;
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

        final String service = "core_course_get_contents";
        final String requestURL = MOODLE_BASE_URL + "&wsfunction=" + service + "&courseid=" + courseId;

        JsonNode course = templ.getForObject(requestURL, JsonNode.class);

        return findAssignmentsInCourse(course);
    }

    public List<MoodleSubmissionOfAssignmet> getSubmissionsOfAssignment(Integer assignmentId) throws JsonProcessingException {

        final String service = "mod_assign_get_submissions";
        final String requestURL = MOODLE_BASE_URL + "&wsfunction=" + service + "&assignmentids[0]=" + 2045;

        JsonNode assignment = templ.getForObject(requestURL, JsonNode.class);

        if (!assignment.has(0)) {
            return Collections.emptyList();
        }

        return findSubmissionsInAssignment(assignment.get(0));

    }


    private List<MoodleAssignment> findAssignmentsInCourse(JsonNode course) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        List<MoodleAssignment> assignments = new ArrayList<>();

        // iterate over all course parts
        for (JsonNode coursePart : course) {
            JsonNode modules = coursePart.findPath("modules");


            // iterate over all modules of that part
            for (JsonNode module : modules) {

                // if module is an assignment
                if ("assign".equals(module.findPath("modname").asText())) {
                    assignments.add(mapper.treeToValue(module, MoodleAssignment.class));
                }
            }
        }
        return assignments;
    }

    private List<MoodleSubmissionOfAssignmet> findSubmissionsInAssignment(JsonNode assignment) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();


        JsonNode submissions = assignment.findPath("submissions");

        if (submissions.size() < 1) {
            return Collections.emptyList();
        }

        List<MoodleSubmissionOfAssignmet> returnSubmissions = new ArrayList<>();
        for (JsonNode singleSubmission : submissions) {
            returnSubmissions.add(mapper.treeToValue(singleSubmission, MoodleSubmissionOfAssignmet.class));
        }

        return returnSubmissions;

    }

}
