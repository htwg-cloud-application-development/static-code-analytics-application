package de.htwg.konstanz.cloud.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Created by steffen on 27/05/16.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MoodleCourse {

    Integer id;
    String shortname;
    String fullname;
    Integer enrolledusercount;
    String idnumber;
    Integer visible;

}

