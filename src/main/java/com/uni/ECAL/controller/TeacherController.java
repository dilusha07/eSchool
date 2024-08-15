package com.uni.ECAL.controller;

import com.uni.ECAL.model.Chapter;
import com.uni.ECAL.model.Course;
import com.uni.ECAL.model.Teacher;
import com.uni.ECAL.model.Wishlist;
import com.uni.ECAL.services.ChapterService;
import com.uni.ECAL.services.CourseService;
import com.uni.ECAL.services.TeacherService;
import com.uni.ECAL.services.WhishlistService;
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

    @Autowired
    private CourseService courseService;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private WhishlistService whishlistService;


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


    @PostMapping("/addCourse")
    @CrossOrigin(origins = "http://localhost:4200")
    public Course addNewCourse(@RequestBody Course course) throws Exception
    {
        Course courseObj = null;
        String newID = getNewID();
        course.setCourseid(newID);

        courseObj = courseService.addNewCourse(course);
        return courseObj;
    }


    @PostMapping("/addnewchapter")
    @CrossOrigin(origins = "http://localhost:4200")
    public Chapter addNewChapters(@RequestBody Chapter chapter) throws Exception
    {
        Chapter chapterObj = null;
        chapterObj = chapterService.addNewChapter(chapter);
        return chapterObj;
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


    @GetMapping("/courselistbyname/{coursename}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<List<Course>> getCourseListByName(@PathVariable String coursename) throws Exception
    {
        Course courseList = courseService.fetchCourseByCoursename(coursename);
        System.out.println(courseList.getCoursename()+" ");
        List<Course> courselist = new ArrayList<>();
        courselist.add(courseList);
        return new ResponseEntity<List<Course>>(courselist, HttpStatus.OK);
    }


    @GetMapping("/youtubecourselist")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<List<Course>> getYoutubeCourseList() throws Exception
    {
        List<Course> youtubeCourseList = courseService.fetchByCoursetype("Youtube");
//		for(Course list:youtubeCourseList)
//		{
//			System.out.println(list.getYoutubeurl());
//		}
        return new ResponseEntity<List<Course>>(youtubeCourseList, HttpStatus.OK);
    }



    @GetMapping("/websitecourselist")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<List<Course>> getWebsiteCourseList() throws Exception
    {
        List<Course> websiteCourseList = courseService.fetchByCoursetype("Website");
        return new ResponseEntity<List<Course>>(websiteCourseList, HttpStatus.OK);
    }




    @GetMapping("/getcoursename")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<List<String>> getCOurseNames() throws Exception {
        List<Course> courses = courseService.getAllCourses();
        List<String> courseNames = new ArrayList<>();
        for (Course obj : courses) {
            courseNames.add(obj.getCoursename());
        }
        return new ResponseEntity<>(courseNames, HttpStatus.OK);
    }


    @GetMapping("/gettotalwhishlist")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<List<Integer>> getTotalWishlist() throws Exception{
        List<Wishlist> wishlists = whishlistService.getAllLikedCourses();
        List<Integer> wishlistCount = new ArrayList<>();
        wishlistCount.add(wishlists.size());
        return new ResponseEntity<>(wishlistCount, HttpStatus.OK);
    }

    @GetMapping("/gettotalchapters")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<List<Integer>> getTotalChapters() throws Exception
    {
        List<Chapter> chapters = chapterService.getAllChapters();
        List<Integer> chaptersCount = new ArrayList<>();
        chaptersCount.add(chapters.size());
        return new ResponseEntity<List<Integer>>(chaptersCount, HttpStatus.OK);
    }

    @GetMapping("/gettotalcourses")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<List<Integer>> getTotalCourses() throws Exception
    {
        List<Course> courses = courseService.getAllCourses();
        List<Integer> coursesCount = new ArrayList<>();
        coursesCount.add(courses.size());
        return new ResponseEntity<List<Integer>>(coursesCount, HttpStatus.OK);
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
