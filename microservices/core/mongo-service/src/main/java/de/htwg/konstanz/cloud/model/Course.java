package de.htwg.konstanz.cloud.model;

//import sun.javafx.beans.IDProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
public class Course {
    @Id
    String courseId;
    String name;
    List<Group> groups;
    
}
