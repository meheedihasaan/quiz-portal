package com.exam.portal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.exam.portal.security.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	public UserDetailsService usreDetailsService() {
		return new UserDetailsServiceImpl();
	};

	private static final String[] PUBLIC_URLS = {
			"/",
			"/sign-up",
			"/sign-in",
			"/sign-in/process"
	};
	
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
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(
				authorize->
					authorize
						.requestMatchers(STATIC_RESOURCES).permitAll()
						.requestMatchers(PUBLIC_URLS).permitAll()
						.requestMatchers("/backend/**").hasAnyRole("ADMIN", "NORMAL")
					)
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
		
		return http.build();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(usreDetailsService());
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}
	
}
