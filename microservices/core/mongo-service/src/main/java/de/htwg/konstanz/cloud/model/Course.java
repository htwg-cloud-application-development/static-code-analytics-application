package de.htwg.konstanz.cloud.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@Data
@Document
public class Course {

    @Id
    private String id;

    private String shortname;

    private String fullname;

    private String enrolledusercount;

    private String idnumber;

    private String visible;

    @DBRef
    private List<Assignment> assignments;

    @DBRef
    private List<Group> groups;
}
