package de.htwg.konstanz.cloud.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Assignment {

    @Id
    String id;
    String name;
    String modicon;
    String modname;
    String modplural;
    String indent;
}
