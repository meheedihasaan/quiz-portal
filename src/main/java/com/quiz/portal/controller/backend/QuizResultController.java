package com.quiz.portal.controller.backend;

import com.quiz.portal.constsant.AppConstant;
import com.quiz.portal.entity.QuizResult;
import com.quiz.portal.entity.User;
import com.quiz.portal.service.QuizResultService;
import com.quiz.portal.service.UserService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/backend")
public class QuizResultController {

    private final UserService userService;

    private final QuizResultService quizResultService;

    public void loadCommonData(Model model, Principal principal) {
        String username = principal.getName();
        User user = userService.getUserByEmailWithException(username);
        model.addAttribute("user", user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/quiz-history/page={pageNumber}")
    public String viewQuizHistoryPage(@PathVariable int pageNumber, Model model, Principal principal) {
        loadCommonData(model, principal);
        model.addAttribute("title", "Quiz History");
        model.addAttribute("quizHistoryActive", "active");

        String SORT_BY = "date";
        String SORT_DIRECTION = "desc";
        Sort sort = null;
        if (SORT_DIRECTION.equalsIgnoreCase("asc")) {
            sort = Sort.by(SORT_BY).ascending();
        } else if (SORT_DIRECTION.equalsIgnoreCase("desc")) {
            sort = Sort.by(SORT_BY).descending();
        }

        Pageable pageable = PageRequest.of(pageNumber, AppConstant.QUIZ_HISTORY_PAGE_SIZE, sort);
        Page<QuizResult> quizResultPage = this.quizResultService.getQuizResults(pageable);
        model.addAttribute("quizResultPage", quizResultPage);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("pageSize", AppConstant.QUIZ_HISTORY_PAGE_SIZE);
        model.addAttribute("totalPages", quizResultPage.getTotalPages());
        return "admin-template/admin/quiz-history";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/quiz-history/{resultId}/{quizTitle}")
    public String viewSingleQuizResultHistory(@PathVariable UUID resultId, Model model, Principal principal) {
        loadCommonData(model, principal);
        model.addAttribute("quizHistoryActive", "active");
        QuizResult quizResult = this.quizResultService.getQuizResultById(resultId);
        model.addAttribute("title", "Result" + quizResult.getQuiz().getTitle());
        model.addAttribute("quizResult", quizResult);
        return "admin-template/admin/single-quiz-result";
    }

    @PreAuthorize("hasRole('NORMAL')")
    @GetMapping("/attempted-quizzes/page={pageNumber}")
    public String viewAttemptedQuizzesPage(@PathVariable int pageNumber, Model model, Principal principal) {
        loadCommonData(model, principal);
        model.addAttribute("title", "Attempted Quizzes");
        model.addAttribute("attemptedQuizzesActive", "active");
        User user = this.userService.getUserByEmailWithException(principal.getName());

        String SORT_BY = "date";
        String SORT_DIRECTION = "desc";
        Sort sort = null;
        if (SORT_DIRECTION.equalsIgnoreCase("asc")) {
            sort = Sort.by(SORT_BY).ascending();
        } else if (SORT_DIRECTION.equalsIgnoreCase("desc")) {
            sort = Sort.by(SORT_BY).descending();
        }

        Pageable pageable = PageRequest.of(pageNumber, AppConstant.QUIZ_RESULT_PAGE_SIZE, sort);
        Page<QuizResult> quizResultPage = this.quizResultService.getQuizResultsByUser(user.getId(), pageable);
        model.addAttribute("quizResultPage", quizResultPage);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("pageSize", AppConstant.QUIZ_RESULT_PAGE_SIZE);
        model.addAttribute("totalPages", quizResultPage.getTotalPages());
        return "admin-template/normal/attempted-quizzes";
    }

    @PreAuthorize("hasRole('NORMAL')")
    @GetMapping("/attempted-quizzes/{resultId}/{quizTitle}")
    public String viewSingleQuizResultPage(@PathVariable UUID resultId, Model model, Principal principal) {
        loadCommonData(model, principal);
        model.addAttribute("attemptedQuizzesActive", "active");
        QuizResult quizResult = this.quizResultService.getQuizResultById(resultId);
        model.addAttribute("title", "Result" + quizResult.getQuiz().getTitle());
        model.addAttribute("quizResult", quizResult);
        return "admin-template/normal/single-quiz-result";
    }
}
