package de.htwg.konstanz.cloud.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * Created by steffen on 02/06/16.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeneralMoodleInfo {

    String sitename;
    String username;
    String firstname;
    String lastname;
    String fullname;
    String lang;
    Integer userid;
    String siteurl;
    String userpictureurl;

}
