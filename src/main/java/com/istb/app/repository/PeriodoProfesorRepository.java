package com.istb.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.istb.app.entities.PeriodoProfesor;

@Repository
public interface PeriodoProfesorRepository extends JpaRepository<PeriodoProfesor, Integer> {

}
