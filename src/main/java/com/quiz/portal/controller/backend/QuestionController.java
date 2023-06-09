package com.quiz.portal.controller.backend;

import com.quiz.portal.constsant.AppConstant;
import com.quiz.portal.entity.Question;
import com.quiz.portal.entity.Quiz;
import com.quiz.portal.entity.User;
import com.quiz.portal.helper.Message;
import com.quiz.portal.service.QuestionService;
import com.quiz.portal.service.QuizService;
import com.quiz.portal.service.UserService;
import jakarta.validation.Valid;
import java.security.Principal;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Controller
@RequestMapping("/backend")
public class QuestionController {

    private final QuestionService questionService;

    private final QuizService quizService;

    private final UserService userService;

    public void loadCommonData(Model model, Principal principal) {
        String username = principal.getName();
        User user = userService.getUserByEmailWithException(username);
        model.addAttribute("user", user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/quizzes/{quizId}/{title}/questions/page={pageNumber}")
    public String viewQuestionsPage(
            @PathVariable UUID quizId, @PathVariable int pageNumber, Model model, Principal principal) {
        loadCommonData(model, principal);
        model.addAttribute("title", "Question");
        model.addAttribute("quizzesActive", "active");

        String SORT_BY = "id";
        String SORT_DIRECTION = "asc";
        Sort sort = null;
        if (SORT_DIRECTION.equalsIgnoreCase("asc")) {
            sort = Sort.by(SORT_BY).ascending();
        } else if (SORT_DIRECTION.equalsIgnoreCase("desc")) {
            sort = Sort.by(SORT_BY).descending();
        }

        Pageable pageable = PageRequest.of(pageNumber, AppConstant.QUESTION_PAGE_SIZE, sort);
        Page<Question> questionPage = this.questionService.getQuestionsByQuiz(quizId, pageable);
        model.addAttribute("questionPage", questionPage);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("pageSize", AppConstant.QUESTION_PAGE_SIZE);
        model.addAttribute("totalPages", questionPage.getTotalPages());
        Quiz quiz = this.quizService.getQuizById(quizId);
        model.addAttribute("quiz", quiz);
        return "admin-template/admin/questions";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/quizzes/{quizId}/{title}/questions/add")
    public String viewNewQuestionPage(
            @PathVariable UUID quizId, Model model, Principal principal, RedirectAttributes redirectAttributes) {
        loadCommonData(model, principal);
        model.addAttribute("title", "Add Question");
        model.addAttribute("quizzesActive", "active");

        // To check if quiz is exist or not
        Quiz quiz;
        try {
            quiz = this.quizService.getQuizById(quizId);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(
                    "message", new Message("alert-danger", "Something went wrong! " + e.getMessage()));
            return "redirect:/backend/quizzes/page=0";
        }

        model.addAttribute("quiz", quiz);
        model.addAttribute("question", new Question());
        return "admin-template/admin/new-question";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/quizzes/{quizId}/{title}/questions/add")
    public String createQuestion(
            @Valid @ModelAttribute Question question,
            BindingResult bindingResult,
            @PathVariable UUID quizId,
            @RequestParam(value = "answer") String answer,
            RedirectAttributes redirectAttributes,
            Model model,
            Principal principal) {
        loadCommonData(model, principal);
        model.addAttribute("title", "Add Question");
        model.addAttribute("quizzesActive", "active");
        Quiz quiz = this.quizService.getQuizById(quizId);
        try {
            if (quiz.getTotalQuestions() == this.questionService.countByQuiz(quiz)) {
                throw new Exception("This quiz has already required number of questions.");
            }

            if (bindingResult.hasErrors()) {
                model.addAttribute("question", question);
                model.addAttribute("quiz", quiz);
                return "admin-template/admin/new-question";
            }

            if (answer.equals("A")) {
                answer = question.getOptionA();
            } else if (answer.equals("B")) {
                answer = question.getOptionB();
            } else if (answer.equals("C")) {
                answer = question.getOptionC();
            } else if (answer.equals("D")) {
                answer = question.getOptionD();
            }

            question.setAnswer(answer);
            question.setQuiz(quiz);
            this.questionService.createQuestion(question);
            redirectAttributes.addFlashAttribute(
                    "message", new Message("alert-primary", "Question is added successfully."));
            return "redirect:/backend/quizzes/" + quiz.getId() + "/" + quiz.getTitle() + "/questions/add";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(
                    "message", new Message("alert-primary", "Something went wrong! " + e.getMessage()));
            return "redirect:/backend/quizzes/" + quiz.getId() + "/" + quiz.getTitle() + "/questions/add";
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/quizzes/{quizId}/{title}/questions/{questionId}/edit")
    public String viewEditQuestionPage(
            @PathVariable UUID quizId,
            @PathVariable UUID questionId,
            Model model,
            Principal principal,
            RedirectAttributes redirectAttributes) {
        // To check if quiz is exist or not
        Quiz quiz;
        try {
            quiz = this.quizService.getQuizById(quizId);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(
                    "message", new Message("alert-danger", "Something went wrong! " + e.getMessage()));
            return "redirect:/backend/quizzes/page=0";
        }

        try {
            loadCommonData(model, principal);
            model.addAttribute("title", "Edit Question");
            model.addAttribute("quizzesActive", "active");
            model.addAttribute("quiz", quiz);
            Question question = this.questionService.getQuestionById(questionId);
            model.addAttribute("question", question);
            return "admin-template/admin/edit-question";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(
                    "message", new Message("alert-danger", "Something went wrong! " + e.getMessage()));
            return "redirect:/backend/quizzes/" + quiz.getId() + "/" + quiz.getTitle() + "/questions/page=0";
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/quizzes/{quizId}/{title}/questions/{questionId}/edit")
    public String editQuestion(
            @Valid Question question,
            BindingResult bindingResult,
            @PathVariable UUID quizId,
            @PathVariable UUID questionId,
            @RequestParam(value = "answer") String answer,
            RedirectAttributes redirectAttributes,
            Model model,
            Principal principal) {
        loadCommonData(model, principal);
        model.addAttribute("title", "Edit Question");
        model.addAttribute("quizzesActive", "active");
        Quiz quiz = quizService.getQuizById(quizId);
        try {
            if (answer.equals("A")) {
                answer = question.getOptionA();
            } else if (answer.equals("B")) {
                answer = question.getOptionB();
            } else if (answer.equals("C")) {
                answer = question.getOptionC();
            } else if (answer.equals("D")) {
                answer = question.getOptionD();
            }

            question.setAnswer(answer); // To pass the answer in view if there is validation error

            if (bindingResult.hasErrors()) {
                model.addAttribute("question", question);
                model.addAttribute("quiz", quiz);
                return "admin-template/admin/edit-question";
            }

            this.questionService.updateQuestion(question.getId(), question);
            redirectAttributes.addFlashAttribute(
                    "message", new Message("alert-primary", "Question is updated successfully."));
            return "redirect:/backend/quizzes/" + quiz.getId() + "/" + quiz.getTitle() + "/questions/page=0";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(
                    "message", new Message("alert-danger", "Something went wrong! " + e.getMessage()));
            return "redirect:/backend/quizzes/" + quiz.getId() + "/" + quiz.getTitle() + "/questions/page=0";
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/quizzes/{quizId}/{title}/questions/{questionId}/delete")
    public String deleteQuestion(
            @PathVariable UUID quizId, @PathVariable UUID questionId, RedirectAttributes redirectAttributes) {
        Quiz quiz = this.quizService.getQuizById(quizId);
        try {
            this.questionService.deleteQuestion(questionId);
            redirectAttributes.addFlashAttribute(
                    "message", new Message("alert-success", "Question is deleted successfully."));
            return "redirect:/backend/quizzes/" + quiz.getId() + "/" + quiz.getTitle() + "/questions/page=0";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(
                    "message", new Message("alert-danger", "Something went wrong! " + e.getMessage()));
            return "redirect:/backend/quizzes/" + quiz.getId() + "/" + quiz.getTitle() + "/questions/page=0";
        }
    }
}
