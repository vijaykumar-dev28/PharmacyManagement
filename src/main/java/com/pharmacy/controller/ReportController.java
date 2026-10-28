package com.pharmacy.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pharmacy.dto.response.MedicineResponseDTO;
import com.pharmacy.enums.Category;
import com.pharmacy.service.ReportService;

@RestController
@RequestMapping("/reports")
public class ReportController {
	private final ReportService service;	
	
	public ReportController(ReportService service) {
		
		this.service = service;
	}

	@GetMapping("/expired-value")
	public ResponseEntity<Double> getExpiredValue(){
		return ResponseEntity.ok(service.getExpiredValue());
	}
	
	@GetMapping("/category-wise")
	public ResponseEntity<Map<Category, Integer>> getCategoryWiseStock(){
		return ResponseEntity.ok(service.getCategoryWiseStock());
	}
	
	@GetMapping("/low-stock-list")
	public ResponseEntity<List<MedicineResponseDTO>> getLowStockList(){
		return ResponseEntity.ok(service.getLowStockList());
	}
}
