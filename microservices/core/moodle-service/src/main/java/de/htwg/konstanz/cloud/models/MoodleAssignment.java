package de.htwg.konstanz.cloud.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Created by steffen on 02/06/16.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MoodleAssignment {

    Integer id;
    String name;
    Integer course;

}
