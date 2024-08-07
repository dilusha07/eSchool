package com.uni.ECAL.controller;

import com.uni.ECAL.model.User;
import com.uni.ECAL.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/userlist")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<List<User>> getUsers() throws Exception {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }


    @GetMapping("/userprofileDetails/{email}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<List<User>> getProfileDetails(@PathVariable String email) throws Exception {
        List<User> users = userService.fetchProfileByEmail(email);
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }


    @PutMapping("/updateUser")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<User> updateUserProfile(@RequestBody User user) throws Exception {
        User userobj = userService.updateUserProfile(user);
        return new ResponseEntity<User>(userobj, HttpStatus.OK);
    }


    @GetMapping("/gettotalusers")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<List<Integer>> getTotalUsers() throws Exception {
        List<User> users = userService.getAllUsers();
        List<Integer> userCount = new ArrayList<>();
        userCount.add(users.size());
        return new ResponseEntity<List<Integer>>(userCount, HttpStatus.OK);
    }

}