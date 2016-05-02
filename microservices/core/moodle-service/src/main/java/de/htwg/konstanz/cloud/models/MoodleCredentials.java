package de.htwg.konstanz.cloud.models;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MoodleCredentials {

    @NotNull
    private String password;
    @NotNull
    private String mail;
}
