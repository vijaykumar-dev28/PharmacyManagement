package com.pharmacy.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pharmacy.dto.request.StockTransactionRequestDTO;
import com.pharmacy.dto.response.StockTransactionResponseDTO;
import com.pharmacy.entity.Medicine;
import com.pharmacy.entity.StockTransaction;
import com.pharmacy.enums.TransactionType;
import com.pharmacy.exceptions.InsufficientStockException;
import com.pharmacy.exceptions.MedicineNotFoundException;
import com.pharmacy.repository.MedicineRepository;
import com.pharmacy.repository.StockTransactionRepository;

@Service
public class StockTransactionService {
	@Autowired
	private StockTransactionRepository stockRepo;
	
	@Autowired
	private MedicineService medicineService;
	
	@Autowired
	private MedicineRepository medicineRepo;
	
	// helper method
	private StockTransaction toEntity(StockTransactionRequestDTO d){
		StockTransaction s=new StockTransaction();
		s.setQuantity(d.getQuantity());
		s.setReason(d.getReason());	
		return s;
	}
	
	private StockTransactionResponseDTO toResponseDTO(StockTransaction s) {
		StockTransactionResponseDTO d=new StockTransactionResponseDTO();
		d.setId(s.getId());
		d.setMedicineName(s.getMedicine().getName());
		d.setTransactionType(s.getTransactionType());
		d.setQuantity(s.getQuantity());
		d.setTransactionDate(s.getTransactionDate());
		d.setReason(s.getReason());
		return d;
	}
	
	private List<StockTransactionResponseDTO> toListResponseSTO(List<StockTransaction> sList){
		List<StockTransactionResponseDTO> srList=sList.stream().map(this::toResponseDTO).collect(Collectors.toList());
		return srList;
	}
	
	public void addStock(StockTransactionRequestDTO dto) {
		Medicine m=medicineRepo.findById(dto.getMedicineId()).orElseThrow(()->new MedicineNotFoundException("Medicine not found!"));
		StockTransaction s=toEntity(dto);
		s.setMedicine(m);
		s.setTransactionType(TransactionType.IN);
		
		m.setStockQuantity(m.getStockQuantity()+s.getQuantity());
		m.setStatus(medicineService.determineStatus(m));
		
		stockRepo.save(s);
		medicineRepo.save(m);
	}
	
	
	public void sellStock(StockTransactionRequestDTO dto) {
		Medicine m=medicineRepo.findById(dto.getMedicineId()).orElseThrow(()->new MedicineNotFoundException("Medicine not found!"));
		
		if(m.getStockQuantity()<dto.getQuantity()) {
			throw new InsufficientStockException("Not enough stock available");
		}
		
		StockTransaction s=toEntity(dto);
		s.setMedicine(m);
		s.setTransactionType(TransactionType.OUT);
		
		m.setStockQuantity(m.getStockQuantity()-dto.getQuantity());
		m.setStatus(medicineService.determineStatus(m));
		
		stockRepo.save(s);
		medicineRepo.save(m);
	}
	
	public List<StockTransactionResponseDTO> getAllTransaction() {
		return toListResponseSTO(stockRepo.findAll());
	}
	
	public List<StockTransactionResponseDTO> getTransactionByMadicine(Integer id) {
		return toListResponseSTO(stockRepo.findByMedicineId(id));
	}
}
