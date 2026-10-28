package com.pharmacy.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.pharmacy.entity.User;
import com.pharmacy.repository.UserRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService{

	private final UserRepository repo;

	public CustomUserDetailsService(UserRepository repo) {
		this.repo = repo;
	}



	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user=repo.findByUsername(username).orElseThrow(()->new com.pharmacy.exceptions.UserNotFoundException("User not found: " + username));
		return org.springframework.security.core.userdetails.User
				.withUsername(user.getUsername())
				.password(user.getPassword())
				.roles(user.getRole().name()).build();
	}
	
}
