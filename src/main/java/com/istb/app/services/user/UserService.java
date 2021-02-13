package com.istb.app.services.user;

import java.util.List;
import java.util.Map;

import com.istb.app.entities.Usuario;

public interface UserService {
	List<Usuario> findAll();

	Map<String, String> save(Usuario usuario);

	void update(Usuario usuario);

	Usuario findById(int id);

	Usuario findByNombreUsuario(String nombreUsuario);

	Usuario findByProfesorCorreo(String correo);

	String encodePassword(String contrasena);

	void delete(int id);
}
