package com.pharmacy.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pharmacy.dto.request.MedicineRequestDTO;
import com.pharmacy.dto.response.MedicineResponseDTO;
import com.pharmacy.entity.Medicine;
import com.pharmacy.enums.Category;
import com.pharmacy.enums.MedicineStatus;
import com.pharmacy.exceptions.DuplicateMedicineException;
import com.pharmacy.exceptions.MedicineNotFoundException;
import com.pharmacy.exceptions.SupplierNotFoundException;
import com.pharmacy.repository.MedicineRepository;
import com.pharmacy.repository.SupplierRepository;

@Service
public class MedicineService {
	@Autowired
	private MedicineRepository medicineRepo;
	
	@Autowired
	private SupplierRepository supplierRepo;
	
	// helper methods
	
	private Medicine toEntity(MedicineRequestDTO dto) {
		Medicine m=new Medicine();
		m.setName(dto.getName());
		m.setCategory(dto.getCategory());
		m.setManufacturer(dto.getManufacturer());
		m.setPrice(dto.getPrice());
		m.setExpiryDate(dto.getExpiryDate());
		m.setSupplier(supplierRepo.findById(dto.getSupplierId()).orElseThrow(()->new SupplierNotFoundException("Supplier not found!")));
		return m;
	}
	
	private MedicineResponseDTO toResponseDTO(Medicine m) {
		MedicineResponseDTO mr=new MedicineResponseDTO();
		mr.setId(m.getId());
		mr.setName(m.getName());
		mr.setCategory(m.getCategory());
		mr.setManufacturer(m.getManufacturer());
		mr.setPrice(m.getPrice());
		mr.setStockQuantity(m.getStockQuantity());
		mr.setExpiryDate(m.getExpiryDate());
		mr.setStatus(m.getStatus());
		mr.setSupplierName(m.getSupplier().getName());
		return mr;
	}
	
	private List<MedicineResponseDTO> toListResponseDTO(List<Medicine> medicines){
		List<MedicineResponseDTO> responseList=medicines.stream().map(this::toResponseDTO).collect(Collectors.toList());
		return responseList;
	}
	
	public MedicineStatus determineStatus(Medicine m) {
		if(m.getExpiryDate().isBefore(LocalDate.now())) {
			return MedicineStatus.EXPIRED;
		}else if(m.getExpiryDate().isBefore(LocalDate.now().plusDays(30))) {
			return MedicineStatus.EXPIRING_SOON;
		}else if(m.getStockQuantity()<10) {
			return MedicineStatus.LOW_STOCK;
		}else {
			return MedicineStatus.ACTIVE;
		}
	}
	
	// CRUD operations
	
	public void addMedicine(MedicineRequestDTO dto) {
		Medicine m=toEntity(dto);
		if(medicineRepo.existsByName(m.getName())) {
			throw new DuplicateMedicineException("Medicine already exists");
		}
		m.setStatus(determineStatus(m));
		medicineRepo.save(m);
	}
	
	public MedicineResponseDTO getMedicineById(Integer id) {
		Medicine m=medicineRepo.findById(id).orElseThrow(()->new MedicineNotFoundException("Medicine not found!"));
		return toResponseDTO(m);
	}
	
	public List<MedicineResponseDTO> getAllMedicines() {
		return toListResponseDTO(medicineRepo.findAll());
	}
	
	public void updateMedicineById(Integer id,MedicineRequestDTO updatedMedicine) {
		Medicine exists=medicineRepo.findById(id).orElseThrow(()->new MedicineNotFoundException("Medicine doesn't exist"));
		exists.setName(updatedMedicine.getName());
	    exists.setCategory(updatedMedicine.getCategory());
	    exists.setManufacturer(updatedMedicine.getManufacturer());
	    exists.setPrice(updatedMedicine.getPrice());
	    exists.setExpiryDate(updatedMedicine.getExpiryDate());
	    exists.setSupplier(supplierRepo.findById(updatedMedicine.getSupplierId()).orElseThrow(() -> new SupplierNotFoundException("Supplier not found!")));
	    exists.setStatus(determineStatus(exists));
	    medicineRepo.save(exists);
	}
	
	public void deleteMedicineById(Integer id) {
		Medicine m=medicineRepo.findById(id).orElseThrow(()->new MedicineNotFoundException("Medicine doesn't exist"));
		medicineRepo.delete(m);
	}
	
	// Filter/Search
	
	public List<MedicineResponseDTO> getExpiringSoon(){
		List<MedicineResponseDTO> list=toListResponseDTO(medicineRepo.findByStatus(MedicineStatus.EXPIRING_SOON));
		return list;
	}
	
	public List<MedicineResponseDTO> getExpired(){
		return toListResponseDTO(medicineRepo.findByStatus(MedicineStatus.EXPIRED));
	}
	
	public List<MedicineResponseDTO> getLowStock(){
		return toListResponseDTO(medicineRepo.findByStatus(MedicineStatus.LOW_STOCK));
	}
	
	public List<MedicineResponseDTO> getByCategory(Category category){
		 return toListResponseDTO(medicineRepo.findByCategory(category));
	}
	
	
	public List<MedicineResponseDTO> searchByName(String name) {
		return toListResponseDTO(medicineRepo.findByNameContainingIgnoreCase(name));
	}
}
