package de.htwg.konstanz.cloud.service;

import org.springframework.data.mongodb.repository.MongoRepository;

import de.htwg.konstanz.cloud.model.UserRep;

public interface UserRepRepository extends MongoRepository<UserRep, String>, UserRepOperations {
	
	
}
