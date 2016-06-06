package de.htwg.konstanz.cloud.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Group {
    @Id
    String groupId;
    String name;
    String repositoryUrl;
}
