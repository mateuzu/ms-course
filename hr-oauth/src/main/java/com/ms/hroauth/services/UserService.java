package com.ms.hroauth.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ms.hroauth.entities.User;
import com.ms.hroauth.feignclients.UserFeignClient;

@Service
public class UserService implements UserDetailsService{

	private static Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserFeignClient userFeignClient;
	
	public User findByEmail(String email) {
		var user = userFeignClient.findByEmail(email).getBody();
		
		if(user == null) {
			logger.error("Email not found: " + email); //Log de erro caso não encontre o email
			throw new IllegalArgumentException("Email not found");
		}
		
		logger.info("Email found: " + email); //Log de info caso encontre o email
		return user;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		var user = userFeignClient.findByEmail(username).getBody();
		
		if(user == null) {
			logger.error("Email not found: " + username); //Log de erro caso não encontre o email
			throw new UsernameNotFoundException("Email not found");
		}
		
		logger.info("Email found: " + username); //Log de info caso encontre o email
		return user;
	}
}
