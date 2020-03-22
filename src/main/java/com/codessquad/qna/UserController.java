package com.codessquad.qna;

import com.codessquad.qna.domain.UpdateUserDTO;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.domain.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

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
    public ModelAndView updateForm(@PathVariable Long id,
                                   Model model,
                                   HttpSession httpSession) {
        User sessionedUser = (User) httpSession.getAttribute("sessionedUser");
        if (sessionedUser == null) {
            return new ModelAndView("user/login_failed");
        }

        User user = userRepository.findById(id).orElseThrow(() ->
                new IllegalStateException("No User"));

        if (sessionedUser.notMatchId(id)) {
            return new ModelAndView("user/login_failed");
        }

        model.addAttribute("user", user);
        return new ModelAndView("user/updateForm");
    }

    @PutMapping("/{id}")
    public ModelAndView update(@PathVariable Long id,
                               UpdateUserDTO updateUserDTO,
                               ModelAndView modelAndView) {
        modelAndView.setViewName("redirect:/users");

        if (updateUserDTO.isCheckFail()) {
            return modelAndView;
        }

        User user = userRepository.findById(id).orElseThrow(() ->
                new IllegalStateException("No User"));
        user.update(updateUserDTO);
        userRepository.save(user);
        return modelAndView;
    }

    @GetMapping("/loginForm")
    public ModelAndView loginForm() {
        return new ModelAndView("user/login");
    }

    @PostMapping("/login")
    public ModelAndView login(String userId, String password,
                              HttpSession httpSession,
                              ModelAndView modelAndView,
                              Model model) {
        User user = userRepository.findByUserId(userId).orElseThrow(() ->
                new IllegalStateException("No User"));
        if (user.notMatchPassword(password)) {
            return new ModelAndView("user/login_failed");
        }
        modelAndView.setViewName("redirect:/");
        httpSession.setAttribute("sessionedUser", user);
        return modelAndView;
    }

    @GetMapping("logout")
    public ModelAndView logout(HttpSession httpSession) {
        httpSession.removeAttribute(HttpUtils.SESSIONED_ID);
        // 쿠키 삭제하는 메서드.
        httpSession.invalidate();
        return new ModelAndView("redirect:/");
    }

}
