package de.htwg.konstanz.cloud.service;

import java.net.UnknownHostException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;


@Configuration
@EnableMongoRepositories
public class MongoConfig {

	@Bean
	public MongoClient mongo() throws UnknownHostException {
		
		MongoClient mongo = new MongoClient("localhost", 27017);
		return mongo;
	
	}
	
	@Bean
	public MongoOperations mongoTemplate(Mongo mongo){
	
		return new MongoTemplate(mongo, "TestDB");
	
	}
	
}
