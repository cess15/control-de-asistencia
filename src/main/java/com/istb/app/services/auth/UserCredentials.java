package com.istb.app.services.auth;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.istb.app.entities.Usuario;
import com.istb.app.repository.PeriodoRepository;
import com.istb.app.repository.UserRepository;

@Service
public class UserCredentials {

	@Autowired
	UserRepository userRepository;

	@Autowired
	PeriodoRepository periodoRepository;

	public Usuario getUserAuth() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return userRepository.findByNombreUsuario(auth.getName());
	}

	public String convertDate(LocalDate fecha) {
		Locale spanishLocale = new Locale("es", "ES");

		String month = fecha.getMonth().getDisplayName(TextStyle.FULL, spanishLocale);

		int year = fecha.getYear();

		return month.toUpperCase().concat(" " + year);
	}

}
