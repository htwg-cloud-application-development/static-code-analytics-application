package de.htwg.konstanz.cloud.service;

import de.htwg.konstanz.cloud.model.CheckstyleResults;
import de.htwg.konstanz.cloud.model.Group;
import de.htwg.konstanz.cloud.model.PmdResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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

    /**
     * Saves checkstyle entry
     * Finds associated group via "userId" key in requestBody
     * Attaches entry to group
     *
     * @param checkstyleResults checkstyle results to save
     * @return HttpStatus
     */
    @RequestMapping(value = "/addCheckstyleEntry", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity addCheckstyleEntry(@RequestBody final CheckstyleResults checkstyleResults) {

        ResponseEntity responseEntity;
        checkstyleResults.setTimestamp(String.valueOf(new Date().getTime()));

        final String userId = checkstyleResults.getUserId();
        final Group group = mongo.findOne(Query.query(Criteria.where("id").is(userId)), Group.class);

        if (null == group) {
            responseEntity = new ResponseEntity(HttpStatus.NO_CONTENT);

        } else {
            checkstyleRepo.save(checkstyleResults);
            /** set checkstyle in gorup **/
            mongo.updateFirst(Query.query(Criteria.where("id").is(userId)),
                    Update.update("checkstyle", checkstyleResults), Group.class);
            responseEntity = new ResponseEntity(HttpStatus.OK);
        }
        return responseEntity;
    }

    /**
     * Finds the last added checkstyle result for specific group
     *
     * @param userId id to querry groups
     * @return chekstyle result
     */
    @RequestMapping(value = "/courses/{userId}/findLastCheckstyleResult", method = RequestMethod.GET)
    public ResponseEntity<CheckstyleResults> getLastCheckstyleGroupResult(@PathVariable("userId") final String userId) {

        ResponseEntity<CheckstyleResults> responseEntity;
        final List<CheckstyleResults> checkstyleResults = mongo
                .find(Query
                        .query(Criteria.where("userId").is(userId))
                        .with(new Sort(Sort.Direction.DESC, "timestamp"))
                        .limit(1), CheckstyleResults.class);

        if (checkstyleResults.isEmpty()) {
            responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            responseEntity = new ResponseEntity<>(checkstyleResults.get(0), HttpStatus.OK);
        }
        return responseEntity;
    }

    /**
     * Saves pmd entry
     * Finds associated group via "userId" key in requestBody
     * Attaches entry to group
     * @param pmdResults pmd result to save
     * @return HttpStatus
     */
    @RequestMapping(value = "/addPmdEntry", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity addPmdEntry(@RequestBody final PmdResults pmdResults) {

        ResponseEntity responseEntity;
        pmdResults.setTimestamp(String.valueOf(new Date().getTime()));

        final String userId = pmdResults.getUserId();
        final Group group = mongo.findOne(Query.query(Criteria.where("id").is(userId)), Group.class);

        if (null == group) {
            responseEntity = new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            pmdRepo.save(pmdResults);
            /**set pmd in gorup**/
            mongo.updateFirst(Query.query(Criteria.where("id").is(userId)), Update.update("pmd", pmdResults), Group.class);
            responseEntity = new ResponseEntity(HttpStatus.OK);
        }
        return responseEntity;
    }

    /**
     * Finds the last added pmd result for specific group
     * @param userId id to querry groups
     * @return pmd result
     */
    @RequestMapping(value = "/courses/{userId}/findLastPmdResult", method = RequestMethod.GET)
    public ResponseEntity<PmdResults> getLastPmdGroupResult(@PathVariable("userId") final String userId) {

        ResponseEntity<PmdResults> responseEntity;
        final List<PmdResults> pmdResults = mongo
                .find(Query
                        .query(Criteria.where("userId").is(userId))
                        .with(new Sort(Sort.Direction.DESC, "timestamp"))
                        .limit(1), PmdResults.class);

        if (pmdResults.isEmpty()) {
            responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            responseEntity = new ResponseEntity<>(pmdResults.get(0), HttpStatus.OK);
        }
        return responseEntity;
    }
}