package com.pharmacy.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pharmacy.dto.request.AuthRequestDTO;
import com.pharmacy.dto.response.AuthResponseDTO;
import com.pharmacy.entity.User;
import com.pharmacy.enums.Role;
import com.pharmacy.exceptions.DuplicateUserException;
import com.pharmacy.exceptions.UserNotFoundException;
import com.pharmacy.repository.UserRepository;
import com.pharmacy.security.JwtUtil;

@Service
public class AuthService {
	private final UserRepository repo;
	
	private final PasswordEncoder passwordEncoder;
	
	private final AuthenticationManager authenticationManager;
	
	private final JwtUtil jwtUtil;
	
	public AuthService(UserRepository repo, PasswordEncoder passwordEncoder,
			AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
		this.repo = repo;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}

	public void register(AuthRequestDTO dto) {
		if(repo.existsByUsername(dto.getUsername())){
			throw new DuplicateUserException("Username already exists");
		}
		
		User user=new User();
		user.setUsername(dto.getUsername());
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		user.setRole(Role.USER);
		repo.save(user);
	}
	
	public void registerAdmin(AuthRequestDTO dto) {
		if(repo.existsByUsername(dto.getUsername())){
			throw new DuplicateUserException("Username already exists");
		}
		
		User user=new User();
		user.setUsername(dto.getUsername());
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		user.setRole(Role.ADMIN);
		repo.save(user);
	}
	
	public AuthResponseDTO login(AuthRequestDTO dto) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
		String token = jwtUtil.generateToken(dto.getUsername());
		User user=repo.findByUsername(dto.getUsername()).orElseThrow(()->new UserNotFoundException("User not exist"));
		return new AuthResponseDTO(dto.getUsername(),token,user.getRole());
	}
}
