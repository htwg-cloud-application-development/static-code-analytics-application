package de.htwg.konstanz.cloud.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Java Bean of a course from moodle
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

