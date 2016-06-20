package de.htwg.konstanz.cloud.service;

import de.htwg.konstanz.cloud.model.PmdResults;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PmdResultsRepository extends MongoRepository<PmdResults, String> {

}
