package de.htwg.konstanz.cloud.service;

import de.htwg.konstanz.cloud.model.Group;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GroupRepository extends MongoRepository<Group, String> {
}
