package com.codessquad.qna.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    private Logger logger = LoggerFactory.getLogger(QuestionController.class);

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @GetMapping("/form")
    public String form() {
        return "question/form";
    }

    @PostMapping("/create")
    public ModelAndView create(Question question) {
        logger.info("question : {} ", question);
        questionRepository.save(question);
        return new ModelAndView("redirect:/");
    }

    @GetMapping("/{questionId}")
    public ModelAndView show(@PathVariable Long questionId, ModelAndView modelAndView) {
        Question question = questionRepository.findById(questionId).orElseThrow(() ->
                new IllegalStateException("No Question"));
        modelAndView.setViewName("question/show");
        modelAndView.addObject("question", question);
        return modelAndView;
    }

    @GetMapping("/updateForm")
    public ModelAndView updateForm() {
        return new ModelAndView("question/updateForm");
    }
}
