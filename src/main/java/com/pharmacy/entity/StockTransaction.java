package com.pharmacy.entity;

import java.time.LocalDate;

import com.pharmacy.enums.TransactionType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="stock_transaction")
public class StockTransaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="transaction_id")
	private Integer id;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private TransactionType transactionType;
	
	@Column(nullable = false)
	private Integer quantity;
	
	private LocalDate transactionDate=LocalDate.now();
	
	private String reason;
	
	@ManyToOne
	@JoinColumn(name="medicine_id",nullable = false)
	private Medicine medicine;
}
