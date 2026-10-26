package com.pharmacy.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="supplier_details")
public class Supplier {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="supplier_id") 
	private Integer id;
	@Column(nullable = false)
	private String name;
	@Column(unique = true,nullable = false)
	private String contactNumber;
	@Column(unique = true,nullable = false)
	private String email;             
	
	@OneToMany(mappedBy = "supplier")
	private List<Medicine> medicines;

}
