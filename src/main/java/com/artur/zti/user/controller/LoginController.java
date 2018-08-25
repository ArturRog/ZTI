package com.artur.zti.user.controller;


import com.artur.zti.user.model.User;
import com.artur.zti.user.model.UserRole;
import com.artur.zti.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }



}
