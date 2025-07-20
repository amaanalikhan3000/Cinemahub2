package com.example.cinemahub2.repository.user;

import com.example.cinemahub2.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface userRepo extends JpaRepository<AppUser,Integer> {


    Optional<AppUser> findByEmail(String email);

//    Optional<AppUser> findByUserName(String username);

}
