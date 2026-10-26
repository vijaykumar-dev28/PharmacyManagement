package com.pharmacy.entity;

import java.time.LocalDate;

import com.pharmacy.enums.Category;
import com.pharmacy.enums.MedicineStatus;

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
@Table(name="medicine_details")
public class Medicine {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="medicine_id")
	private Integer id;
	
	@Column(name="medicine_name",length = 50,nullable = false,unique = true)
	private String name;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Category category;
	
	@Column(nullable = false)
	private String manufacturer;
	
	@Column(nullable = false)
	private Double price;
	
	@Column(nullable = false)
	private Integer stockQuantity=0;
	
	@Column(nullable = false)
	private LocalDate expiryDate;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private MedicineStatus status=MedicineStatus.ACTIVE;
	
	
	@ManyToOne
	@JoinColumn(name = "supplier_id",nullable = false)
	private Supplier supplier;

}
