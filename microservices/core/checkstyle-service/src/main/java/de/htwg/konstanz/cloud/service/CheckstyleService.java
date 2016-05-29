package de.htwg.konstanz.cloud.service;

import javax.xml.parsers.ParserConfigurationException;
import java.net.MalformedURLException;
import java.io.FileNotFoundException;
import java.io.IOException;

import de.htwg.konstanz.cloud.model.ValidationData;
import org.xml.sax.SAXException;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


// to create a RESTful Controller (add Controller and ResponseBody)
@RestController
public class CheckstyleService {

    @RequestMapping(value = "/validate", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity validate(@RequestBody ValidationData data) {
        try {
            CheckGitRep oCheckGitRep = new CheckGitRep();
            String json = oCheckGitRep.startIt(data.getRepositoryUrl());
            return ResponseEntity.ok(json);
        } catch (ParserConfigurationException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (SAXException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String info() {
        return "Checkstyle-Service";
    }

}
