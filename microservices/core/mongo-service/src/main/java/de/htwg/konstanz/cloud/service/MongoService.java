package de.htwg.konstanz.cloud.service;

import de.htwg.konstanz.cloud.model.CheckstyleResults;
import de.htwg.konstanz.cloud.model.Group;
import de.htwg.konstanz.cloud.model.PmdResults;
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
    PmdResultsRepository pmdRepo;

    @Autowired
    GroupRepository groupRepo;


    //Add CheckstyleEntry to DB
    //Finds associated group over "userId" key in requestBody
    //Saves in checkStyleResults & Group
    @RequestMapping(value = "/addCheckstyleEntry", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity addCheckstyleEntry(@RequestBody final CheckstyleResults checkstyleResults) {

        ResponseEntity responseEntity;
        checkstyleResults.setTimestamp(String.valueOf(new Date().getTime()));

        final String userId = checkstyleResults.getUserId();
        final Group group = mongo.findOne(Query.query(Criteria.where("userId").is(userId)), Group.class);

        if (null == group){
            responseEntity = new ResponseEntity(HttpStatus.NO_CONTENT);

        } else {
            checkstyleRepo.save(checkstyleResults);

            group.setCheckstyle(checkstyleResults);
            groupRepo.save(group);

            responseEntity = new ResponseEntity(HttpStatus.OK);
        }
        return responseEntity;
    }

    // Finds the last added CheckstyleResult for specific userId
    @RequestMapping(value = "/courses/{userId}/findLastCheckstyleResult", method = RequestMethod.GET)
    public ResponseEntity<CheckstyleResults> getLastCheckstyleGroupResult(@PathVariable("userId") final String userId) {

        ResponseEntity<CheckstyleResults> responseEntity;
        final List<CheckstyleResults> checkstyleResults = mongo
                .find(Query
                        .query(Criteria.where("userId").is(userId))
                        .with(new Sort(Sort.Direction.DESC, "timestamp"))
                        .limit(1), CheckstyleResults.class);

        if (checkstyleResults.isEmpty()){
            responseEntity =  new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            responseEntity = new ResponseEntity<>(checkstyleResults.get(0), HttpStatus.OK);
        }
        return  responseEntity;
    }

    //Add PmdEntry to DB
    //Finds associated group over "userId" key in requestBody
    //Saves in PmdResults & Group
    @RequestMapping(value = "/addPmdEntry", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity addPmdEntry(@RequestBody final PmdResults pmdResults) {

        ResponseEntity responseEntity;
        pmdResults.setTimestamp(String.valueOf(new Date().getTime()));

        final String userId = pmdResults.getUserId();
        final Group group = mongo.findOne(Query.query(Criteria.where("userId").is(userId)), Group.class);

        if (null == group){
            responseEntity = new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            pmdRepo.save(pmdResults);

            group.setPmd(pmdResults);
            groupRepo.save(group);

            responseEntity = new ResponseEntity(HttpStatus.OK);
        }
        return responseEntity;
    }

    // Finds the last added PmdResult for specific userId
    @RequestMapping(value = "/courses/{userId}/findLastPmdResult", method = RequestMethod.GET)
    public
    ResponseEntity<PmdResults> getLastPmdGroupResult(@PathVariable("userId") final String userId) {

        ResponseEntity<PmdResults> responseEntity;
        final List<PmdResults> pmdResults = mongo
                .find(Query
                        .query(Criteria.where("userId").is(userId))
                        .with(new Sort(Sort.Direction.DESC, "timestamp"))
                        .limit(1), PmdResults.class);

        if (pmdResults.isEmpty()){
            responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            responseEntity = new ResponseEntity<>(pmdResults.get(0), HttpStatus.OK);
        }
        return responseEntity;
    }
}