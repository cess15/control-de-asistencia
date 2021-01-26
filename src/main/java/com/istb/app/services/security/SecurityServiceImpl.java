package com.istb.app.services.security;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {

	@Override
	public SecurityService loadAccessControllList(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/", "/index", "/css/**", "/js/**", "/fonts/**", "/images/**")
			.permitAll();
	
		return this;
	}

	@Override
	public SecurityService loadFormLogin(HttpSecurity http) throws Exception {
		http.formLogin()
			.loginPage("/login")
			.permitAll();
	
		return this;
	}

	@Override
	public SecurityService loadLogout(HttpSecurity http) throws Exception {
		http.logout()
			.deleteCookies("JSESSIONID");
	
		return this;
	}

	@Override
	public SecurityService loadAccessDenied(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SecurityService loadAuthDetailService(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
