package com.ms.hroauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer //Permite que o projeto vire um servidor de autorização
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Value("${oauth.client.name}")
	private String clientName;
	
	@Value("${oauth.client.secret}")
	private String clientSecret;
	
	//Dependencias
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Autowired
	private JwtAccessTokenConverter converter;
	
	@Autowired
	private JwtTokenStore tokenStore;
	
	@Autowired
	private AuthenticationManager manager;
	
	//Métodos sobrescritos
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
	}

	//Configura a autenticação com base nas credenciais do app e com o tipo do grant_type
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory() //Especifica que a autenticação ocorre em memória
		.withClient(clientName) //Define o client_id = username do aplicativo
		.secret(encoder.encode(clientSecret)) //Define o client_secret = senha do aplicativo (criptografada)
		.scopes("read", "write") //Define as permissões = ler e escrever
		.authorizedGrantTypes("password") //Tipo do grand_type = password
		.accessTokenValiditySeconds(86400); //Tempo de expiração = 24 horas
	}

	//Configura o processamento do token
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(manager)
		.tokenStore(tokenStore)
		.accessTokenConverter(converter);
	}

}
