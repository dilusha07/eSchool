package com.uni.ECAL.controller;

import com.uni.ECAL.model.Teacher;
import com.uni.ECAL.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TeacherController {
    @Autowired
    private TeacherService teacherService;


    @GetMapping("/teacherlist")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<List<Teacher>> getTeacherList() throws Exception{
        List<Teacher> teachers = teacherService.getAllTeachers();
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }


    @GetMapping("/teacherlistbyemail/{email}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<List<Teacher>> getTeacherListByEmail(@PathVariable String email) throws Exception
    {
        List<Teacher> teachers = teacherService.getTeachersByEmail(email);
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }


    @PostMapping("/addTeacher")
    @CrossOrigin(origins = "http://localhost:4200")
    public Teacher addNewTeacher(@RequestBody Teacher teacher) throws Exception
    {
        Teacher teacherObj = null;
        String newID = getNewID();
        teacher.setTeacherid(newID);
        teacherObj = teacherService.addNewTeacher(teacher);
        teacherService.updateStatus(teacher.getEmail());
        return teacherObj;
    }


    @GetMapping("/acceptstatus/{email}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<List<String>> updateStatus(@PathVariable String email) throws Exception
    {
        teacherService.updateStatus(email);
        List<String> al=new ArrayList<>();
        al.add("accepted");
        return new ResponseEntity<>(al,HttpStatus.OK);
    }


    @GetMapping("/rejectstatus/{email}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<List<String>> reectStatus(@PathVariable String email) throws Exception
    {
        teacherService.rejectStatus(email);
        List<String> al=new ArrayList<>();
        al.add("rejected");
        return new ResponseEntity<>(al,HttpStatus.OK);
    }


    @GetMapping("/teacherprofileDetails/{email}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<List<Teacher>> getProfileDetails(@PathVariable String email){
        List<Teacher> teachers = teacherService.fetchProfileByEmail(email);
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }


    @PutMapping("/updateteacher")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Teacher> updateTeacherProfile(@RequestBody Teacher teacher){
        Teacher teacherobj = teacherService.updateTeacherProfile(teacher);
        return new ResponseEntity<>(teacherobj, HttpStatus.OK);
    }


    @GetMapping("/gettotalteachers")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<List<Integer>>getTotalTeachers() throws Exception{
        List<Teacher> teachers = teacherService.getAllTeachers();
        List<Integer>teacherCount = new ArrayList<>();
        teacherCount.add(teachers.size());
        return new ResponseEntity<>(teacherCount, HttpStatus.OK);
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
