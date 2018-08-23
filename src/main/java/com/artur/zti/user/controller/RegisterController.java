package com.artur.zti.user.controller;

import com.artur.zti.user.model.User;
import com.artur.zti.user.service.UserService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class RegisterController {
    private final static String PREFIX = "pages/";

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String register(User user, Model model) {
//        model.addAttribute("newUser", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerNewUser(@Valid User user, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            return "register";
        }
        List<ObjectError> validate = userService.validate(user);
        if(!validate.isEmpty()){
            validate.stream().forEach(bindingResult::addError);
            return "register";
        }

        userService.save(user);

        return "redirect:/login";
    }
}
