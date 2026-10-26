package com.pharmacy.dto.request;

import java.time.LocalDate;

import com.pharmacy.enums.Category;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicineRequestDTO {
	@NotBlank(message = "Name can't be empty")
	private String name;
	@NotNull(message = "Category can't be empty")
	private Category category;
	@NotBlank(message = "Manufacturer can't be empty")
	private String manufacturer;
	@NotNull(message = "Price can't be null")
	@Min(value=0,message = "Price can't be negative")
	private Double price;
	@NotNull(message = "Expiry date can't be null")
	private LocalDate expiryDate;
	@NotNull(message = "Supplier ID can't be null")
	private Integer supplierId;
}
