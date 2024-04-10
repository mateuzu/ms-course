package com.ms.hrpayroll.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ms.hrpayroll.entities.Payment;
import com.ms.hrpayroll.entities.Worker;

@Service
public class PaymentService {
	
	@Value("${hr-worker.host}")
	private String workerHost;

	@Autowired
	private RestTemplate restTemplate;
	
	public Payment getPayment(long workerId, int days) { //Instancia um payment através do worker Id
		
		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put("id", ""+workerId); //PathVariable da requisição, nesse caso o workerId 
		
		var worker = restTemplate.getForObject(workerHost + "/workers/{id}", Worker.class, uriVariables);
		
		return new Payment(worker.getName(), worker.getDailyIncome(), days); //Payment preenchido com o Worker
	}
}
