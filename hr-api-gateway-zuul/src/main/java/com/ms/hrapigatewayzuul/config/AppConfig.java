package com.ms.hrapigatewayzuul.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@RefreshScope //Atualiza o valor das variaveis em tempo de execução, caso haja mudança no repositorio git
@Configuration
public class AppConfig {

	@Value("${jwt.secret}")
	private String jwtSecret;
	
	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
		tokenConverter.setSigningKey(jwtSecret); //configuração da chave secreta (secret) para assinar os tokens
		return tokenConverter;
	}
	
	@Bean
	public JwtTokenStore jwtTokenStore() { //Responsável por ler as informações do token
		return new JwtTokenStore(jwtAccessTokenConverter()); //recebe o accessTokenConverter como parametro
	}
}
