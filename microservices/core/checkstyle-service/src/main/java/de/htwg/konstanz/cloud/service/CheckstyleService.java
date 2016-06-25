package de.htwg.konstanz.cloud.service;

import de.htwg.konstanz.cloud.model.ValidationData;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

// to create a RESTful Controller (add Controller and ResponseBody)
@RestController
public class CheckstyleService {
    private static final Logger LOG = LoggerFactory.getLogger(CheckstyleService.class);

    @Value("${app.config.svn.ip}")
    private String sSvnServerIp;

    @Value("${app.config.checkstyle.rulepath}")
    private String sRuleSetPath;

    @Value("${spring.application.name}")
    private String serviceName;

    /**
     * executes the checkstyle validation
     * @param data - given post request data - expects a string repository link
     * @return - return a response entity within the json object and a http status code
     */
    @RequestMapping(value = "/validate", method = RequestMethod.POST,
            produces = "application/json", consumes = "application/json")
    public ResponseEntity validate(@RequestBody ValidationData data) {
        try {
            return ResponseEntity.ok(new Checkstyle(sSvnServerIp, sRuleSetPath).startIt(data.getRepository()));
        } catch (InvalidRemoteException e) {
            LOG.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * Get Request to test the response of the pmd service
     * @returna - simple string that indicates that the service is still alive
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String info() {
        return serviceName;
    }
}