package de.htwg.konstanz.cloud.models;

import lombok.Data;

/**
 * Created by steffen on 27/05/16.
 */
@Data
public class MoodleCourse {

    Integer id;
    String shortname;
    String fullname;
    Integer enrolledusercount;
    String idnumber;
    Integer visible;

}

