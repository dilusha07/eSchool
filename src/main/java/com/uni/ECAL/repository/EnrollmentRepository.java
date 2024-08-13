package com.uni.ECAL.repository;

import com.uni.ECAL.model.Enrollment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface EnrollmentRepository extends CrudRepository<Enrollment, Integer> {

    Enrollment findByCoursename(String coursename);

    Enrollment findByCourseid(String courseid);

    List<Enrollment> findByEnrolledusername(String enrolledusername);

    List<Enrollment> findByEnrolleduserid(String enrolleduserid);

    List<Enrollment> findByEnrolledusertype(String enrolledusertype);

    List<Enrollment> findByInstructorname(String instructorname);

    List<Enrollment> findByInstructorinstitution(String instructorinstitution);

    List<Enrollment> findByEnrolleddate(String enrolleddate);

    List<Enrollment> findByCoursetype(String coursetype);

    List<Enrollment> findByYoutubeurl(String youtubeurl);

    List<Enrollment> findByWebsiteurl(String websiteurl);

    List<Enrollment> findBySkilllevel(String skilllevel);

    List<Enrollment> findByLanguage(String language);

    @Transactional
    @Modifying
    @Query(value = "update enrollment set enrolledcount = ?1 where coursename = ?2",nativeQuery = true)
    void updateEnrolledcount(int enrolledcount, String coursename);


}
