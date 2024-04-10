package com.ms.hrpayroll.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

	//Padrão Singleton: Registra uma instância unica do RestTemplate, essa instancia fica disponível para injetar em outros componentes
	@Bean
	public RestTemplate restTemplate() { 
		return new RestTemplate();
	}
}
