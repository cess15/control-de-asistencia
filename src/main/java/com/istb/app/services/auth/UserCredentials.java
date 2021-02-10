package com.istb.app.services.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.istb.app.entities.Usuario;
import com.istb.app.repository.UserRepository;

@Service
public class UserCredentials {

	@Autowired
	UserRepository userRepository;

	public Usuario getUserAuth() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return userRepository.findByNombreUsuario(auth.getName());
	}
}
