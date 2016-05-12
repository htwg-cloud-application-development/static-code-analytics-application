package de.htwg.konstanz.cloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;

import com.mongodb.DBObject;
import com.mongodb.util.JSON;

import de.htwg.konstanz.cloud.model.UserRep;

public class UserRepRepositoryImpl implements UserRepOperations {
	
	@Autowired
	MongoOperations mongo;
	
	public void persistJson(String jsonString){
		
		UserRep student;
		student = mongo.getConverter().read(UserRep.class, (DBObject) JSON.parse(jsonString));
		mongo.insert(student);
		
	}

}
