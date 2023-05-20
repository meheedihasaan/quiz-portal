package com.quiz.portal.model.audit;

import com.quiz.portal.constsant.AppConstant;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            return  Optional.of(SecurityContextHolder.getContext().getAuthentication().getName());
        }
        return Optional.of(AppConstant.DEFAULT_AUDITOR);
    }
}
