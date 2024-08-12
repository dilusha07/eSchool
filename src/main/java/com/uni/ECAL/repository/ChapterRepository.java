package com.uni.ECAL.repository;

import com.uni.ECAL.model.Chapter;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChapterRepository extends CrudRepository<Chapter, Integer> {
    List<Chapter> findByCoursename(String Coursename);
}
