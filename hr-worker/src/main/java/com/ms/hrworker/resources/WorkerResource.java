package com.ms.hrworker.resources;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms.hrworker.entities.Worker;
import com.ms.hrworker.repositories.WorkerRepository;

@RestController
@RequestMapping(value = "/workers")
public class WorkerResource {

	//OBS: Essa estrutura é uma alternativa apenas para verificar qual porta o projeto está rodando
	private static Logger logger = LoggerFactory.getLogger(WorkerResource.class); //Imprime mensagens de LOG no console
	
	@Autowired
	private Environment env; //Contém informações do contexto da apllicação, como variaveis de configuração e etc
	
	@Autowired
	private WorkerRepository workerRepository;
	
	@GetMapping
	public ResponseEntity<List<Worker>> findAll(){
		var list = workerRepository.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Worker> findById(@PathVariable("id")Long id){
		
		/* TESTANDO TIMEOUT DE RESPOSTA
		try {
			Thread.sleep(3000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		logger.info("EXECUTED PORT = " + env.getProperty("local.server.port")); //Imprime no log a porta de execução deste projeto
		
		var worker = workerRepository.findById(id).get();
		return ResponseEntity.ok().body(worker);
	}
}
