package com.istb.app.services.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.istb.app.entities.Usuario;
import com.istb.app.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public Usuario findByNombreUsuario(String nombreUsuario) {
		Usuario usuario = userRepository.findByNombreUsuario(nombreUsuario);
		if (usuario != null) {
			return usuario;
		}
		return null;

	}
	
	@Transactional
	@Override
	public void delete(int id) {
		userRepository.deleteById(id);
	}

	@Override
	public Map<String, String> save(Usuario usuario) {

		Map<String, String> errorAttributes = new HashMap<>();
		boolean existeNombreUsuario = findByNombreUsuario(usuario.getNombreUsuario()) != null;
		
		String password = encodePassword(usuario.getContrasena());
		
		if (existeNombreUsuario) {
			
			errorAttributes.put("nombreUsuario", "Usuario ".concat(
				usuario.getNombreUsuario()).concat(" ya existe"));
			errorAttributes.put("clase", "text-danger");
		
			return errorAttributes;

		} 
		
		usuario.setEstado(true);
		usuario.setContrasena(password);
		
		userRepository.save(usuario);

		return errorAttributes;
		
	}

	@Override
	public Map<String, String> update(Usuario usuario) {
		
		userRepository.save(usuario);

		return null;
	}

	@Override
	public List<Usuario> findAll() {
		return userRepository.findAll();
	}

	@Override
	public Usuario findByProfesorCorreo(String correo) {

		Usuario usuario = userRepository.findByProfesorCorreo(correo);
		if (usuario != null) {
			return usuario;
		}
		
		return null;

	}

	@Override
	public String encodePassword(String contrasena) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2Y, 12);
		return passwordEncoder.encode(contrasena);

	}

	@Override
	public Usuario findById(int id) {
		Optional<Usuario> usuario = userRepository.findById(id);
		if (usuario.isPresent()) {
			return usuario.get();
		}
		return null;
	}

}
