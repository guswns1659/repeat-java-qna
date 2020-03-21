package com.codessquad.qna;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    @GetMapping("/form")
    public String form() {
        return "user/form";
    }

    @GetMapping("/profile")
    public String profile() {
        return "user/profile";
    }
}
