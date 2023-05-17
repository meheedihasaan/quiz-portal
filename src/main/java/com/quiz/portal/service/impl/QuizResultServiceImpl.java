package com.quiz.portal.service.impl;

import com.quiz.portal.entity.QuizResult;
import com.quiz.portal.entity.User;
import com.quiz.portal.exception.custom.NotFoundException;
import com.quiz.portal.repository.QuizResultRepository;
import com.quiz.portal.repository.UserRepository;
import com.quiz.portal.service.QuizResultService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class QuizResultServiceImpl implements QuizResultService {

    private final QuizResultRepository quizResultRepository;

    private final UserRepository userRepository;

    @Override
    public QuizResult createQuizResult(QuizResult quizResult) {
        return this.quizResultRepository.save(quizResult);
    }

    @Override
    public Page<QuizResult> getQuizResults(Pageable pageable) {
        return this.quizResultRepository.findAll(pageable);
    }

    @Override
    public QuizResult getQuizResultById(UUID id) {
        return this.quizResultRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Quiz result not found!"));
    }

    @Override
    public Page<QuizResult> getQuizResultsByUser(UUID userId, Pageable pageable) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found!"));
        return this.quizResultRepository.findByUser(user, pageable);
    }

    @Override
    public long countAttemptsByUser(UUID userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found!"));
        return this.quizResultRepository.countByUser(user);
    }

    @Override
    public long countParticipants() {
        List<QuizResult> quizResults = this.quizResultRepository.findAll();
        Set<UUID> set = new HashSet<>();
        quizResults.forEach((quizResult) -> set.add(quizResult.getUser().getId()));
        return set.size();
    }

    @Override
    public double getAvgAccuracyByUser(UUID userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found!"));
        double avgAccuracy;
        try {
            avgAccuracy = this.quizResultRepository.getAvgAccuracyByUser(user);
        } catch (Exception e) {
            avgAccuracy = 0;
        }

        String formattedValue = String.format("%.1f", avgAccuracy);
        avgAccuracy = Double.parseDouble(formattedValue);
        return avgAccuracy;
    }
}
