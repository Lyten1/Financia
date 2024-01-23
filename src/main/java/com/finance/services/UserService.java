package com.finance.services;


import com.finance.models.User;
import com.finance.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    public UserDetails loadUserByUsername(String username) throws NullPointerException, UsernameNotFoundException {
        User userUnit = findByUsername(username);

           return new org.springframework.security.core.userdetails.User(
                   userUnit.getUsername(),
                   userUnit.getPassword(),
                   userUnit.getAuthorities()
           );

    }



    public User findByUsername(String username) {
        return userRepo.findByUsername(username).orElse(null);
    }

}
