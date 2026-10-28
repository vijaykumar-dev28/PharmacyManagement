package com.pharmacy.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.pharmacy.dto.request.SupplierRequestDTO;
import com.pharmacy.dto.response.SupplierResponseDTO;
import com.pharmacy.entity.Supplier;
import com.pharmacy.exceptions.DuplicateSupplierException;
import com.pharmacy.exceptions.SupplierNotFoundException;
import com.pharmacy.repository.SupplierRepository;

@Service
public class SupplierService {
	private final SupplierRepository supplierRepo;
	
	public SupplierService(SupplierRepository supplierRepo) {
		this.supplierRepo = supplierRepo;
	}

	// helper methods
	private Supplier toEntity(SupplierRequestDTO d){
		Supplier s=new Supplier();
		s.setName(d.getName());
		s.setContactNumber(d.getContactNumber());
		s.setEmail(d.getEmail());
		return s;
	}
	
	private SupplierResponseDTO toResponseDTO(Supplier s) {
		SupplierResponseDTO sr=new SupplierResponseDTO();
		sr.setId(s.getId());
		sr.setName(s.getName());
		sr.setContactNumber(s.getContactNumber());
		sr.setEmail(s.getEmail());
		return sr;
	}
	
	private List<SupplierResponseDTO> toListResponseDTO(List<Supplier> sList){
		List<SupplierResponseDTO> srList=sList.stream().map(this::toResponseDTO).collect(Collectors.toList());
		return srList;
	}
	
	public void addSupplier(SupplierRequestDTO d){
		if(supplierRepo.existsByContactNumber(d.getContactNumber())) {
			throw new DuplicateSupplierException("Contact number already exists");
		}
		if(supplierRepo.existsByEmail(d.getEmail())) {
			throw new DuplicateSupplierException("Email already exists");
		}
		supplierRepo.save(toEntity(d));
	}
	
	public SupplierResponseDTO getSupplierById(Integer id){
		Supplier s=supplierRepo.findById(id).orElseThrow(()->new SupplierNotFoundException("Supplier not exists"));
		return toResponseDTO(s);
	}
	
	public List<SupplierResponseDTO> getAllSuppliers(){
		return toListResponseDTO(supplierRepo.findAll());
	}
	
	public void updateSupplierById(Integer id,SupplierRequestDTO updated){
		Supplier exists=supplierRepo.findById(id).orElseThrow(()->new SupplierNotFoundException("Supplier not exists"));
		exists.setName(updated.getName());
		exists.setContactNumber(updated.getContactNumber());
		exists.setEmail(updated.getEmail());
		supplierRepo.save(exists);
	}
	
	public void deleteSupplierById(Integer id) {
		Supplier exists=supplierRepo.findById(id).orElseThrow(()->new SupplierNotFoundException("Supplier not exists"));
		supplierRepo.delete(exists);
	}
}
