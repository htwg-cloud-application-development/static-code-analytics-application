package de.htwg.konstanz.cloud.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ValidationData {

    @NotNull
    private String repositoryUrl;

}
