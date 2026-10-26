package com.pharmacy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pharmacy.dto.request.StockTransactionRequestDTO;
import com.pharmacy.dto.response.StockTransactionResponseDTO;
import com.pharmacy.service.StockTransactionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/stock")
public class StockTransactionController {
	@Autowired
	private StockTransactionService service;
	
	@PostMapping("/add")
	public ResponseEntity<String> addStock(@Valid @RequestBody StockTransactionRequestDTO d){
		service.addStock(d);
		return ResponseEntity.status(201).body("Stock added");
	}
	
	@PostMapping("/sell")
	public ResponseEntity<String> sellStock(@Valid @RequestBody StockTransactionRequestDTO d){
		service.sellStock(d);
		return ResponseEntity.ok("Stock sold successfully!");
	}
	
	@GetMapping("/transactions")
	public ResponseEntity<List<StockTransactionResponseDTO>> getAllTransaction(){
		return ResponseEntity.ok(service.getAllTransaction());
	}
	
	@GetMapping("/transactions/{id}")
	public ResponseEntity<List<StockTransactionResponseDTO>> getTransactionByMadicine(@PathVariable Integer id){
		return ResponseEntity.ok(service.getTransactionByMadicine(id));
	}
}
