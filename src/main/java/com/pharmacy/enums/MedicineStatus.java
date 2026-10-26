package com.pharmacy.enums;

public enum MedicineStatus {
//	→ normal, good stock, not expiring
	ACTIVE,
//	 → expiring within 30 days
	EXPIRING_SOON,
//	 → expiry datepassed
	EXPIRED,       
//	 → quantity below 10
	LOW_STOCK      
}
