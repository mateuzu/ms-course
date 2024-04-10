package com.ms.hrpayroll.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ms.hrpayroll.entities.Worker;

@Component
@FeignClient(
		name = "hr-worker", //Nome do client feign
		url = "localhost:8001", //url que o nosso client irá enviar requisições
		path = "/workers" //caminho do recurso específico para enviar requisições
		)
public interface WorkerFeignClient {

	@GetMapping(value = "/{id}")
	ResponseEntity<Worker> findById(@PathVariable("id")Long id);
}
