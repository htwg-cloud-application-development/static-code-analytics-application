package de.htwg.konstanz.cloud.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.htwg.konstanz.cloud.model.UserRep;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;

@RestController
public class MongoService{


	@Autowired
	MongoOperations mongo;

	@Autowired
	UserRepRepositoryImpl repo;
	
	@RequestMapping("/mongoservice")
	public String test(){
	
		mongo.dropCollection(UserRep.class);
		
		String jsonString = "{\"name\": \"Arscg\",\"groupId\": \"1\",\"repositoryName\": \"Name\",\"files\":[{\"filepath\": \"pfad\",\"errors\":[{\"line\": \"5\",\"column\": \"5\",\"severity\": \"Hoch\",\"message\": \"Nachricht\",\"source\": \"quelle\"}]}]}";

		System.out.println("Trying to persist jsonString");
		repo.persistJson(jsonString);
		
		
		ArrayList<UserRep> studentList;
		studentList = (ArrayList<UserRep>) mongo.findAll(UserRep.class);
		
		for(UserRep student: studentList){
			System.out.println(student.toString());
		}
		
		return "Hi!!!";

	}
}
