package com.finance.controllers;

import ch.qos.logback.core.model.Model;
import com.finance.configs.AuthProvider;
import com.finance.models.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    AuthProvider authProvider;
//    @PostMapping("/login")
//    public String postLogin(@RequestParam String login, @RequestParam String password, Model model){
//
//        return "redirect:/user";
//    }


    @GetMapping("/login/redirect")
    public String postLogin(){


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        var userRole = (UserRole)(authentication.getAuthorities().toArray()[0]);
//
//        switch (userRole){
//            case ROLE_USER -> {

                return "redirect:/stat/";
//            }
//        }
//        return "redirect:/login";
    }



    @GetMapping("/login")
    public String login() {
        return "login"; // Assuming you have a login page named "login.html"
    }

}
