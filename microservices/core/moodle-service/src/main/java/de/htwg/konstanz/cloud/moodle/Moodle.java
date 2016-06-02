package de.htwg.konstanz.cloud.moodle;

import com.fasterxml.jackson.databind.JsonNode;
import de.htwg.konstanz.cloud.models.MoodleCourse;
import de.htwg.konstanz.cloud.models.MoodleToken;
import org.springframework.web.client.RestTemplate;


/**
 * Created by steffen on 27/05/16.
 */
public class Moodle {

    private RestTemplate templ = new RestTemplate();


    private static final String MOODLE_BASE_URL = "https://moodle.htwg-konstanz.de/moodle/webservice/rest/server.php?" +
            "&moodlewsrestformat=json";

    public String getMoodleIdFromMoodleToken(MoodleToken token) {

        String service = "core_webservice_get_site_info";
        String requestURL = MOODLE_BASE_URL + "&wsfunction=" + service +
                "&wstoken=" + token.getToken();

        JsonNode moodleInfo = templ.getForObject(requestURL, JsonNode.class);

        String userId = moodleInfo.findPath("userid").asText();

        return userId;
    }




    public MoodleCourse getCourseInformation(MoodleToken tokenFromMoodle, int s) {
        return null;
    }
}
