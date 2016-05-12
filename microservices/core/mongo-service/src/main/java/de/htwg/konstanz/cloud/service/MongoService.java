package de.htwg.konstanz.cloud.service;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	
	@RequestMapping(value = "/addEntry", method = RequestMethod.POST)
	public void addEntryToDb(@RequestBody String jsonString){
	
		//mongo.dropCollection(UserRep.class);
		
		repo.persistJson(jsonString);
		
		
		ArrayList<UserRep> studentList;
		studentList = (ArrayList<UserRep>) mongo.findAll(UserRep.class);
		
		for(UserRep student: studentList){
			System.out.println(student.toString());
		}

	}
}
