package de.htwg.konstanz.cloud.service;

import lombok.Data;
import org.json.JSONArray;
import org.json.JSONObject;

@Data
class OwnJsonProperties {
    JSONObject oJsonRoot = new JSONObject();

    JSONObject oJsonExercise = new JSONObject();

    JSONArray lJsonClasses = new JSONArray();

    JSONArray lJsonExercises = new JSONArray();

    String sTmpExcerciseName = "";
}
