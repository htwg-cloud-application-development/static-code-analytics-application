package de.htwg.konstanz.cloud.service;

import de.htwg.konstanz.cloud.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserService {

    @Autowired
    MongoOperations mongo;

    @Autowired
    UserRepository userRepo;

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public void addUser (@RequestParam User user){

        userRepo.save(user);
    }

    @RequestMapping(value ="/info", method = RequestMethod.GET)
    public String info(){
        return "Hello";
    }


}
