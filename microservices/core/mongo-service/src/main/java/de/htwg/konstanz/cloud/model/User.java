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
    private String userid;

    private String sitename;

    private String username;

    private String firstname;

    private String lastname;

    private String fullname;

    private String lang;

    private String siteurl;

    private String userpictureurl;

    @DBRef
    private List<Course> courses;

}
