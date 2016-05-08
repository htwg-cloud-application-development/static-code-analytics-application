package de.htwg.konstanz.cloud.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;

@RestController
public class MongoService{


	@Autowired
	MongoOperations mongo;

	@Autowired
	UserRepositoryImpl repo;
	
	@RequestMapping("/mongoservice")
	public String test(){
	
		mongo.dropCollection(User.class);
		
		
		String jsonString = "{\"user\": \"Morph0815\",\"repositoryName\": \"SOTE1\",\"className\": \"irendeineJava.class\",\"errors\" : [{\"severity\": \"MONSTER\",\"line\": \"5\",\"column\": \"12\",\"source\": \"a source\",\"message\": \"ajlksdj\"},{ \"severity\": \"ULTRA\",\"line\": \"123\",\"column\": \"12\",\"source\": \"a source\",\"message\": \"ajlksdj\"}]}";
		
		System.out.println("Trying to persist jsonString");
		repo.persistJson(jsonString);
		
		
		ArrayList<User> studentList;
		studentList = (ArrayList<User>) mongo.findAll(User.class);
		
		for(User student: studentList){
			System.out.println(student.toString());
		}
		
		return "Hi!!!";

	}
}
