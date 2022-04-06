package com.example.usersCRUD.controllers;

import com.example.usersCRUD.models.User;
import com.example.usersCRUD.repositories.UserRepository;
import com.example.usersCRUD.services.SequenceGeneratorService;
import com.example.usersCRUD.validations.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    SequenceGeneratorService sequenceGenerator;

    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        try {
            String email = user.getEmail();
            if (!UserValidation.isValidEmailAddress(email)) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }

            String name = user.getName();
            Optional<User> user1 = Optional.ofNullable(userRepository.getUserByNameOrEmail(name, email));

            if (user1.isPresent()) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }

            user.setId(sequenceGenerator.generateSequence(User.SEQUENCE_NAME));
            User saveUser = userRepository.save(user);
            return new ResponseEntity<>(saveUser, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/findAll", produces = "application/json")
    public ResponseEntity<List<User>> getUsers() {
        try {
            List<User> allUsers = userRepository.findAll();

            if (allUsers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(allUsers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<List<User>> deleteUser(@PathVariable long id) {
        try {
            Optional<User> userData = userRepository.findById(id);
            if (userData.isPresent()) {
                userRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        try {
            Optional<User> userData = userRepository.findById(user.getId());
            if (userData.isEmpty() || !UserValidation.isValidEmailAddress(user.getEmail())) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            User saveUser = userRepository.save(user);
            return new ResponseEntity<>(saveUser, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
