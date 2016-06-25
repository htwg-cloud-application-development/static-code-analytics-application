package de.htwg.konstanz.cloud.service;

import de.htwg.konstanz.cloud.model.CpdResults;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by max on 24/06/16.
 */
public interface CpdResultsRepository extends MongoRepository<CpdResults, String> {
}
