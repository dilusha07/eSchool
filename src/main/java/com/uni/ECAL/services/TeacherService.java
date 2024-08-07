package com.uni.ECAL.services;

import com.uni.ECAL.model.Teacher;
import com.uni.ECAL.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepo;

    public Teacher saveTeacher(Teacher teacher){return teacherRepo.save(teacher);}

    public Teacher addNewTeacher(Teacher teacher){return teacherRepo.save(teacher);}

    public Teacher updateTeacherProfile(Teacher teacher){return teacherRepo.save(teacher);}

    public List<Teacher> getAllTeachers(){ return (List<Teacher>) teacherRepo.findAll();}

    public List<Teacher> getTeacherListByEmail(String email){return (List<Teacher>)teacherRepo.findTeacherByEmail(email);}

    public Teacher fetchTeacherByEmail(String email){return teacherRepo.findByEmail(email);}

    public Teacher fetchTeacherByEmailAndPassword(String email, String password){return teacherRepo.findByEmailAndPassword(email, password);}

    public List<Teacher> fetchProfileByEmail(String email){return (List<Teacher>) teacherRepo.findProfileByEmail(email);}

    public void updateStatus(String email){teacherRepo.updateStatus(email);}

    public void rejectStatus(String email){ teacherRepo.rejectStatus(email);}

    public List<Teacher> getTeachersByEmail(String email){return teacherRepo.findTeacherByEmail(email);}
}
