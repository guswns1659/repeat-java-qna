package com.codessquad.qna;

import com.codessquad.qna.domain.QuestionController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("questions", QuestionController.questions);
        return "index";
    }
}
