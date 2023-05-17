package com.quiz.portal.controller.backend;

import com.quiz.portal.entity.*;
import com.quiz.portal.service.QuestionService;
import com.quiz.portal.service.QuizResultService;
import com.quiz.portal.service.QuizService;
import com.quiz.portal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
@RequestMapping("/backend/quizzes")
public class QuizAttemptController {

    private final UserService userService;

    private final QuizService quizService;

    private final QuestionService questionService;

    private final QuizResultService quizResultService;

    public void loadCommonData(Model model, Principal principal) {
        String username = principal.getName();
        User user = userService.getUserByEmailWithException(username);
        model.addAttribute("user", user);
    }

    @PreAuthorize("hasRole('NORMAL')")
    @GetMapping("/{id}/{title}/quiz-rules")
    public String viewBeforeQuizPage(@PathVariable UUID id, Model model, Principal principal) {
        loadCommonData(model, principal);
        model.addAttribute("quizzesActive", "active");
        Quiz quiz = this.quizService.getQuizById(id);
        model.addAttribute("title", quiz.getTitle());
        model.addAttribute("quiz", quiz);
        return "admin-template/normal/quiz-rules";
    }

    @PreAuthorize("hasRole('NORMAL')")
    @GetMapping("/{id}/{title}/start")
    public String viewAttemptQuiz(@PathVariable UUID id, Model model, Principal principal) {
        loadCommonData(model, principal);
        model.addAttribute("quizzesActive", "active");
        Quiz quiz = this.quizService.getQuizById(id);
        model.addAttribute("title", quiz.getTitle());
        model.addAttribute("quiz", quiz);
        List<Question> questions = this.questionService.getQuestionsByQuiz(id);
        model.addAttribute("questions", questions);

        QuestionResponse questionResponse = new QuestionResponse();
        questionResponse.setResponses(questions);
        model.addAttribute("questionResponse", questionResponse);
        return "admin-template/normal/start-quiz";
    }


    @PreAuthorize("hasRole('NORMAL')")
    @PostMapping("/{id}/{title}/result")
    public String evaluateQuiz(@PathVariable UUID id, @ModelAttribute("questionResponse") QuestionResponse questionResponse, Model model, Principal principal) {
        loadCommonData(model, principal);
        model.addAttribute("quizzesActive", "active");
        Quiz quiz = this.quizService.getQuizById(id);
        model.addAttribute("title", "Result -" + quiz.getTitle());
        model.addAttribute("quiz", quiz);

        try {
            int attemptedQuestions = quiz.getTotalQuestions();
            int correctAnswers = 0;
            List<Question> questions = new ArrayList<>();
            List<Question> responses = questionResponse.getResponses();
            for (Question response : responses) {
                Question question = this.questionService.getQuestionById(response.getId());

                if (response.getUserAnswer() == null) {
                    attemptedQuestions--;
                    question.setUserAnswer(null);
                    questions.add(question);
                    continue;
                }

                if (response.getUserAnswer().trim().equals(question.getAnswer().trim())) {
                    correctAnswers++;
                }

                question.setUserAnswer(response.getUserAnswer());
                questions.add(question);
            }

            int obtainedMarks = correctAnswers * (quiz.getTotalMarks() / quiz.getTotalQuestions());
            double successRate = (double) correctAnswers / (double) attemptedQuestions;
            int accuracy = (int) Math.floor((successRate * 100));

            QuizResult quizResult = new QuizResult();
            quizResult.setQuiz(quiz);
            quizResult.setUser(userService.getUserByEmail(principal.getName()));
            quizResult.setObtainedMarks(obtainedMarks);
            quizResult.setAttemptedQuestions(attemptedQuestions);
            quizResult.setCorrectAnswers(correctAnswers);
            quizResult.setAccuracy(accuracy);
            QuizResult savedQuizResult = this.quizResultService.createQuizResult(quizResult);

            model.addAttribute("quizResult", savedQuizResult);
            model.addAttribute("questions", questions);
            return "admin-template/normal/result";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "redirect:/backend";
        }
    }

}
