package de.htwg.konstanz.cloud.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
public class User {

    @Id
    String userid;
    String sitename;
    String username;
    String firstname;
    String lastname;
    String fullname;
    String lang;
    String siteurl;
    String userpictureurl;
    @DBRef
    List<Course> courses;
}
