package com.pharmacy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pharmacy.entity.StockTransaction;

@Repository
public interface StockTransactionRepository extends JpaRepository<StockTransaction, Integer>{
	List<StockTransaction> findByMedicineId(Integer medicineId);
}
