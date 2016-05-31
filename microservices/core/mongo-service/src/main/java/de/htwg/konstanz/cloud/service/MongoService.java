package de.htwg.konstanz.cloud.service;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

		repo.persistJson(jsonString);

	}

	@RequestMapping(value = "/testEntry", method = RequestMethod.POST)
	public void testEntry(@RequestBody String jsonString){



	}

}
