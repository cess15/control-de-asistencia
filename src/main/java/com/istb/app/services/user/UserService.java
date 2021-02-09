package com.istb.app.services.user;

import java.util.List;

import com.istb.app.entities.Usuario;

public interface UserService {
	List<Usuario> findAll();

	void save(Usuario usuario);

	void update(Usuario usuario);

	Usuario findByNombreUsuario(String nombreUsuario);

	Usuario findByProfesorCorreo(String correo);

	void delete(int id);
}
