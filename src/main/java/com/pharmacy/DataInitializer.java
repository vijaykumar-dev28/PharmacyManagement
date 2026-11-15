package com.pharmacy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.pharmacy.entity.User;
import com.pharmacy.enums.Role;
import com.pharmacy.repository.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner{
	
	private final UserRepository userRepo;
	private final PasswordEncoder passwordEncoder;
	
	public DataInitializer(UserRepository userRepo, PasswordEncoder passwordEncoder) {
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Value("${admin.default.password}")
	private String defaultAdminPassword;

	@Override
	public void run(String... args) throws Exception {
		if(!userRepo.existsByUsername("admin")) {
			User admin=new User();
			admin.setUsername("admin");
			admin.setPassword(passwordEncoder.encode(defaultAdminPassword));
			admin.setRole(Role.ADMIN);
			userRepo.save(admin);
			System.out.println("First admin saved automatically!");
		}
	}
	
}
