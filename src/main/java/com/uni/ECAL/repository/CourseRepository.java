package com.uni.ECAL.repository;

import com.uni.ECAL.model.Course;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CourseRepository extends CrudRepository<Course, Integer> {
     Course findByCoursename(String coursename);

     Course findByCourseid(String courseid);

     List<Course> findByInstructorname(String instructorname);

     List<Course> findByInstructorinstitution(String instructorinstitution);

     List<Course> findByEnrolleddate(String enrolleddate);

     List<Course> findByCoursetype(String coursetype);

     List<Course> findByYoutubeurl(String youtubeurl);

     List<Course> findByWebsiteurl(String websiteurl);

     List<Course> findBySkilllevel(String skilllevel);

     List<Course> findByLanguage(String language);

    @Transactional
    @Modifying
    @Query(value = "update course set enrolledcount = ?1 where coursename = ?2",nativeQuery = true)
    public void updateEnrolledcount(int enrolledcount, String coursename);
}
