package com.example.cinemahub2.repository.user;

import com.example.cinemahub2.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface userRepo extends JpaRepository<AppUser,Integer> {

    // Custom query method based on email
   // Optional<AppUser> findByEmail(String email);

    AppUser findByEmail(String username);
}
