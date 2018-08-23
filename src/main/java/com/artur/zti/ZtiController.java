package com.artur.zti;

import com.artur.zti.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ZtiController {
    private static final Logger logger = LoggerFactory.getLogger(ZtiController.class);
    @Autowired
    UserService userService;
    @Value("${spring.application.name}")
    private String appName;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @RequestMapping(value = "/")
    public String emptyPath(Model model) {
        return "redirect:/zti";
    }

    @RequestMapping(value = "/zti")
    public String dashboard() {
        logger.info("BaseController - redirect from /");
        return "pages/index";
    }
}

