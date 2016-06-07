package de.htwg.konstanz.cloud.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Course {

    @Id
    String id;
    String shortname;
    String fullname;
    String enrolledusercount;
    String idnumber;
    String visible;
}
