package com.ms.hrapigatewayzuul.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class AppConfig {

	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
		tokenConverter.setSigningKey("MY-SECRET-KEY"); //configuração da chave secreta (secret) para assinar os tokens
		return tokenConverter;
	}
	
	@Bean
	public JwtTokenStore jwtTokenStore() { //Responsável por ler as informações do token
		return new JwtTokenStore(jwtAccessTokenConverter()); //recebe o accessTokenConverter como parametro
	}
}
