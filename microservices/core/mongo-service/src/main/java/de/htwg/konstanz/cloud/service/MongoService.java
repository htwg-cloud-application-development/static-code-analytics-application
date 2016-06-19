package de.htwg.konstanz.cloud.service;

import de.htwg.konstanz.cloud.model.CheckstyleResults;
import de.htwg.konstanz.cloud.model.Group;
import de.htwg.konstanz.cloud.model.PMDResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@RestController
public class MongoService {


    @Autowired
    MongoOperations mongo;

    @Autowired
    CheckstyleResultsRepository checkstyleRepo;

    @Autowired
    PMDResultsRepository pmdRepo;

    @Autowired
    GroupRepository groupRepo;

    @RequestMapping(value = "/addCheckstyleEntry", method = RequestMethod.POST, consumes = "application/json")
    public void addCheckstyleEntry(@RequestBody final CheckstyleResults checkstyleResults) {


        checkstyleResults.setTimestamp(String.valueOf(new Date().getTime()));
        checkstyleRepo.save(checkstyleResults);

        final String userId = checkstyleResults.getUserId();
        final Group group = mongo.findOne(Query.query(Criteria.where("userId").is(userId)), Group.class);

        group.setCheckstyle(checkstyleResults);
        groupRepo.save(group);
    }

    @RequestMapping(value = "/courses/{userId}/findLastCheckstyleResult", method = RequestMethod.GET)
    public ResponseEntity<CheckstyleResults> getLastCheckstyleGroupResult(@PathVariable("userId") final String userId) {

        ResponseEntity<CheckstyleResults> responseEntity;
        final List<CheckstyleResults> checkstyleResults = mongo
                .find(Query
                        .query(Criteria.where("userId").is(userId))
                        .with(new Sort(Sort.Direction.DESC, "timestamp"))
                        .limit(1), CheckstyleResults.class);

        if (checkstyleResults.size() > 0){
            responseEntity = new ResponseEntity<>(checkstyleResults.get(0), HttpStatus.OK);
        } else {
            responseEntity =  new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return  responseEntity;
    }

    @RequestMapping(value = "/courses/{userId}/findLastPmdResult", method = RequestMethod.GET)
    public
    ResponseEntity<PMDResults> getLastPMDGroupResult(@PathVariable("userId") final String userId) {

        ResponseEntity<PMDResults> responseEntity;
        final List<PMDResults> pmdResults = mongo
                .find(Query
                        .query(Criteria.where("userId").is(userId))
                        .with(new Sort(Sort.Direction.DESC, "timestamp"))
                        .limit(1), PMDResults.class);

        if (pmdResults.size() > 0){
            responseEntity = new ResponseEntity<>(pmdResults.get(0), HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return responseEntity;
    }

    @RequestMapping(value = "/addPMDEntry", method = RequestMethod.POST, consumes = "application/json")
    public void addPMDEntry(@RequestBody final PMDResults pmdResults) {
        pmdResults.setTimestamp(String.valueOf(new Date().getTime()));
        pmdRepo.save(pmdResults);

        final String userId = pmdResults.getUserId();
        final Group group = mongo.findOne(Query.query(Criteria.where("userId").is(userId)), Group.class);

        group.setPmd(pmdResults);
        groupRepo.save(group);
    }



}