package com.lambdateam.mycar.controller;

import com.lambdateam.mycar.model.UserModel;
import com.lambdateam.mycar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<UserModel> getUserById(@PathVariable (value = "id") Long id) {

        Optional<UserModel> userModel = userRepository.findById(id);

        return new ResponseEntity(userModel.get(), HttpStatus.OK);
    }

    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<List<UserModel>> listAllUsers() {

        List<UserModel> list = (List<UserModel>) userRepository.findAll();

        return new ResponseEntity<List<UserModel>>(list, HttpStatus.OK);
    }

    @PostMapping(value = "/", produces = "application/json")
    public ResponseEntity<UserModel> createUser(@RequestBody UserModel userModel) {

        UserModel userSaved = userRepository.save(userModel);

        return new ResponseEntity<UserModel>(userSaved, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<UserModel> fullUpdateUser(@RequestBody UserModel userModel) {

        UserModel userSaved = userRepository.save(userModel);

        return new ResponseEntity<UserModel>(userSaved, HttpStatus.OK);
    }

    //@PatchMapping("/drivers/{id}")
    //public Driver incrementalUpdateDriver(@PathVariable("id") Long id
    //, @RequestBody Driver driver) {
    //Driver foundDriver = findDriver(id);
    //foundDriver.setBirthDate(Optional.ofNullable(driver.getBirthDa
    //te()).orElse(foundDriver.getBirthDate()));
    //foundDriver.setName(Optional.ofNullable(driver.getName()).orEl
    //se(foundDriver.getName()));

    @DeleteMapping(value = "/{id}", produces = "application/text")
    public String deleteUser(@PathVariable("id") Long id) {

        userRepository.deleteById(id);

        return "Successfully deleted";
    }
}