package com.pharmacy.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SupplierResponseDTO {
	private Integer id;
	private String name;
	private String contactNumber;
	private String email;
}
