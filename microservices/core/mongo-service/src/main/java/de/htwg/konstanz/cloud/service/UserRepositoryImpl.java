package de.htwg.konstanz.cloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;

import com.mongodb.DBObject;
import com.mongodb.util.JSON;

public class UserRepositoryImpl implements UserOperations {
	
	@Autowired
	MongoOperations mongo;
	
	public void persistJson(String jsonString){
		
		User student;
		
		student = mongo.getConverter().read(User.class, (DBObject) JSON.parse(jsonString));
		mongo.insert(student);
		
	}

}
