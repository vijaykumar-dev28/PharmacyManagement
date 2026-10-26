package com.pharmacy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pharmacy.dto.request.AuthRequestDTO;
import com.pharmacy.dto.response.AuthResponseDTO;
import com.pharmacy.entity.User;
import com.pharmacy.enums.Role;
import com.pharmacy.repository.UserRepository;
import com.pharmacy.security.JwtUtil;

@Service
public class AuthService {
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	public void register(AuthRequestDTO dto) {
		if(repo.existsByUsername(dto.getUsername())){
			throw new RuntimeException("Username already exists!");
		}
		
		User user=new User();
		user.setUsername(dto.getUsername());
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		user.setRole(Role.USER);
		repo.save(user);
	}
	
	public void registerAdmin(AuthRequestDTO dto) {
		if(repo.existsByUsername(dto.getUsername())){
			throw new RuntimeException("Username already exists!");
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
		User user=repo.findByUsername(dto.getUsername()).get();
		return new AuthResponseDTO(dto.getUsername(),token,user.getRole());
	}
}
