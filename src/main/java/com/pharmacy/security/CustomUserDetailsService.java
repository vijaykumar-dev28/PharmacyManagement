package com.pharmacy.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.pharmacy.entity.User;
import com.pharmacy.repository.UserRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user=repo.findByUsername(username).get();
		return org.springframework.security.core.userdetails.User
				.withUsername(user.getUsername())
				.password(user.getPassword())
				.roles(user.getRole().name()).build();
	}
	
}
