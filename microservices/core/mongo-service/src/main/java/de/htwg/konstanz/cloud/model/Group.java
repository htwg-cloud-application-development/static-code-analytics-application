package de.htwg.konstanz.cloud.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Group {

    @Id
    private String userId;
    private String attemptnumber;
    private String timecreated;
    private String timemodified;
    private String status;
    private String repository;
    @DBRef
    private PMDResults pmd;
    @DBRef
    private CheckstyleResults checkstyle;

}
