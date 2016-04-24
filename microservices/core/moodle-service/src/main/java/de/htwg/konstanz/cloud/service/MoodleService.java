package de.htwg.konstanz.cloud.service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


// to create a RESTful Controller (add Controller and ResponseBody)
@RestController
public class MoodleService {
    
    @RequestMapping("/moodle/hello")
    public String getProduct(@PathVariable int productId) {
        return "Hello Moodle";
    }
}
