package com.codessquad.qna;

import com.codessquad.qna.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    private List<User> users = new ArrayList<>();

    @GetMapping("")
    public String users(Model model) {
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/form")
    public String form() {
        return "user/form";
    }

    @PostMapping("/create")
    public String create(User user) {
        logger.info("user : {}", user);

        user.setId((long) users.size() + 1);
        users.add(user);
        return "redirect:/users";
    }

    @GetMapping("/profile")
    public String profile() {
        return "user/profile";
    }
}
