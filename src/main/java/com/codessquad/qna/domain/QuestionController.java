package com.codessquad.qna.domain;

import com.codessquad.qna.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

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
    public ModelAndView form(HttpSession httpSession,
                             ModelAndView modelAndView) {
        modelAndView.setViewName("question/form");
        User user = HttpUtils.getSessionedUser(httpSession);
        if (HttpUtils.isNotLoginUser(user)) {
            return new ModelAndView("user/login");
        }
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView create(String title, String contents,
                               HttpSession httpSession) {
        User user = HttpUtils.getSessionedUser(httpSession);
        if (HttpUtils.isNotLoginUser(user)) {
            return new ModelAndView("user/login");
        }
        Question question = new Question(user.getName(), title, contents);
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
