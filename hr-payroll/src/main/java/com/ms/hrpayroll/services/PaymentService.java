package com.ms.hrpayroll.services;

import org.springframework.stereotype.Service;

import com.ms.hrpayroll.entities.Payment;

@Service
public class PaymentService {

	public Payment getPayment(long workerId, int days) { //Instancia um payment através do worker Id
		return new Payment("name", 100.00, days); //Payment mockado
	}
}
