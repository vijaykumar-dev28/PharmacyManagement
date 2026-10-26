package com.pharmacy.dto.response;

import java.time.LocalDate;

import com.pharmacy.enums.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockTransactionResponseDTO {
	private Integer id;
	private String medicineName;
	private TransactionType transactionType;
	private Integer quantity;
	private LocalDate transactionDate;
	private String reason;

}
