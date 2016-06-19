package de.htwg.konstanz.cloud.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class PMDResults {
    @Id
    private String id;

    private String repository;

    private String numberOfErrors;

    private String numberOfWarnings;

    private String numberOfIgnores;

    private String totalExpendedTime;

    private String timestamp;

    private String userId;

    private Object assignments;
}
