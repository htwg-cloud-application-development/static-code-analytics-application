package de.htwg.konstanz.cloud.service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


// to create a RESTful Controller (add Controller and ResponseBody)
@RestController
public class CheckstyleService {

    @RequestMapping("/checkstyle")
    public String hello() {
    	String githubUrl = "";
		CheckGitRep oCheckGitRep = new CheckGitRep();
		oCheckGitRep.startIt();
        return "Hello Moodle";
    }

    @RequestMapping("/info")
    public String info() {
        return "Helloooooooo :-)";
    }
	
}
