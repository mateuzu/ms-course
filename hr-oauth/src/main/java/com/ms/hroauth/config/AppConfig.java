package com.ms.hroauth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class AppConfig {

	@Value("${jwt.secret}") //Captura a propriedade do servidor de configuração
	private String jwtSecret;
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	//Componentes do JWT
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
