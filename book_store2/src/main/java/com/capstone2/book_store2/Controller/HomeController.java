package com.capstone2.book_store2.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    //these handles the http request from home.html

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/auth/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/auth/register")
    public String registerPage() {
        return "register";
    }
}



