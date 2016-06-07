package de.htwg.konstanz.cloud.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
public class Course {

    @Id
    String id;
    String shortname;
    String fullname;
    String enrolledusercount;
    String idnumber;
    String visible;
    @DBRef
    List<Assignment> assignments;
}
