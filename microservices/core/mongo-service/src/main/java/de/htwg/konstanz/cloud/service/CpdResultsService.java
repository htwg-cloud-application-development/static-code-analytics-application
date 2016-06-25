package de.htwg.konstanz.cloud.service;

import com.netflix.ribbon.proxy.annotation.Http;
import de.htwg.konstanz.cloud.model.CpdResults;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.Date;
import java.util.List;

/**
 * Created by max on 24/06/16.
 */
@RestController
@RequestMapping("/cpdresults")
public class CpdResultsService {

    @Autowired
    MongoOperations mongo;

    @Autowired
    CpdResultsRepository cpdResultsRepository;

    @RequestMapping(value = "/addcpdresult", method = RequestMethod.POST ,consumes = "application/json")
    public ResponseEntity addCpdResult(@RequestBody final CpdResults cpdResults){
        ResponseEntity responseEntity;

        cpdResults.setTimestamp(String.valueOf(String.valueOf(new Date().getTime())));
        cpdResultsRepository.save(cpdResults);
        return new ResponseEntity(HttpStatus.OK);
    }


    @RequestMapping(value = "/getLastCpdResult/{courseId}", method = RequestMethod.GET)
    public ResponseEntity<CpdResults> getLastCpdResult(@PathVariable("courseId") final String courseId){
        ResponseEntity<CpdResults> cpdResultsResponseEntity;

        Query query = new Query();
        query.addCriteria(Criteria.where("courseId").is(courseId));
        Sort sort = new Sort(Sort.Direction.DESC, "timestamp");
        List<CpdResults> cpdResult = mongo.find(query.with(sort).limit(1), CpdResults.class);

        if (cpdResult.isEmpty()){
            cpdResultsResponseEntity = new ResponseEntity<CpdResults>(HttpStatus.NO_CONTENT);
        } else {
            cpdResultsResponseEntity = new ResponseEntity<CpdResults>(cpdResult.get(0), HttpStatus.OK);
        }
        return cpdResultsResponseEntity;
    }
}
