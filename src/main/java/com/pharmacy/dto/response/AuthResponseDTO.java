package com.pharmacy.dto.response;

import com.pharmacy.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDTO {
	private String username;
	private String token;
	private Role role;
}
