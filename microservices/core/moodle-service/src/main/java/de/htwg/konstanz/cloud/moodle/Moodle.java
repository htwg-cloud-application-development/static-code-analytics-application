package de.htwg.konstanz.cloud.moodle;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.htwg.konstanz.cloud.models.GeneralMoodleInfo;
import de.htwg.konstanz.cloud.models.MoodleCourse;
import de.htwg.konstanz.cloud.models.MoodleToken;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by steffen on 27/05/16.
 */
public class Moodle {

    private RestTemplate templ = new RestTemplate();


    private static final String MOODLE_BASE_URL = "https://moodle.htwg-konstanz.de/moodle/webservice/rest/server.php?" +
            "&moodlewsrestformat=json";

    public Integer getMoodleIdFromMoodleToken(String token) {

        String service = "core_webservice_get_site_info";
        String requestURL = MOODLE_BASE_URL + "&wsfunction=" + service +
                "&wstoken=" + token;

        GeneralMoodleInfo moodleInfo = templ.getForObject(requestURL, GeneralMoodleInfo.class);


        return moodleInfo.getUserid();
    }

    public List<MoodleCourse> getCoursesOfMoodleUser(String userId, String token) throws JsonProcessingException {

        String service = "core_enrol_get_users_courses";
        String requestURL = MOODLE_BASE_URL + "&wsfunction=" + service +
                "&wstoken=" + token + "&userid=" + userId;

        JsonNode courses = templ.getForObject(requestURL, JsonNode.class);


        ObjectMapper mapper = new ObjectMapper();
        List<MoodleCourse> moodleCourses = new ArrayList<MoodleCourse>();
        for (JsonNode course : courses) {
            moodleCourses.add(mapper.treeToValue(course, MoodleCourse.class));
        }


        return moodleCourses;
    }


    public MoodleCourse getCourseInformation(MoodleToken tokenFromMoodle, int s) {
        return null;
    }
}
