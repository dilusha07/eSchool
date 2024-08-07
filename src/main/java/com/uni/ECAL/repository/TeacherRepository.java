package com.uni.ECAL.repository;

import com.uni.ECAL.model.Teacher;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface TeacherRepository extends CrudRepository<Teacher, Integer> {

     Teacher findByEmail(String email);

     List<Teacher> findTeacherByEmail(String email);

     Teacher findByTeachername(String teachername);

     Teacher findByEmailAndPassword(String email, String password);

     List<Teacher> findProfileByEmail(String email);

    @Transactional
    @Modifying
    @Query(value = "update professor set status = 'accept' where email = ?1", nativeQuery = true)
     void updateStatus(String email);

    @Transactional
    @Modifying
    @Query(value = "update professor set status = 'reject' where email = ?1", nativeQuery = true)
     void rejectStatus(String email);


}
