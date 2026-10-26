package com.pharmacy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pharmacy.dto.request.MedicineRequestDTO;
import com.pharmacy.dto.response.MedicineResponseDTO;
import com.pharmacy.enums.Category;
import com.pharmacy.service.MedicineService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/medicines")
public class MedicineController {

    @Autowired
    private MedicineService medicineService;

    @PostMapping
    public ResponseEntity<String> addMedicine(@Valid @RequestBody MedicineRequestDTO dto) {
        medicineService.addMedicine(dto);
        return ResponseEntity.status(201).body("Medicine added successfully!");
    }

    @GetMapping
    public ResponseEntity<List<MedicineResponseDTO>> getAllMedicines() {
        return ResponseEntity.ok(medicineService.getAllMedicines());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicineResponseDTO> getMedicineById(@PathVariable Integer id) {
        return ResponseEntity.ok(medicineService.getMedicineById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateMedicine(@PathVariable Integer id,@Valid @RequestBody MedicineRequestDTO dto) {
        medicineService.updateMedicineById(id, dto);
        return ResponseEntity.ok("Medicine updated successfully!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMedicine(@PathVariable Integer id) {
        medicineService.deleteMedicineById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/expiring-soon")
    public ResponseEntity<List<MedicineResponseDTO>> getExpiringSoon() {
        return ResponseEntity.ok(medicineService.getExpiringSoon());
    }

    @GetMapping("/expired")
    public ResponseEntity<List<MedicineResponseDTO>> getExpired() {
        return ResponseEntity.ok(medicineService.getExpired());
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<MedicineResponseDTO>> getLowStock() {
        return ResponseEntity.ok(medicineService.getLowStock());
    }

    @GetMapping("/search")
    public ResponseEntity<List<MedicineResponseDTO>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(medicineService.searchByName(name));
    }

    @GetMapping("/category")
    public ResponseEntity<List<MedicineResponseDTO>> getByCategory(@RequestParam Category category) {
        return ResponseEntity.ok(medicineService.getByCategory(category));
    }
}