package de.htwg.konstanz.cloud.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode(of = {"id"})
@Document
public class Group {

    @Id
    private String id;

    private String attemptnumber;

    private String timecreated;

    private String timemodified;

    private String status;

    private String repository;

    private String executiontime;

    @DBRef
    private PmdResults pmd;

    @DBRef
    private CheckstyleResults checkstyle;
}
