package de.htwg.konstanz.cloud.service;

import de.htwg.konstanz.cloud.model.CheckstyleResults;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CheckstyleResultsRepository extends MongoRepository<CheckstyleResults, String>, CheckstyleResultsOperations {
	
	
}
