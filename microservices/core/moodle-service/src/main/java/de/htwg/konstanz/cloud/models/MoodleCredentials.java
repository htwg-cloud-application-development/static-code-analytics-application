package de.htwg.konstanz.cloud.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MoodleCredentials {

    @JsonProperty("password")
    private String password;
    private String mail;
}
