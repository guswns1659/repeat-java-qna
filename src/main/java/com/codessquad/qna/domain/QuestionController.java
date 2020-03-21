package com.codessquad.qna.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    public static List<Question> questions = new ArrayList<>();
    private Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @GetMapping("/form")
    public String form() {
        return "question/form";
    }

    @PostMapping("/create")
    public String create(Question question) {
        logger.info("question : {} ", question);
        question.setId((long) questions.size() + 1);
        questions.add(question);
        return "redirect:/";
    }

    @GetMapping("/{questionId}")
    public String show(@PathVariable Long questionId, Model model) {
        for (Question each : questions) {
            if (each.isSameId(questionId)) model.addAttribute("question", each);
        }
        return "question/show";
    }
}
