package de.htwg.konstanz.cloud.model;

import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ValidationData {

    @NotNull
    private String repositoryUrl;

}
