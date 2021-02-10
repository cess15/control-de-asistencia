package com.istb.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.istb.app.entities.Permiso;

@Repository
public interface PermisoRepository extends JpaRepository<Permiso, Integer> {

}
