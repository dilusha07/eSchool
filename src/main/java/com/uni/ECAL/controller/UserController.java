package com.uni.ECAL.controller;

import com.uni.ECAL.model.*;
import com.uni.ECAL.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private WhishlistService wishlistService;

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


    @GetMapping("/gettotalenrollmentcount")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<List<Integer>> getTotalEnrollmentcount() throws Exception
    {
        List<Enrollment> enrollments = enrollmentService.getAllEnrollments();
        int count = 0;
        for(Enrollment enrollmentObj : enrollments)
        {
            count += Integer.parseInt(enrollmentObj.getEnrolledcount());
        }
        List<Integer> enrollmentsCount = new ArrayList<>();
        enrollmentsCount.add(count);
        return new ResponseEntity<List<Integer>>(enrollmentsCount, HttpStatus.OK);
    }

    @GetMapping("/gettotalenrollments")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<List<Integer>> getTotalEnrollments() throws Exception
    {
        List<Enrollment> enrollments = enrollmentService.getAllEnrollments();
        List<Integer> enrollmentsCount = new ArrayList<>();
        enrollmentsCount.add(enrollments.size());
        return new ResponseEntity<List<Integer>>(enrollmentsCount, HttpStatus.OK);
    }


    @GetMapping("/getenrollmentbyemail/{email}/{role}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<List<Enrollment>> getEnrollmentsByEmail(@PathVariable String email, @PathVariable String role) throws Exception
    {
        User userObj;
        Teacher teacherObj;
        String enrolledUser = "";
        if(role.equalsIgnoreCase("user"))
        {
            userObj = userService.fetchUserByEmail(email);
            enrolledUser = userObj.getUsername();
        }
        else if(role.equalsIgnoreCase("teacher"))
        {
            teacherObj = teacherService.fetchTeacherByEmail(email);
            enrolledUser = teacherObj.getTeachername();
        }

        List<Enrollment> enrollments = enrollmentService.fetchByEnrolledusername(enrolledUser);
        return new ResponseEntity<List<Enrollment>>(enrollments, HttpStatus.OK);
    }



    @GetMapping("/getenrollmentstatus/{coursename}/{email}/{role}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Set<String>> getEnrollmentStatus(@PathVariable String coursename, @PathVariable String email, @PathVariable String role) throws Exception
    {
        List<Enrollment> enrollments = enrollmentService.getAllEnrollments();
        User userObj;
        Teacher teacherObj;
        String enrolledUser = "";
        if(role.equalsIgnoreCase("user"))
        {
            userObj = userService.fetchUserByEmail(email);
            enrolledUser = userObj.getUsername();
        }
        else if(role.equalsIgnoreCase("professor"))
        {
            teacherObj = teacherService.fetchTeacherByEmail(email);
            enrolledUser = teacherObj.getTeachername();
        }

        Set<String> enrollmentStatus = new LinkedHashSet<>();
        int flag = 0;
        OUTER:for(Enrollment enrollmentObj : enrollments)
        {
            if(enrollmentObj.getCoursename().equalsIgnoreCase(coursename) && enrollmentObj.getEnrolledusername().equalsIgnoreCase(enrolledUser))
            {
                enrollmentStatus.add("enrolled");
                flag = 1;
                break OUTER;
            }
        }
        if(flag == 0)
            enrollmentStatus.add("notenrolled");
        return new ResponseEntity<Set<String>>(enrollmentStatus, HttpStatus.OK);
    }


    @GetMapping("/getchapterlistbycoursename/{coursename}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<List<Chapter>> getChapterListByCoursename(@PathVariable String coursename) throws Exception
    {
        List<Chapter> chapterLists = chapterService.fetchByCoursename(coursename);
        if(chapterLists.size()==0)
        {
            Chapter obj1 = new Chapter();
            obj1.setChapter1name("");
            obj1.setChapter2name("");
            obj1.setChapter3name("");
            obj1.setChapter4name("");
            obj1.setChapter5name("");
            obj1.setChapter6name("");
            obj1.setChapter7name("");
            obj1.setChapter8name("");
            chapterLists.add(obj1);
        }
        return new ResponseEntity<List<Chapter>>(chapterLists, HttpStatus.OK);
    }


    @PostMapping("/enrollnewcourse/{email}/{role}")
    @CrossOrigin(origins = "http://localhost:4200")
    public String enrollNewCourse(@RequestBody Enrollment enrollment, @PathVariable String email, @PathVariable String role) throws Exception
    {
        String enrolledUserName = "",enrolledUserID = "";

        if(role.equalsIgnoreCase("user"))
        {
            List<User> users = userService.getAllUsers();
            for(User userObj:users)
            {
                if(userObj.getEmail().equalsIgnoreCase(email))
                {
                    enrolledUserName = userObj.getUsername();
                    enrolledUserID = userObj.getUserid();
                    enrollment.setEnrolleduserid(enrolledUserID);
                    enrollment.setEnrolledusername(enrolledUserName);
                    break;
                }
            }
        }
        else if(role.equalsIgnoreCase("professor"))
        {
            List<Teacher> teachers = teacherService.getAllTeachers();
            for(Teacher teacherObj:teachers)
            {
                if(teacherObj.getEmail().equalsIgnoreCase(email))
                {
                    enrolledUserName = teacherObj.getTeachername();
                    enrolledUserID = teacherObj.getTeacherid();
                    enrollment.setEnrolleduserid(enrolledUserID);
                    enrollment.setEnrolledusername(enrolledUserName);
                    break;
                }
            }
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String todayDate = formatter.format(date);
        enrollment.setEnrolleddate(todayDate);

        Enrollment enrollmentObj = null;
        enrollmentObj = enrollmentService.saveEnrollment(enrollment);
        System.out.println(enrollmentObj);

        List<Enrollment> enrollments = enrollmentService.getAllEnrollments();
        Map<String, Integer> enrolledCount = new LinkedHashMap<>();
        for(Enrollment enrollObj : enrollments)
        {
            String courseName = enrollObj.getCoursename();
            if(enrolledCount.containsKey(courseName))
                enrolledCount.put(courseName, enrolledCount.get(courseName)+1);
            else
                enrolledCount.put(courseName, 1);
        }
        for(Map.Entry<String, Integer> obj : enrolledCount.entrySet())
        {
            if(obj.getKey().equalsIgnoreCase(enrollment.getCoursename()))
            {
                enrollmentService.updateEnrolledcount(obj.getKey(), obj.getValue());
                courseService.updateEnrolledcount(obj.getKey(), obj.getValue());
            }
        }

        return "done";
    }


    @PostMapping("/addtowishlist")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Wishlist> addNewCourse(@RequestBody Wishlist wishlist) throws Exception
    {
        Wishlist wishlistObj = null;
        wishlistObj = wishlistService.addToWishlist(wishlist);
        return new ResponseEntity<Wishlist>(wishlistObj, HttpStatus.OK);
    }

    @GetMapping("/getwishliststatus/{coursename}/{email}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Set<String>> getWishlistStatus(@PathVariable String coursename, @PathVariable String email) throws Exception
    {
        List<Wishlist> wishlists = wishlistService.getAllLikedCourses();
        Set<String> wishlistsStatus = new LinkedHashSet<>();
        int flag = 0;
        OUTER:for(Wishlist wishlistsObj : wishlists)
        {
            if(wishlistsObj.getCoursename().equalsIgnoreCase(coursename) && wishlistsObj.getLikeduser().equalsIgnoreCase(email))
            {
                wishlistsStatus.add("liked");
                flag = 1;
                break OUTER;
            }
        }
        if(flag == 0)
            wishlistsStatus.add("notliked");
        return new ResponseEntity<Set<String>>(wishlistsStatus, HttpStatus.OK);
    }

    @GetMapping("/getallwishlist")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<List<Wishlist>> getAllWislist() throws Exception
    {
        List<Wishlist> Wishlists = wishlistService.getAllLikedCourses();
        return new ResponseEntity<List<Wishlist>>(Wishlists, HttpStatus.OK);
    }

    @GetMapping("/getwishlistbyemail/{email}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<List<Wishlist>> getWishlistByEmail(@PathVariable String email) throws Exception
    {
        List<Wishlist> Wishlists = wishlistService.fetchByLikeduser(email);
        return new ResponseEntity<List<Wishlist>>(Wishlists, HttpStatus.OK);
    }


}