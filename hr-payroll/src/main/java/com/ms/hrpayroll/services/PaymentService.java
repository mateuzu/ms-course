package com.ms.hrpayroll.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.hrpayroll.entities.Payment;
import com.ms.hrpayroll.feignclients.WorkerFeignClient;

@Service
public class PaymentService {

	@Autowired
	private WorkerFeignClient workerFeignClient;
	
	public Payment getPayment(long workerId, int days) { //Instancia um payment através do worker Id
		
		var worker = workerFeignClient.findById(workerId).getBody(); //Captura o corpo da resposta que é do tipo Worker
		
		return new Payment(worker.getName(), worker.getDailyIncome(), days); //Payment preenchido com o Worker
	}
}
