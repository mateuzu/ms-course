package com.ms.hrpayroll.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms.hrpayroll.entities.Payment;
import com.ms.hrpayroll.services.PaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@RequestMapping(value = "/payments")
public class PaymentResources {

	@Autowired
	private PaymentService paymentService;
	
	@HystrixCommand(fallbackMethod = "getPaymentAlternative") //Recebe como parâmetro o nome do método alternativo que será executado caso ocorra alguma
	@GetMapping(value = "/{workerId}/days/{days}")
	public ResponseEntity<Payment> getPayment(@PathVariable("workerId")Long workerId,
			@PathVariable("days") Integer days){
		var payment = paymentService.getPayment(workerId, days);
		return ResponseEntity.ok().body(payment);
	}
	
	//Método alternativo que será chamado caso ocorra alguma falha em getPayment
	//Esse método deve conter uma alternativa que não dependa do microserviço hr-worker para execução
	public ResponseEntity<Payment> getPaymentAlternative(Long workerId, Integer days){
		var payment = new Payment("Teste", 400.00, days);
		return ResponseEntity.ok().body(payment);
	}
}
