package de.htwg.konstanz.cloud.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Java Bean of a assignment in a course from moodle
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MoodleAssignment {

    Integer id;

    String name;

    Integer course;

}
