package com.pharmacy.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockTransactionRequestDTO {
	
	@NotNull(message = "Medicine ID can't be empty")
	private Integer medicineId;
	
	@NotNull(message = "Quantity can't be empty")
	@Min(value = 0,message = "Quantity can't be negative")
	private Integer quantity;
	
	private String reason;
}
