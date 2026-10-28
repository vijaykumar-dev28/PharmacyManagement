package com.pharmacy.scheduler;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.pharmacy.entity.Medicine;
import com.pharmacy.enums.MedicineStatus;
import com.pharmacy.repository.MedicineRepository;
import com.pharmacy.service.MedicineService;

@Component
public class ExpiryScheduler {
	private final MedicineService service;
	
	private final MedicineRepository repo;
	
	public ExpiryScheduler(MedicineService service, MedicineRepository repo) {
		this.service = service;
		this.repo = repo;
	}



	@Scheduled(cron = "0 0 8 * * *")
	public void checkExpiryAndUpdateStatus(){
		List<Medicine> medicine=repo.findAll();
		for (Medicine m : medicine) {
			MedicineStatus newStatus=service.determineStatus(m);
			if(!newStatus.equals(m.getStatus())) {
				m.setStatus(newStatus);
				repo.save(m);
				System.out.println(m.getName()+"'s status updated to "+newStatus);
			}
		}
		System.out.println("Expiry check completed!");
	}
}
