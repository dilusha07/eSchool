package com.uni.ECAL.controller;

import com.uni.ECAL.model.Teacher;
import com.uni.ECAL.model.User;
import com.uni.ECAL.services.TeacherService;
import com.uni.ECAL.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private TeacherService teacherService;

    @GetMapping("/")
    public String welcomeMessage() {
        return "Welcome to ECAL !!!";
    }

    @PostMapping("/loginuser")
    @CrossOrigin(origins = "http://localhost:4200")
    public User loginUser(@RequestBody User user) throws Exception {
        String currEmail = user.getEmail();
        String currPassword = user.getPassword();

        User userObj = null;
        if (currEmail != null && currPassword != null) {
            userObj = userService.fetchUserByEmailAndPassword(currEmail, currPassword);
        }
        if (userObj == null) {

                throw new Exception("User does not exists!!! Please enter valid credentials...");
            }
            return userObj;
        }


    @PostMapping("/loginteacher")
    @CrossOrigin(origins = "http://localhost:4200")
    public Teacher loginTeacher(@RequestBody Teacher teacher) throws Exception{
        String currEmail = teacher.getEmail();
        String currPassword = teacher.getPassword();

        Teacher teacherObj = null;
        if(currEmail != null && currPassword != null){
            teacherObj = teacherService.fetchTeacherByEmailAndPassword(currEmail, currPassword);
        }
        if(teacherObj == null){
            throw new Exception("Professor does not exists!!! Please enter valid credentials...");
        }
            return teacherObj;
        }



    }

