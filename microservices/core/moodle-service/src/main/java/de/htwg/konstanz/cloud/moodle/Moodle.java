package de.htwg.konstanz.cloud.moodle;

import de.htwg.konstanz.cloud.models.MoodleCourse;
import de.htwg.konstanz.cloud.models.MoodleCredentials;
import de.htwg.konstanz.cloud.models.MoodleToken;
import org.springframework.web.client.RestTemplate;


/**
 * Created by steffen on 27/05/16.
 */
public class Moodle {

    private static final String LOGIN_MOODLE_URL = "https://moodle.htwg-konstanz.de/moodle/login/token.php?" +
            "service=moodle_mobile_app&moodlewsrestformat=json";

    public MoodleToken getTokenFromMoodle(MoodleCredentials credentials) {


        String requestURL = LOGIN_MOODLE_URL + "&username=" + credentials.getUsername() +
                "&password=" + credentials.getPassword();

        RestTemplate templ = new RestTemplate();

        MoodleToken token = templ.getForObject(requestURL, MoodleToken.class);

        return token;
    }

    public MoodleCourse getCourseInformation(MoodleToken tokenFromMoodle, int s) {
        return null;
    }
}
