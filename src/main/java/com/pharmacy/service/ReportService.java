package com.pharmacy.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pharmacy.dto.response.MedicineResponseDTO;
import com.pharmacy.entity.Medicine;
import com.pharmacy.enums.Category;
import com.pharmacy.enums.MedicineStatus;
import com.pharmacy.repository.MedicineRepository;

@Service
public class ReportService {
	@Autowired
	private MedicineService medicineService;
	
	@Autowired
	private MedicineRepository medicineRepo;
	
	public double getExpiredValue(){
		List<Medicine> expiredMedicines=medicineRepo.findByStatus(MedicineStatus.EXPIRED);
		double total=expiredMedicines.stream().mapToDouble(m->m.getPrice()*m.getStockQuantity()).sum();
		return total;
	}
	
	public Map<Category, Integer> getCategoryWiseStock() {
		List<Medicine> medicines=medicineRepo.findAll();
		Map<Category, Integer> categorizedMadicine=medicines.stream().collect(Collectors.groupingBy((m)->m.getCategory(),Collectors.summingInt(m->m.getStockQuantity())));
		return categorizedMadicine;
	}
	
	public List<MedicineResponseDTO> getLowStockList() {
		return medicineService.getLowStock();
	}
}
