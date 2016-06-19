package de.htwg.konstanz.cloud.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Assignment {

    @Id
    private String id;

    private String name;

    private String modicon;

    private String modname;

    private String modplural;

    private String indent;
}
