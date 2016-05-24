package de.htwg.konstanz.cloud.service;

import de.htwg.konstanz.cloud.model.Course;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CourseRepository extends MongoRepository<Course, String> {

}
