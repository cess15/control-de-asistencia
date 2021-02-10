package com.istb.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.istb.app.entities.Usuario;

@Repository
public interface UserRepository extends JpaRepository<Usuario, Integer> {
	Usuario findByNombreUsuario(String nombreUsuario);

	Usuario findByProfesorCorreo(String correo);
}
