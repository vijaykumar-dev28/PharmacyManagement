package com.pharmacy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pharmacy.entity.Medicine;
import com.pharmacy.enums.Category;
import com.pharmacy.enums.MedicineStatus;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Integer>{
	boolean existsByName(String name);
	List<Medicine> findByNameContainingIgnoreCase(String name);
	List<Medicine> findByCategory(Category category);
	List<Medicine> findByStatus(MedicineStatus status);
}
