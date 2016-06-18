package de.htwg.konstanz.cloud.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * Created by steffen on 02/06/16.
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
