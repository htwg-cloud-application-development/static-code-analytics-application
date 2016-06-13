package de.htwg.konstanz.cloud.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Group {

    @Id
    String userid;
    String attemptnumber;
    String timecreated;
    String timemodified;
    String status;
    String repository;
    @DBRef
    String pmdId;
    @DBRef
    String checkstyleId;

}
