package com.codessquad.qna;

import com.codessquad.qna.domain.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
    private final QuestionRepository questionRepository;

    @Autowired
    public HomeController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @GetMapping("")
    public ModelAndView index(ModelAndView modelAndView) {
        modelAndView.setViewName("index");
        modelAndView.addObject("questions", questionRepository.findAll());
//        modelAndView.addObject("user", questionRepository.findAll());
        return modelAndView;
    }
}
