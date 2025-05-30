package com.quiz.portal.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    public static final String[] STATIC_RESOURCES = {
        "/admin-resources/**",
        "/site-resources/**",
        "/css/**",
        "/js/**",
        "/images/**",
        "/images-old/**",
        "/scss/**",
        "/icon/**",
        "/pages/**"
    };
    private static final String[] PUBLIC_URLS = {"/", "/sign-up", "/sign-in", "/sign-in/process", "/actuator/**"};
    private final UserDetailsServiceImpl userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @SuppressWarnings("removal")
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(STATIC_RESOURCES).permitAll()
                        .requestMatchers(PUBLIC_URLS).permitAll()
                        .requestMatchers("/backend/**").hasAnyRole("ADMIN", "NORMAL").anyRequest().authenticated())
                .formLogin()
                .loginPage("/sign-in")
                .loginProcessingUrl("/sign-in/process")
                .defaultSuccessUrl("/backend")
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .and()
                .csrf()
                .disable();

        http.authenticationProvider(authenticationProvider());
        http.addFilterAfter(new LoggedInUserFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
