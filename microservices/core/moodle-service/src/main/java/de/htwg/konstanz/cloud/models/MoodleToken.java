package de.htwg.konstanz.cloud.models;

import lombok.Data;
import javax.validation.constraints.NotNull;

/**
 * Created by steffen on 27/05/16.
 */

@Data
public class MoodleToken {

    @NotNull
    private String token;
}
