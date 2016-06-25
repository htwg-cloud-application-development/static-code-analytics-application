package de.htwg.konstanz.cloud.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by max on 24/06/16.
 */
@Data
@Document
public class CpdResults {

    @Id
    private String id;

    private String duplicationCoursePath;

    private int totalExpendedTime;

    private String assignments;

    private int numberOfDuplications;

    private Object duplications;

    private String timestamp;
}
