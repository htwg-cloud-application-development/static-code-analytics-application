package de.htwg.konstanz.cloud.service;

import de.htwg.konstanz.cloud.model.Assignment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AssignmentRepository extends MongoRepository<Assignment, String> {

}
