package com.istb.app.services.user;

import com.istb.app.entities.Usuario;

public interface UserService {
	void save(Usuario usuario);

	void update(Usuario usuario);

	Usuario findByNombreUsuario(String nombreUsuario);

	void delete(int id);
}
