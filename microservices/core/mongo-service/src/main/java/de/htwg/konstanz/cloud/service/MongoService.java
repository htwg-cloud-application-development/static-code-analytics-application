package de.htwg.konstanz.cloud.service;

import de.htwg.konstanz.cloud.model.CheckstyleResults;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;

import java.util.List;


@RestController
public class MongoService{


	@Autowired
	MongoOperations mongo;

	@Autowired
	CheckstyleResultsRepositoryImpl repo;
	
	@RequestMapping(value = "/addCheckstyleEntry", method = RequestMethod.POST)
	public void addCheckstyleEntry(@RequestBody String jsonString){

		repo.persistJson(jsonString);
	}

    @RequestMapping(value = "/findLastResult", method = RequestMethod.GET)
    public @ResponseBody
	CheckstyleResults getLastGroupResult(@RequestParam("groupId") String groupId){

       List<CheckstyleResults> userReps =  mongo.find(Query.query(Criteria.where("groupId").is(groupId)).with(new Sort(Sort.Direction.DESC, "timestamp")).limit(1), CheckstyleResults.class);
       return userReps.get(0);
    }

	@RequestMapping(value ="/addPMDEntry", method = RequestMethod.POST)
	public void addPMDEntry(@RequestBody String jsonString){


	}

}