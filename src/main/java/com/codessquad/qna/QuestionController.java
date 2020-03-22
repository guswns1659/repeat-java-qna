package com.codessquad.qna;

import com.codessquad.qna.domain.AnswerRepository;
import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.QuestionRepository;
import com.codessquad.qna.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    private Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;


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
        Question question = new Question(user, title, contents);
        questionRepository.save(question);
        return new ModelAndView("redirect:/");
    }

    @GetMapping("/{questionId}")
    public ModelAndView show(@PathVariable Long questionId, ModelAndView modelAndView) {
        Question question = questionRepository.findById(questionId).orElseThrow(() ->
                new IllegalStateException("No Question"));
        modelAndView.setViewName("question/show");
        modelAndView.addObject("question", question);
        modelAndView.addObject("answers", answerRepository.findAllByQuestionIdAndDeletedFalse(questionId));
        return modelAndView;
    }

    @GetMapping("/{questionId}/updateForm")
    public ModelAndView updateForm(@PathVariable Long questionId,
                                   HttpSession httpSession,
                                   ModelAndView modelAndView) {
        User user = HttpUtils.getSessionedUser(httpSession);
        if (HttpUtils.isNotLoginUser(user)) {
            return new ModelAndView("user/login");
        }
        Question question = questionRepository.findById(questionId).orElseThrow(() ->
                new IllegalStateException("No Question"));
        if (question.isNotMatchedUser(user)) {
            return new ModelAndView("user/login");
        }
        modelAndView.setViewName("question/updateForm");
        modelAndView.addObject("question", question);
        return modelAndView;
    }

    @PutMapping("/{questionId}/{writer}")
    public ModelAndView update(@PathVariable Long questionId,
                               String title, String contents,
                               ModelAndView modelAndView,
                               HttpSession httpSession) {
        User user = HttpUtils.getSessionedUser(httpSession);
        if (HttpUtils.isNotLoginUser(user)) {
            return new ModelAndView("user/login");
        }
        Question question = questionRepository.findById(questionId).orElseThrow(() ->
                new IllegalStateException("No Question"));
        if (question.isNotMatchedUser(user)) {
            return new ModelAndView("user/login");
        }

        question.update(title, contents);
        questionRepository.save(question);

        modelAndView.setViewName("redirect:/questions/{questionId}");
        return modelAndView;
    }

    @DeleteMapping("/{questionId}")
    public ModelAndView delete(@PathVariable Long questionId,
                               ModelAndView modelAndView,
                               HttpSession httpSession) {

        User user = HttpUtils.getSessionedUser(httpSession);
        if (HttpUtils.isNotLoginUser(user)) {
            return new ModelAndView("user/login");
        }
        Question question = questionRepository.findById(questionId).orElseThrow(() ->
                new IllegalStateException("No Question"));
        if (question.isNotMatchedUser(user)) {
            return new ModelAndView("user/login");
        }

        question.delete();
        questionRepository.save(question);
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }
}
