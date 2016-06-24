package de.htwg.konstanz.cloud.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * Java bean of a submission in moodle
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MoodleSubmissionOfAssignmet {

    Integer id;

    Integer userid;

    Integer attemptnumber;

    Integer timecreated;

    Integer timemodified;

    String status;

    String repository;
}
