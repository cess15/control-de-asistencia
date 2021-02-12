package com.istb.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.istb.app.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
	Role findByNombre(String nombre);
	
}
