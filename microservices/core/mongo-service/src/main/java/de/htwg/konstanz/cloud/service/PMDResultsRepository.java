package de.htwg.konstanz.cloud.service;

import de.htwg.konstanz.cloud.model.PMDResults;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PMDResultsRepository extends MongoRepository<PMDResults, String> {

}
