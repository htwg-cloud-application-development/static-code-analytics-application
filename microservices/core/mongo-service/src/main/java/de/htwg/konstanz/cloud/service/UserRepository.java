package de.htwg.konstanz.cloud.service;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String>, UserOperations {
	
	
}
