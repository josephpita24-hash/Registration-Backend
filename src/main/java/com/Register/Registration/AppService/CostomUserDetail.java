package com.Register.Registration.AppService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Register.Registration.Repository.userDataRepository;
import com.Register.Registration.User.userData;

@Service
public class CostomUserDetail implements UserDetailsService{

    @Autowired
    private userDataRepository repository;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
              
        userData  users = repository.findByUsermail(userEmail).orElseThrow(() -> new UsernameNotFoundException("User name not Found!"));

        UserDetails userDetails = User
                      .withUsername(users.getuserEmail())
                       .password(users.getuserPassword())
                       .roles(users.getuserRole())
                       .build();
           
    return userDetails;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
         return new BCryptPasswordEncoder();
    }

   


    
}
