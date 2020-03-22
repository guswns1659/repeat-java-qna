package com.codessquad.qna;

import com.codessquad.qna.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;


@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {
    private Logger logger = LoggerFactory.getLogger(ApiAnswerController.class);

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @PostMapping("")
    public ModelAndView create(@PathVariable Long questionId,
                               String contents,
                               ModelAndView modelAndView,
                               HttpSession httpSession) {

        User user = HttpUtils.getSessionedUser(httpSession);
        if (HttpUtils.isNotLoginUser(user)) {
            return new ModelAndView("user/login");
        }
        Question question = questionRepository.findById(questionId).orElseThrow(() ->
                new IllegalStateException("No Question"));
        Answer answer = new Answer(user, question, contents);
        answerRepository.save(answer);

        modelAndView.setViewName("redirect:/questions/{questionId}");
        return modelAndView;
    }

    @DeleteMapping("/{answerId}")
    public ModelAndView delete(@PathVariable Long answerId,
                               ModelAndView modelAndView,
                               HttpSession httpSession) {
        User user = HttpUtils.getSessionedUser(httpSession);
        if (HttpUtils.isNotLoginUser(user)) {
            return new ModelAndView("user/login");
        }
        Answer answer = answerRepository.findById(answerId).orElseThrow(() ->
                new IllegalStateException("No Answer"));
        if (answer.isNotMatchedUser(user)) {
            logger.info("answer : {}", answer);
            logger.info("user : {}", user);

            return new ModelAndView("user/login");
        }
        answer.delete();
        answerRepository.save(answer);
        modelAndView.setViewName("redirect:/questions/{questionId}");
        return modelAndView;
    }
}
