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


/**
 * Wrapper for accessing moodle with REST
 */
public class Moodle {

    // rest template needed for perform requests
    private final RestTemplate templ = new RestTemplate();

    // the base url for moodle for all requests
    private final String moodleBaseUrl;

    // Object for parsing JSON values
    private final ObjectMapper mapper = new ObjectMapper();



    /**
     * Constructor for creating wrapper class with needed token
     * @param token a token from moodle needed for all requests
     */
    public Moodle(String token) {
        // construct base url with token
        this.moodleBaseUrl = "https://moodle.htwg-konstanz.de/moodle/webservice/rest/server.php?"
                + "&moodlewsrestformat=json&wstoken=" + token;
    }


    /**
     * Get all information based on a token
     * @return GeneralMoodleInfo with all needed information
     */
    public GeneralMoodleInfo getMoodleInfoFromMoodleToken() {

        // service from moodle for accessing information
        String service = "core_webservice_get_site_info";

        // generate request url on top of the base url
        String requestUrl = moodleBaseUrl + "&wsfunction=" + service;

        // perform request and obtain information from moodle
        return templ.getForObject(requestUrl, GeneralMoodleInfo.class);
    }

    /**
     * Get all courses of a user
     * @param userId the id of a user in moodle
     * @return a list with {@link MoodleCourse}
     * @throws JsonProcessingException if parsing the value fails
     */
    public List<MoodleCourse> getCoursesOfMoodleUser(Integer userId) throws JsonProcessingException {

        // service from moodle for accessing information
        String service = "core_enrol_get_users_courses";

        // generate request url on top of the base url
        String requestUrl = moodleBaseUrl + "&wsfunction=" + service + "&userid=" + userId;

        // perform request and obtain information from moodle
        JsonNode courses = templ.getForObject(requestUrl, JsonNode.class);

        // create empty list of moodle courses
        List<MoodleCourse> moodleCourses = new ArrayList<>();

        // iterate over all courses
        for (JsonNode course : courses) {

            // transfom course to java object and add it to list
            moodleCourses.add(mapper.treeToValue(course, MoodleCourse.class));
        }


        // return courses
        return moodleCourses;
    }

    /**
     * Get all assignments of a moodle course
     * @param courseId the id of a course in moodle
     * @return a list of {@link MoodleAssignment}
     * @throws JsonProcessingException if parsing the value fails
     */
    public List<MoodleAssignment> getAssignmentsOfMoodleCourse(Integer courseId) throws JsonProcessingException {

        // service from moodle for accessing information
        final String service = "mod_assign_get_assignments";

        // generate request url on top of the base url
        final String requestUrl = moodleBaseUrl + "&wsfunction=" + service + "&courseids[0]=" + courseId;

        // perform request and obtain information from moodle
        JsonNode course = templ.getForObject(requestUrl, JsonNode.class);

        // return the assignments
        return findAssignmentsInCourse(course);
    }

    /**
     * Get all submissions of a certain assignment
     * @param assignmentId id of a assignment in moodle
     * @return a list of {@link MoodleSubmissionOfAssignmet}
     * @throws JsonProcessingException if parsing the value fails
     */
    public List<MoodleSubmissionOfAssignmet> getSubmissionsOfAssignment(Integer assignmentId) throws JsonProcessingException {

        // service from moodle for accessing information
        final String service = "mod_assign_get_submissions";

        // generate request url on top of the base url
        final String requestUrl = moodleBaseUrl + "&wsfunction=" + service + "&assignmentids[0]=" + assignmentId;

        // perform request and obtain information from moodle
        JsonNode assignment = templ.getForObject(requestUrl, JsonNode.class).findPath("assignments");

        // check if there is an assignment
        if (assignment.size() < 1) {
            // return empty list
            return Collections.emptyList();
        }

        // call private method for finding submissions in assignment and return them
        return findSubmissionsInAssignment(assignment.get(0));

    }


    /**
     * Check if a user has enough permissions on a course
     * @param courseId id of a course in moodle
     * @param userId id of a user
     * @return true if the user has enough permission, false otherwise
     */
    public Boolean hasPermissionOnCourse(String courseId, Integer userId) {

        // service from moodle for accessing information
        final String service = "core_enrol_get_enrolled_users";

        // generate request url on top of the base url
        final String requestUrl = moodleBaseUrl + "&wsfunction=" + service + "&courseid=" + courseId;

        // perform request and obtain information from moodle
        JsonNode enrolledUser = templ.getForObject(requestUrl, JsonNode.class);


        // iterate over all user in given course
        for (JsonNode user : enrolledUser) {

            // check on userid
            if (userId == user.findPath("id").asInt()) {

                // user found --> check on permission
                JsonNode roles = user.findPath("roles");


                // iterate over roles of user in that course
                for (JsonNode role : roles) {

                    // check if user is of type teacher
                    if ("editingteacher".equals(role.findPath("shortname").asText())) {

                        // user has permission
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

    /**
     * Parses the course and find all assignments in that course
     * @param course a JSON presentation of a course from moodle
     * @return a list of all {@link MoodleAssignment}
     * @throws JsonProcessingException if parsing the value fails
     */
    private List<MoodleAssignment> findAssignmentsInCourse(JsonNode course) throws JsonProcessingException {

        // get the course
        JsonNode jsonCourse = course.findPath("courses");

        // check if there is a course
        if (jsonCourse.size() < 1) {

            // return empty list
            return Collections.emptyList();
        }

        // find the assignment property
        JsonNode jsonAssignments = jsonCourse.elements().next().findPath("assignments");

        // create empty list of assignments
        List<MoodleAssignment> assignments = new ArrayList<>();

        // iterate over all assignments
        for (JsonNode assign : jsonAssignments) {

            // parse assignment to java object and add it to list
            assignments.add(mapper.treeToValue(assign, MoodleAssignment.class));
        }

        // return all found assignments
        return assignments;
    }

    /**
     * Parses a JSON assignment from moodle and finds the submissions
     * @param assignment a JSON presentation of an assignment from moodle
     * @return a list of all {@link MoodleSubmissionOfAssignmet}
     * @throws JsonProcessingException if parsing the value fails
     */
    private List<MoodleSubmissionOfAssignmet> findSubmissionsInAssignment(JsonNode assignment) throws JsonProcessingException {


        // get the submission property
        JsonNode moodleSubmissions = assignment.findPath("submissions");

        // check if there are submissions
        if (moodleSubmissions.size() < 1) {

            // return empty list
            return Collections.emptyList();
        }


        // create empty list of submissions
        List<MoodleSubmissionOfAssignmet> submissions = new ArrayList<>();

        // iterate over all submissions
        for (JsonNode singleSubmission : moodleSubmissions) {

            // create pojo
            MoodleSubmissionOfAssignmet tempSubmission = mapper.treeToValue(singleSubmission, MoodleSubmissionOfAssignmet.class);

            // call private method for finding the actual submission
            tempSubmission.setRepository(findRepoInSubmission(singleSubmission));

            // add pojo to list
            submissions.add(tempSubmission);
        }

        // return the list of all found submissions
        return submissions;
    }

    /**
     * Parses a submission in moodle to find the actual submission
     * @param singleSubmission a JSON presentation of a submission in moodle
     * @return the actual submission. Just a simple string, but should be an URL without a possible last slash
     */
    private String findRepoInSubmission(JsonNode singleSubmission) {

        // get the plugin property
        JsonNode plugins = singleSubmission.findPath("plugins");

        // check if there is a plugin
        if (plugins.size() < 1) {

            // return empty string
            return "";
        }

        // iterate over all plugins
        for (JsonNode plugin : plugins) {

            // check if a plugin is "onlinetext"
            if ("onlinetext".equals(plugin.findPath("type").asText())) {

                // get the "editorfield" property
                JsonNode editorfields = plugin.findPath("editorfields");

                // check if there is a editorfield
                if (editorfields.size() < 1) {
                    //return empty string
                    return "";
                }

                // iterate over all editor fields
                for (JsonNode field : editorfields) {

                    // get the propety text
                    JsonNode actualSubmit = field.findPath("text");

                    // check if there is a value for it
                    if (!actualSubmit.isMissingNode()) {

                        // get the actual submit and remove html
                        String repo = Jsoup.parse(actualSubmit.asText()).text();

                        // return and remove last slash if present
                        return removeLastSlashIfPresent(repo);
                    }
                }

            }
        }

        // nothin found --> return empty string
        return "";
    }

    /**
     * Helper method for removing the last dash in an URL
     * @param repository a URL
     * @return the same string but without a possible last slash
     */
    private String removeLastSlashIfPresent(String repository) {

        // temp save to local variable
        String repo = repository;

        // remove last slash if present
        if (repo.endsWith("/")) {
            repo = repo.substring(0, repo.length() - 1);

        }

        // return the URL
        return repo;
    }


}
