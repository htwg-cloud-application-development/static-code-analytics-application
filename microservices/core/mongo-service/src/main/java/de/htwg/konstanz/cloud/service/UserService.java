package de.htwg.konstanz.cloud.service;

import de.htwg.konstanz.cloud.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserService {

    @Autowired
    MongoOperations mongo;

    @Autowired
    UserRepository userRepo;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void addUser(@RequestBody final User user) {

        userRepo.save(user);
    }

    @RequestMapping(value = "{userId}", method = RequestMethod.GET)
    public User getUser(@PathVariable final String userId) {
        return userRepo.findOne(userId);
    }
}
