package de.htwg.konstanz.cloud.service;

import de.htwg.konstanz.cloud.model.CpdResults;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by max on 24/06/16.
 */
@RestController
@RequestMapping("/cpdresults")
public class CpdResultsService {

    @Autowired
    CpdResultsRepository cpdResultsRepository;

    @RequestMapping(value = "/addcpdresult", method = RequestMethod.POST ,consumes = "application/json")
    public ResponseEntity addCpdResult(@RequestBody final CpdResults cpdResults){
        ResponseEntity responseEntity;

        cpdResults.setTimestamp(String.valueOf(String.valueOf(new Date().getTime())));

        cpdResultsRepository.save(cpdResults);

        return new ResponseEntity(HttpStatus.OK);
    }
}
