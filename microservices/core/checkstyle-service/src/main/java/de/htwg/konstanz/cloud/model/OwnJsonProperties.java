package de.htwg.konstanz.cloud.model;

import lombok.Data;
import org.json.JSONArray;
import org.json.JSONObject;

//Properties for the json file
@Data
public class OwnJsonProperties {
    JSONObject oJsonRoot = new JSONObject();

    JSONObject oJsonExercise = new JSONObject();

    JSONArray lJsonClasses = new JSONArray();

    JSONArray lJsonExercises = new JSONArray();

    String sTmpExcerciseName = "";
}
