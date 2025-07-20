package com.example.cinemahub2.services;


import com.example.cinemahub2.entity.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import com.example.cinemahub2.repository.user.userRepo;
import java.util.Optional;


@Component
public class UserDetailsServiceImpl implements UserDetailsService {


    @Autowired
    private userRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<AppUser> optUser = userRepo.findByEmail(username);
        AppUser user = optUser.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + optUser));


        if(user!=null){
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getEmail())
                    .password(user.getPassword())
                    .roles(user.getRoles().toArray(new String[0]))
                    .build();


        }
        throw new UsernameNotFoundException("User not found with username: "+ username);
    }
}
