package de.htwg.konstanz.cloud.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
public class Group {
    @Id
    String groupId;
    String name;
    String repositoryUrl;
}
