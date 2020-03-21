package com.codessquad.qna;

import com.codessquad.qna.domain.UpdateUserDTO;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.domain.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("")
    public String users(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/form")
    public String form() {
        return "user/form";
    }

    @PostMapping("/create")
    public String create(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/{userId}")
    public String profile(@PathVariable String userId, Model model) {
        User user = userRepository.findByUserId(userId).orElseThrow(() ->
                new IllegalStateException("No User"));
        model.addAttribute("user", user);
        return "user/profile";
    }

    @GetMapping("/{id}/updateForm")
    public ModelAndView updateForm(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new IllegalStateException("No User"));
        model.addAttribute("user", user);
        return new ModelAndView("user/updateForm");
    }

    @PostMapping("/{id}")
    public ModelAndView update(@PathVariable Long id, UpdateUserDTO updateUserDTO) {
        ModelAndView modelAndView = new ModelAndView("redirect:/users");

        if (updateUserDTO.isCheckFail()) {
            return modelAndView;
        }

        User user = userRepository.findById(id).orElseThrow(() ->
                new IllegalStateException("No User"));
        user.update(updateUserDTO);
        userRepository.save(user);
        return modelAndView;
    }
}
