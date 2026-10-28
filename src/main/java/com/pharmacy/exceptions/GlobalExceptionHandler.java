package com.pharmacy.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(MedicineNotFoundException.class)
	public ResponseEntity<String> handleMedicineNotFound(MedicineNotFoundException e){
		return ResponseEntity.status(404).body(e.getMessage());
	}
	
	@ExceptionHandler(SupplierNotFoundException.class)
	public ResponseEntity<String> SupplierNotFoundException(SupplierNotFoundException e){
		return ResponseEntity.status(404).body(e.getMessage());
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<String> handleUserNotFound(UserNotFoundException e){
		return ResponseEntity.status(404).body(e.getMessage());
	}
	
	@ExceptionHandler(InsufficientStockException.class)
	public ResponseEntity<String> handleInsufficientStock(InsufficientStockException e){
		return ResponseEntity.badRequest().body(e.getMessage());
	}
	
	@ExceptionHandler(DuplicateMedicineException.class)
	public ResponseEntity<String> handleDuplicateMedicine(DuplicateMedicineException e){
		return ResponseEntity.status(409).body(e.getMessage());
	}
	
	@ExceptionHandler(DuplicateSupplierException.class)
	public ResponseEntity<String> handleDuplicateSupplier(DuplicateSupplierException e){
		return ResponseEntity.status(409).body(e.getMessage());
	}
	
	@ExceptionHandler(DuplicateUserException.class)
	public ResponseEntity<String> handleDuplicateUser(DuplicateUserException e){
		return ResponseEntity.status(409).body(e.getMessage());
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> globalException(MethodArgumentNotValidException e){
		String error=e.getBindingResult().getFieldErrors().stream().map(err->err.getField()+": "+err.getDefaultMessage()).findFirst().orElse("Invalid value");
		return ResponseEntity.badRequest().body(error);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> globalException(Exception e){
		return ResponseEntity.status(500).body("Something went wrong!");
	}
}
