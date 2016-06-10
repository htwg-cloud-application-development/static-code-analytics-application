package de.htwg.konstanz.cloud.service;

import javax.xml.parsers.ParserConfigurationException;
import java.net.MalformedURLException;
import java.io.FileNotFoundException;
import java.io.IOException;

import de.htwg.konstanz.cloud.model.ValidationData;
import lombok.Data;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.xml.sax.SAXException;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


// to create a RESTful Controller (add Controller and ResponseBody)
@RestController
@Data
public class PmdService {

    @RequestMapping(value = "/validate", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity validate(@RequestBody ValidationData data) {
        try {
            PMD oPMD = new PMD();
            String json = oPMD.startIt(data.getRepositoryUrl());
            return ResponseEntity.ok(json);
        } catch (ParserConfigurationException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (SAXException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (InvalidRemoteException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        } catch (TransportException e) {
            return ResponseEntity.status(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE).body(e.getMessage());
        } catch (GitAPIException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String info() {
        return "PMD-Service";
    }

    @RequestMapping(value = "/CPD validate", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity copypaste(@RequestBody ValidationData data) {
        try {
            CPD oCPD = new CPD();
            String json = oCPD.startIt(data.getRepositoryUrl());
            return ResponseEntity.ok(json);
        } catch (ParserConfigurationException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (SAXException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (InvalidRemoteException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        } catch (TransportException e) {
            return ResponseEntity.status(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE).body(e.getMessage());
        } catch (GitAPIException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
