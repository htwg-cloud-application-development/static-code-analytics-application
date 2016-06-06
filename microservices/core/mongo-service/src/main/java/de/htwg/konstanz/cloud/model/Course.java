package de.htwg.konstanz.cloud.model;

//import sun.javafx.beans.IDProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
public class Course {

    String id;
    String shortname;
    String fullname;
    String enrolledusercount;
    String idnumber;
    String visible;
}
