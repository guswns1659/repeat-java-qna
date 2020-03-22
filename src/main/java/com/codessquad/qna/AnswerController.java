package com.codessquad.qna;

import com.codessquad.qna.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {

   @Autowired
   private QuestionRepository questionRepository;
   @Autowired
   private UserRepository userRepository;
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

}
