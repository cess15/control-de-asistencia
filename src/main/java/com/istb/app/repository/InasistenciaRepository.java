package com.istb.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.istb.app.entities.Inasistencia;

@Repository
public interface InasistenciaRepository extends JpaRepository<Inasistencia, Integer> {

}
