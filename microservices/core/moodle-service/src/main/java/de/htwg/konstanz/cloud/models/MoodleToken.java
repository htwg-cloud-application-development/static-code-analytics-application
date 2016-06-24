package de.htwg.konstanz.cloud.models;

import lombok.Data;
import javax.validation.constraints.NotNull;

/**
 * Java Bean of a moodle Token
 */
@Data
public class MoodleToken {

    @NotNull
    private String token;
}
