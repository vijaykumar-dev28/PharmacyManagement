package com.pharmacy.dto.response;

import java.time.LocalDate;

import com.pharmacy.enums.Category;
import com.pharmacy.enums.MedicineStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MedicineResponseDTO {
	private Integer id;
	private String name;
	private Category category;
	private String manufacturer;
	private Double price;
	private Integer stockQuantity;
	private LocalDate expiryDate;
	private MedicineStatus status;
	private String supplierName;
}
