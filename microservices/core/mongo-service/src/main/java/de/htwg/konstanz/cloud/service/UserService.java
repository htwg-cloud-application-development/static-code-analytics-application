package de.htwg.konstanz.cloud.service;

import de.htwg.konstanz.cloud.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserService {

    @Autowired
    MongoOperations mongo;

    @Autowired
    UserRepository userRepo;

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public void addUser (@RequestBody User user){

        userRepo.save(user);
    }

    @RequestMapping(value ="/info", method = RequestMethod.GET)
    public String info(){
        return "Hello";
    }


}
