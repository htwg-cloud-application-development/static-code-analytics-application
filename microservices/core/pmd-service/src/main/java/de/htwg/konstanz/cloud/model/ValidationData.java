package de.htwg.konstanz.cloud.model;

import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ValidationData {

    @NotNull
    private String repository;

    @Override
    public String toString(){
        JSONObject json = new JSONObject();
        try {
            json.put("repository", repository);
            return json.toString();
        } catch (JSONException e) {
            return repository;
        }
    }
}
