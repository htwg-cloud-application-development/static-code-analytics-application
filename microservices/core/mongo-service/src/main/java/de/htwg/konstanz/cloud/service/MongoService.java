package de.htwg.konstanz.cloud.service;

import com.mongodb.BasicDBObject;
import de.htwg.konstanz.cloud.model.UserRep;
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
	UserRepRepositoryImpl repo;
	
	@RequestMapping(value = "/addEntry", method = RequestMethod.POST)
	public void addEntryToDb(@RequestBody String jsonString){

		repo.persistJson(jsonString);

	}

    @RequestMapping(value = "/findLastResult", method = RequestMethod.GET)
    public @ResponseBody UserRep getLastGroupResult(@RequestParam("groupId") String groupId){

        List<UserRep> userReps =  mongo.find(Query.query(Criteria.where("groupId").is(groupId)).with(new Sort(Sort.Direction.DESC, "timestamp")).limit(1), UserRep.class);

		// Ok like that?
        return userReps.get(0);
    }

}