package com.ms.hrapigatewayzuul.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableResourceServer //Permite que o projeto vire um servidor de recurso
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{

	@Autowired
	private JwtTokenStore tokenStore;
	
	private static final String[] ENDPOINT_PUBLIC = {"/hr-oauth/oauth/token"}; //Endpoints publicos
	private static final String[] ENDPOINT_OPERATOR = {"/hr-worker/**"}; //Endpoints que precisam pelo menos da ROLE_OPERATOR
	private static final String[] ENDPOINT_ADMIN = {"/hr-payroll/**", "/hr-user/**", "/actuator/**", "/hr-worker/actuator/**", "/hr-oauth/actuator/**"}; //Endpoints que precisam da ROLE_ADMIN
	
	//Métodos sobrescritos
	
	//Configura o armazenamento de tokens
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenStore(tokenStore);
	}

	//Configura as autorizações
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests() //Permite configurar requisições
			.antMatchers(ENDPOINT_PUBLIC).permitAll() //Libera os endpoints do vetor PUBLIC
			.antMatchers(HttpMethod.GET, ENDPOINT_OPERATOR).hasAnyRole("OPERATOR", "ADMIN") //Libera as requisições do tipo GET para os endpoints do vetor OPERATOR desde que possua a permissão OPERATOR OR ADMIN
			.antMatchers(ENDPOINT_ADMIN).hasRole("ADMIN") //Libera os endpoints do vetor ADMIN desde que possua a permissão ADMIN
			.anyRequest().authenticated(); //Qualquer outra requisição exige que o usuário esteja autenticado
		
		http.cors().configurationSource(corsConfigurationSource()); //Passa o bean de configuração de CORS como parâmetro
	}
	
	//CORS CONFIGURATION
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration corsConfig = new CorsConfiguration();
		//Define as configurações para aplicações externas
		corsConfig.setAllowedOrigins(Arrays.asList("*")); //Origens permitidas para acessar o sistema = * indica todos
		corsConfig.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "PATCH")); //Métodos HTTP permitidos para aplicações externas
		corsConfig.setAllowCredentials(true); //Permite credenciais para aplicações externas
		corsConfig.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type")); //Cabeçalhos permitidos para aplicações externas
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); //Registra as configurações CORS em caminhos específicos
		source.registerCorsConfiguration("/**", corsConfig); //Indica que todos os caminhos possuem a configuração CORS do objeto corsConfig
		return source;
	}
	
	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter(){
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(corsConfigurationSource()));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE); //Filtro executado em alta precedência
		return bean;
	}
}
