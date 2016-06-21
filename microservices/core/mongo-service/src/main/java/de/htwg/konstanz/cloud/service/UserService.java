package de.htwg.konstanz.cloud.service;

import de.htwg.konstanz.cloud.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserService {

    @Autowired
    MongoOperations mongo;

    @Autowired
    UserRepository userRepo;

    //creates user
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity addUser(@RequestBody final User user) {

        userRepo.save(user);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "{userId}", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable final String userId) {
        return new ResponseEntity<User>(userRepo.findOne(userId), HttpStatus.OK);
    }
}
