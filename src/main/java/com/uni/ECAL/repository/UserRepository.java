package com.uni.ECAL.repository;

import com.uni.ECAL.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {

     User findByEmail(String email);

     User findByUsername(String username);

     User findByEmailAndPassword(String email, String password);

     List<User> findProfileByEmail(String email);
}
