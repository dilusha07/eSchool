package com.uni.ECAL.controller;

import com.uni.ECAL.model.Teacher;
import com.uni.ECAL.model.User;
import com.uni.ECAL.services.TeacherService;
import com.uni.ECAL.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private TeacherService teacherService;

    @PostMapping("/registeruser")
    @CrossOrigin(origins = "http://localhost:4200")
    public User registerUser(@RequestBody User user) throws Exception
    {
        String currEmail = user.getEmail();
        String newID = getNewID();
        user.setUserid(newID);

        if(currEmail != null || !"".equals(currEmail))
        {
            User userObj = userService.fetchUserByEmail(currEmail);
            if(userObj != null)
            {
                throw new Exception("User with "+currEmail+" already exists !!!");
            }
        }
        User userObj = null;
        userObj = userService.saveUser(user);
        return userObj;
    }

    @PostMapping("/registerTeacher")
    @CrossOrigin(origins = "http://localhost:4200")
    public Teacher registerTeacher(@RequestBody Teacher teacher) throws Exception{
        String currEmail = teacher.getEmail();
        String  newId = getNewID();
        teacher.setTeacherid(newId);

        if(currEmail != null || !"".equals(currEmail)){
            Teacher teacherObj = teacherService.fetchTeacherByEmail(currEmail);
            if(teacherObj != null){
                throw new Exception("\"Professor with \"+currEmail+\" already exists !!!");
            }
        }

        Teacher teacherObj = null;
        teacherObj = teacherService.saveTeacher(teacher);
        return  teacherObj;
    }

    public String getNewID()
    {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"+"0123456789"+"abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 12; i++)
        {
            int index = (int)(AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
    }

}
