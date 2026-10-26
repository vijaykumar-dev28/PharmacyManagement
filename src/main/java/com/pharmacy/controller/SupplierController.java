package com.pharmacy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pharmacy.dto.request.SupplierRequestDTO;
import com.pharmacy.dto.response.SupplierResponseDTO;
import com.pharmacy.service.SupplierService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/suppliers")
public class SupplierController {
	@Autowired
	private SupplierService supplierService;
	
	@PostMapping
	public ResponseEntity<String> addSupplier(@Valid @RequestBody SupplierRequestDTO s){
		supplierService.addSupplier(s);
		return ResponseEntity.status(201).body("Supplier added successfully!");
	}
	
	@GetMapping
	public ResponseEntity<List<SupplierResponseDTO>> getAllSuppliers(){
		return ResponseEntity.ok(supplierService.getAllSuppliers());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<SupplierResponseDTO> getSupplierById(@PathVariable Integer id){
		return ResponseEntity.ok(supplierService.getSupplierById(id));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<String> updateSupplier(@PathVariable Integer id,@Valid @RequestBody SupplierRequestDTO s){
		supplierService.updateSupplierById(id, s);
		return ResponseEntity.ok("Supplier detail updated!");
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteSupplierById(@PathVariable Integer id){
		supplierService.deleteSupplierById(id);
		return ResponseEntity.noContent().build();
	}
}
