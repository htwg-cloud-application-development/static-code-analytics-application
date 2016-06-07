package de.htwg.konstanz.cloud.service;

import de.htwg.konstanz.cloud.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

}
