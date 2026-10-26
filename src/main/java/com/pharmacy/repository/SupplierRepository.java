package com.pharmacy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pharmacy.entity.Supplier;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer>{
	boolean existsByContactNumber(String contactNumber);
	boolean existsByEmail(String email);
}
