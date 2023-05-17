package com.quiz.portal.controller.backend;

import com.quiz.portal.entity.Quiz;
import com.quiz.portal.entity.User;
import com.quiz.portal.service.CategoryService;
import com.quiz.portal.service.QuizResultService;
import com.quiz.portal.service.QuizService;
import com.quiz.portal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/backend")
public class AdminController {

    private final UserService userService;

    private final CategoryService categoryService;

    private final QuizService quizService;

    private final QuizResultService quizResultService;

    public void loadCommonData(Model model, Principal principal) {
        String email = principal.getName();
        User user = this.userService.getUserByEmailWithException(email);
        model.addAttribute("user", user);
    }

    @GetMapping
    public String viewDashboard(Model model, Principal principal) {
        loadCommonData(model, principal);
        model.addAttribute("title", "Dashboard");
        model.addAttribute("dashboardActive", "active");
        model.addAttribute("totalCategory", this.categoryService.countCategories());
        model.addAttribute("totalQuiz", this.quizService.countQuizzes());
        model.addAttribute("totalPublishedQuiz", this.quizService.countPublishedQuizzes());
        User user = this.userService.getUserByEmail(principal.getName());
        model.addAttribute("totalAttempt", this.quizResultService.countAttemptsByUser(user.getId()));
        model.addAttribute("totalParticipant", this.quizResultService.countParticipants());
        double avgAccuracy = quizResultService.getAvgAccuracyByUser(user.getId());
        if (Math.floor(avgAccuracy) == Math.ceil(avgAccuracy)) {
            model.addAttribute("avgAccuracy", (int) avgAccuracy);
        } else {
            model.addAttribute("avgAccuracy", avgAccuracy);
        }

        List<Quiz> quizzes = this.quizService.getPublishedQuizzes();
        model.addAttribute("quizzes", quizzes);
        return "admin-template/index";
    }

    @GetMapping("/my-profile/{name}")
    public String viewMyProfile(Model model, Principal principal) {
        loadCommonData(model, principal);
        model.addAttribute("title", "My Profile");
        model.addAttribute("myProfileActive", "active");
        return "admin-template/my-profile";
    }

}
