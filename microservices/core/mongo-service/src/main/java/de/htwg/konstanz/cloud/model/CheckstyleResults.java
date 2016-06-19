package de.htwg.konstanz.cloud.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class CheckstyleResults {

    @Id
    private String id;

    private String repository;

    private int numberOfErrors;

    private int numberOfWarnings;

    private int numberOfIgnores;

    private long totalExpendedTime;

    private String timestamp;

    private String userId;

    private Object assignments;

}
