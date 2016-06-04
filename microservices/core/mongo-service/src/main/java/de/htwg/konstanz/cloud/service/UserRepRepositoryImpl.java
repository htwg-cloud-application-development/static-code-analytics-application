package de.htwg.konstanz.cloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.json.JSONObject;

import com.mongodb.DBObject;
import com.mongodb.util.JSON;

import de.htwg.konstanz.cloud.model.UserRep;

import java.util.Date;

public class UserRepRepositoryImpl implements UserRepOperations {
	
	@Autowired
	MongoOperations mongo;
	
	public void persistJson(String jsonString){
		
		UserRep student;
        jsonString = addTimestamp(jsonString);

		student = mongo.getConverter().read(UserRep.class, (DBObject) JSON.parse(jsonString));
		mongo.insert(student);
	}

	public String addTimestamp(String json){

		JSONObject jsonOBject = new JSONObject(json);
        jsonOBject.put("timestamp", String.valueOf(new Date().getTime()));

        return jsonOBject.toString();
	}

}
