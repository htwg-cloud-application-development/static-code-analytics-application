package de.htwg.konstanz.cloud.service;

import de.htwg.konstanz.cloud.model.CheckstyleResults;
import de.htwg.konstanz.cloud.model.PMDResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

//TODO:Exceptions for ID not found etc?
//TODO:Own mapping or only mapping to methods?
@RestController
public class MongoService {


    @Autowired
    MongoOperations mongo;

    @Autowired
    CheckstyleResultsRepository checkstyleRepo;

    @Autowired
    PMDResultsRepository pmdRepo;

    @RequestMapping(value = "/addCheckstyleEntry", method = RequestMethod.POST, consumes = "application/json")
    public void addCheckstyleEntry(@RequestBody CheckstyleResults checkstyleResults) {
        checkstyleResults.setTimestamp(String.valueOf(new Date().getTime()));
        checkstyleRepo.save(checkstyleResults);
    }

    @RequestMapping(value = "/addPMDEntry", method = RequestMethod.POST, consumes = "application/json")
    public void addPMDEntry(@RequestBody PMDResults pmdResults) {
        pmdResults.setTimestamp(String.valueOf(new Date().getTime()));
        pmdRepo.save(pmdResults);
    }

    @RequestMapping(value = "/findLastCheckstyleResult", method = RequestMethod.GET)
    public
    @ResponseBody
    CheckstyleResults getLastCheckstyleGroupResult(@RequestParam("groupId") String groupId) {

        List<CheckstyleResults> userReps = mongo.find(Query.query(Criteria.where("groupId").is(groupId)).with(new Sort(Sort.Direction.DESC, "timestamp")).limit(1), CheckstyleResults.class);
        return userReps.get(0);
    }

    @RequestMapping(value = "/findLastPMDResult", method = RequestMethod.GET)
    public
    @ResponseBody
    PMDResults getLastPMDGroupResult(@RequestParam("groupId") String groupId) {

        List<PMDResults> pmdResults = mongo.find(Query.query(Criteria.where("groupId").is(groupId)).with(new Sort(Sort.Direction.DESC, "timestamp")).limit(1), PMDResults.class);
        return pmdResults.get(0);
    }
}