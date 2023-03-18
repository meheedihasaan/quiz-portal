package com.exam.portal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private static final String[] PUBLIC_URLS = {
			"/",
			"/sign-up",
			"/sign-in"
	};
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(
				authorize->
					authorize
						.requestMatchers("/admin-resources/**", "/site-resources/**").permitAll()
						.requestMatchers(PUBLIC_URLS).permitAll()
						.requestMatchers("/backend/**").hasAnyRole("ADMIN", "NORMAL")
					)
					.formLogin()
					.defaultSuccessUrl("/backend")
					.and()
					.csrf()
					.disable();
		
		return http.build();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
