package com.istb.app.services.security;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/***
 * 
 * @author jdzm
 *
 */
public interface SecurityService {
	
	/**
	 * Load access control list ACL
	 * @param HttpSecurity object
	 * @return the current service instance
	 * @throws Exception
	 */
	SecurityService loadAccessControllList (HttpSecurity http) throws Exception;
	
	/**
	 * Load form login
	 * @param HttpSecurity object
	 * @return the current service instance
	 * @throws Exception
	 */
	SecurityService loadFormLogin (HttpSecurity http) throws Exception;
	
	/**
	 * Load logout
	 * @param HttpSecurity object
	 * @return the current service instance
	 * @throws Exception
	 */
	SecurityService loadLogout (HttpSecurity http) throws Exception;
	
	/**
	 * Load Access denied
	 * @param HttpSecurity object
	 * @return the current service instance
	 * @throws Exception
	 */
	SecurityService loadAccessDenied (HttpSecurity http) throws Exception;
	
	/**
	 * Load Auth user detail service
	 * @param AuthenticationManagerBuilder object
	 * @return the current service instance
	 * @throws Exception
	 */
	SecurityService loadAuthDetailService (AuthenticationManagerBuilder auth) throws Exception;
}
