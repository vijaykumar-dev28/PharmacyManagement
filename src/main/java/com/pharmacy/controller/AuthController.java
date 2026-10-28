package com.pharmacy.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pharmacy.dto.request.AuthRequestDTO;
import com.pharmacy.dto.response.AuthResponseDTO;
import com.pharmacy.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private final AuthService service;

	public AuthController(AuthService service) {
		this.service = service;
	}

	@PostMapping("/register")
	public ResponseEntity<String> register(@Valid @RequestBody AuthRequestDTO dto) {
		service.register(dto);
		return ResponseEntity.status(201).body("Registered successfully");
	}
	
	@PostMapping("/register-admin")
	public ResponseEntity<String> registerAdmin(@Valid @RequestBody AuthRequestDTO dto) {
		service.registerAdmin(dto);
		return ResponseEntity.status(201).body("Admin registered successfully");
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody AuthRequestDTO dto){
		return ResponseEntity.ok(service.login(dto));
	}
	
	
}
