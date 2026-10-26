package com.pharmacy.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SupplierRequestDTO {
	@NotBlank(message = "Name can't be empty")
	private String name;
	@NotBlank(message = "Contact can't be empty")
	private String contactNumber;
	@NotBlank(message = "Email can't be empty")
	@Email(message = "Invalid email format")
	private String email;             
}
