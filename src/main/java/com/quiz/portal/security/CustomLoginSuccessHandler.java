package com.quiz.portal.security;

import com.quiz.portal.constsant.AppConstant;
import com.quiz.portal.entity.Role;
import com.quiz.portal.entity.User;
import com.quiz.portal.repository.UserRepository;
import com.quiz.portal.service.RoleService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;

    private final RoleService roleService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (authentication.getPrincipal() instanceof OAuth2User oAuth2User) {
            String name = oAuth2User.getAttribute("name");
            String email = oAuth2User.getAttribute("email");

            User user = userRepository.findByEmail(email).orElse(null);
            if (user == null) {
                User newUser = new User();
                newUser.setName(name);
                newUser.setEmail(email);
                newUser.setPhone("017********");
                newUser.setPassword("1234");

                Set<Role> roles = new HashSet<>();
                roles.add(roleService.getRoleByRoleName(AppConstant.NORMAL));
                newUser.setRoles(roles);
                user = userRepository.save(newUser);
            }

            UserDetails userDetails = new UserDetailsImpl(user);
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
        }

        new DefaultRedirectStrategy().sendRedirect(request, response, "/backend");
    }
}
