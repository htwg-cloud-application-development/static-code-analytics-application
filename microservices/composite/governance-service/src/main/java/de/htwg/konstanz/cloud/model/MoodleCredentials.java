package de.htwg.konstanz.cloud.model;

import lombok.Data;

@Data
public class MoodleCredentials {

    String username;
    String password;


    public MoodleCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
