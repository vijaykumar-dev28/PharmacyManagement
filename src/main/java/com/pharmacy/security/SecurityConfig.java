package com.pharmacy.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
	
	@Autowired
	private JwtFilter jwtFilter;
	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http.csrf(csrf->csrf.disable())
		.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.authorizeHttpRequests(auth->auth
				.requestMatchers("/auth/**").permitAll()
				.requestMatchers(HttpMethod.POST,"/medicines/**").hasRole("ADMIN")
				.requestMatchers(HttpMethod.PUT,"/medicines/**").hasRole("ADMIN")
				.requestMatchers(HttpMethod.DELETE,"/medicines/**").hasRole("ADMIN")
				.requestMatchers("/reports/**").hasRole("ADMIN")
				.requestMatchers("/suppliers/**").hasRole("ADMIN")
				.requestMatchers(HttpMethod.GET,"/medicines/**").hasAnyRole("ADMIN","USER")
				.requestMatchers("/stock/**").hasAnyRole("ADMIN","USER")
				.anyRequest().authenticated())
		.authenticationProvider(authenticationProvider())
		.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider=new DaoAuthenticationProvider(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) {
		return config.getAuthenticationManager();
	}
}
