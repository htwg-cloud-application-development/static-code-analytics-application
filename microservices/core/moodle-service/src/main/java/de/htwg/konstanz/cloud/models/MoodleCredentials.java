package de.htwg.konstanz.cloud.models;

import lombok.Data;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class MoodleCredentials {

    @NotNull
    private String password;
    @NotNull
    @Email
    private String mail;
}
