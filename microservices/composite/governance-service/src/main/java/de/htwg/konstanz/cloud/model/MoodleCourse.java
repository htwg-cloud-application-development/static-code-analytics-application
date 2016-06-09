package de.htwg.konstanz.cloud.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

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

